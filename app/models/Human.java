package models;


import models.msgs.*;
import play.Logger;
import play.mvc.*;
import play.libs.*;
import play.libs.F.*;
import akka.actor.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Arrays;


public class Human extends UntypedActor
{
    public final String               name;
    public final ActorRef             table;
    public String challenger = null;

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

                    int nr = event.get("nr").asInt();
                	getSelf().tell(new Move(nr,name), getSelf() );
                    System.out.println("M(OVE MOVE ");
                }
                catch (Exception e)
                {
                    Logger.error("invokeError");
                }
                try{
                    System.out.println(event.get("nrr").asText());
                    setChallenger(event.get("nrr").asText());
                    int nr = event.get("nrr").asInt();
                    getSelf().tell(new Challenge(name ,event.get("nrr").asText()), getSelf() );
                    System.out.println("CHALLENGE");
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
            	int position = ((Move) msg).getPosition();            	           			
            	String text = "I've got the position " + position + " from WebSocket and send it to Table";
            	table.tell(new Info(text, name), getSelf()); 
            	table.tell(new Move(position, name), getSelf());
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
                }
                else{
                    event.put("message", "You get challenge from " + info.getSender());

                }
                out.write(event);
            }
            
            else if(msg instanceof Ack)
            {       
            	             	
	            ObjectNode event = Json.newObject();
	            event.put("message", "[ Ack from Table: my move has been accepted ] "); 
	            
	            out.write(event);
            }
            else {
                unhandled(msg);
            }
            
        }

        private void setChallenger(String challenger){
            this.challenger = challenger;
        }
            
}

