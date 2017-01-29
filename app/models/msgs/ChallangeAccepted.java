package models.msgs;

public class ChallangeAccepted {

    final String text;
    final String name;

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public ChallangeAccepted(String text, String name) {
        this.text = text;
        this.name = name;
    }
}

