package models.msgs;

public class Challenge {
    String sender;
    String challenger;



    public String getSender(){
        return sender;
    }
    public String challenger(){
        return challenger;
    }

    public Challenge(String sender, String challenger) {
        this.sender = sender;
        this.challenger = challenger;

    }
}

