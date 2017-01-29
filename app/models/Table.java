package models;

import play.mvc.*;
import play.libs.*;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import akka.actor.*;
import static akka.pattern.Patterns.ask;
import static java.util.concurrent.TimeUnit.*;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;

import models.msgs.*;


public class Table extends UntypedActor {
    
    // Default table.
    static ActorRef defaultTable = Akka.system().actorOf(Props.create(Table.class));
    
    // Members of this table.
    static Map<String, ActorRef> members = new HashMap<String, ActorRef>();
       
    public static void join(final String name, WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out ) throws Exception{

        // Send the Join message to the table
        String result = (String)Await.result(ask(defaultTable,new Join(name), 1000), Duration.create(1, SECONDS));
                
        if("OK".equals(result)) {
        	
            ActorRef player =  Akka.system().actorOf(
                    Props.create(Human.class, name, in, out,  defaultTable ));
            
            members.put(name, player);            
            String text =  "my Actor has been added to Table"; 
            notifyAll(new Info(text,name));                             
        }
    }
    
    public static void quit(final String username) throws Exception{

        //to do
    }
    
       
    public void onReceive(Object message) throws Exception {

        if(message instanceof Join) {
            
            Join join = (Join) message;
                        
            if(false){
            	// if username is already taken do sth        	
            }
            else {                
                getSender().tell("OK", getSelf());
            }
        } else if (message instanceof Move)
        {
            System.out.println("^^^");
        	int position = ((Move) message).getPosition();
        	String name = ((Move) message).getName();  
        	getSender().tell(new Ack(), getSelf());
        	String text = "move on position    " +  position  + "  from " + name;
        	notifyAll(new Info(text,"Table"));
        }
        else if (message instanceof Challenge)
        {
//            int position = ((Challenge2) message).getPosition();
//            String name = ((Challenge2) message).getName();
//            getSender().tell(new Ack(), getSelf());
//            String text = "You get challenge from: " + name;
            foo2(message);
//            notifyAll(new GetChallenge(text,"Table"));
        }
        else if (message instanceof Info)
        {
        	String text = ((Info) message).getText();
        	String name = ((Info) message).getName();  
        	
        	notifyAll(new Info(text, name));
        }  
        else if(message instanceof Quit)  {
           
            String name = ((Quit)message).getName();            
            members.remove(name);
            String text = "has left the game";            		
            notifyAll(new Info(text, name));
          
        
        } else {
            unhandled(message);
        }
        
    }
    
        
    static public void notifyAll(Object msg)
    {
        for (ActorRef member : members.values())
        {
            member.tell(msg , defaultTable);
        }
    }

    static public void foo2(Object msg)
    {
        if(msg instanceof Challenge) {
            String name = ((Challenge) msg).challenger();
            System.out.println("foo name : " + name);
            GetChallenge a = new GetChallenge(((Challenge) msg).getSender(), name);
            for(String m : members.keySet()){
                if(m.equals(name)){
                    members.get(name).tell(a, defaultTable);
                }
            }

        }
    }
    
    
    
    
}

