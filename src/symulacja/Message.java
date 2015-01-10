package symulacja;

public class Message {

    private String content;
    private Agent author;
    private Agent receiver;

    private boolean broadcasted;

    public Message(String text, Agent msgAuthor, Agent msgReceiver){
        this.content = text;
        this.author = msgAuthor;
        this.receiver = msgReceiver;
        setBroadcasted();
    }

    private void setBroadcasted(){
        this.broadcasted = (null == this.receiver) ? true : false;
    }

    public boolean checkBroadcasted(){
        return this.broadcasted;
    }
}
