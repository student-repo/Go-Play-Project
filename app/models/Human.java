package models;


import game.GoPlayer;
import game.engine.BoardFieldOwnership;
import game.engine.GameEngine;
import game.engine.GameEngineStatus;
import models.msgs.*;
import play.Logger;
import play.mvc.*;
import play.libs.*;
import play.libs.F.*;
import akka.actor.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Human extends UntypedActor
{
    public final String               name;
    public final ActorRef             table;
    public String challenger = null;
    public String opponent;
    public boolean gameStarted = false;
    private GameEngine game;
    private GoPlayer player, opponentPlayer;

    protected WebSocket.In<JsonNode>  in;
    protected WebSocket.Out<JsonNode> out;



    public Human(String _name, WebSocket.In<JsonNode> _in,
            WebSocket.Out<JsonNode> _out, ActorRef _table)
    {
        name = _name;        
        table = _table;
        in = _in;
        out = _out;
        

        in.onMessage(new Callback<JsonNode>()
        {
            @Override
            public void invoke(JsonNode event)
            {
                try
                {
                    ArrayList<String> inputArguments = new ArrayList<String>(Arrays.asList(event.get("nr").asText().split("\\s* \\s*")));
                    System.out.println(inputArguments);

//                    int nr = event.get("nr").asText();
                    if(gameStarted){

                        ObjectNode event1 = Json.newObject();
                        try{

                            game.makeMove(Integer.parseInt(inputArguments.get(0)), Integer.parseInt(inputArguments.get(1)), player);
                            event1.put("message", getGameBoardView());
                            out.write(event1);
                            getSelf().tell(new Move(new Point(Integer.parseInt(inputArguments.get(0)), Integer.parseInt(inputArguments.get(1))),name, opponent), getSelf() );
                        }
                        catch(Exception e){
                            event1.put("message", "Not your turn! ");
                            out.write(event1);
                        }


                    }
                }
                catch (Exception e)
                {
                    Logger.error("invokeError");
                }
                try{
                    if(gameStarted){
                        ;
                    }
                    else{
                        if (event.get("nrr").asText().equals(challenger)) {

                            getSelf().tell(new Challenge(name ,event.get("nrr").asText()), getSelf() );
                            gameStarted = true;
                            setOpponent(challenger);
                            ObjectNode event1 = Json.newObject();
                            event1.put("message", "Lets start the game! Your move");
                            out.write(event1);
                            player = new GoPlayer(BoardFieldOwnership.BLACK, GameEngineStatus.GAME);
                            opponentPlayer = new GoPlayer(BoardFieldOwnership.WHITE, GameEngineStatus.GAME);
                            game = new GameEngine(opponentPlayer, player);
                        }
                        else{
                            setChallenger(event.get("nrr").asText());
                            getSelf().tell(new Challenge(name ,event.get("nrr").asText()), getSelf() );
                            ObjectNode event1 = Json.newObject();
                            event1.put("message", "You suggest game for " + event.get("nrr").asText());
                            out.write(event1);
                        }
                    }
                }
                catch (Exception e)
                {
                    Logger.error("invokeError");
                }
                
            }
        });

        in.onClose(new Callback0()
        {
            @Override
            public void invoke()
            {
                table.tell(new Quit(name), getSelf() );
            }
        });
    }

    @Override
    public void preStart()
    {        
        String text = "my Actor is now running!";
    	table.tell(new Info(text, name), getSelf()); 
    }

    @Override
    public void postStop()
    {
        String text = "I've been killed";
    	table.tell(new Info(text, name), getSelf()); 
    }

    @Override
    public void onReceive(Object msg) throws Exception
    {
 
            if (msg instanceof Move)
            {   
            	Point position = ((Move) msg).getPosition();
            	String text = "I've got the position " + position + " from WebSocket and send it to Table";
                table.tell(new Move(position, ((Move) msg).getFrom(), ((Move) msg).getTo()), getSelf());
            }
            else if(msg instanceof GetMove){
                GetMove move = (GetMove) msg;
                ObjectNode event = Json.newObject();
                event.put("message", "[ " + move.getFrom() + " ]" + move.getPosition() );

                out.write(event);

                ObjectNode event1 = Json.newObject();
                game.makeMove((int)move.getPosition().getX(), (int)move.getPosition().getY(), opponentPlayer);
                event1.put("message", getGameBoardView());
                out.write(event1);
            }
        else if(msg instanceof Challenge)
        {
            String sender = ((Challenge) msg).getSender();
            String n = ((Challenge) msg).challenger();
            table.tell(new Challenge(sender, n), getSelf());
        }

            else if(msg instanceof Info)
            {
                Info info = (Info) msg;
                ObjectNode event = Json.newObject();
                event.put("message", "[ "+ info.getName()+ " ] : " + info.getText());

                out.write(event);
            }

        else if(msg instanceof GetChallenge)
            {
                GetChallenge info = (GetChallenge) msg;
                ObjectNode event = Json.newObject();
                if(info.getSender().equals(challenger)){
                    event.put("message", "User " + info.getChallenger() + " accepted your challenge. Lets start the game!");
                    setOpponent(info.getSender());
                    gameStarted = true;
                    player = new GoPlayer(BoardFieldOwnership.WHITE, GameEngineStatus.GAME);
                    opponentPlayer = new GoPlayer(BoardFieldOwnership.BLACK, GameEngineStatus.GAME);
                    game = new GameEngine(player, opponentPlayer);
                }
                else{
                    setChallenger(info.getSender());
                    event.put("message", "You get challenge from " + info.getSender() + ", to accept challenge the user also ");

                }
                out.write(event);
            }
            else {
                unhandled(msg);
            }
            
        }

        private void setChallenger(String challenger){
            this.challenger = challenger;
        }

    private void setOpponent(String opponent){
        this.opponent = opponent;
    }
    private String getGameBoardView() {
        String s = "Board state: \n";
        for (Point p : game.getBoardFields().keySet()) {
            if (game.getBoardFields().get(p) != BoardFieldOwnership.FREE) {
                s += "[ " + (int)p.getX() + ", " + (int)p.getY() + " ]" + ": " + game.getBoardFields().get(p) + '\n';
            }
        }
        return s;
    }
            
}

