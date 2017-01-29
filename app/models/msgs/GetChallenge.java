package models.msgs;

public class GetChallenge {

    String sender;
    String challenger;

    public String getSender(){
        return sender;
    }
    public String getChallenger(){
        return challenger;
    }

    public GetChallenge(String sender, String challenger) {
        this.sender = sender;
        this.challenger = challenger;
    }
}

