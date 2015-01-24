package symulacja;

import static symulacja.Config.MESSAGE_LIFETIME;
public class Message {

    private String content;
    private Agent author;
    private Agent receiver;
    private int id;
    int sendTime;
    private static int idCounter = 1;

    private boolean broadcasted;

    public Message(String text, Agent msgAuthor, Agent msgReceiver, int sendTime){
        setNewID();
        this.content = text;
        this.author = msgAuthor;
        this.receiver = msgReceiver;
        this.sendTime = sendTime;
        setBroadcasted();
    }

    private void setNewID(){
        this.id = idCounter;
        idCounter++;
    }

    public int getID(){
        return this.id;
    }

//    public Agent getAuthor(){
//        return author;
//    }

    public boolean isValid(int currentStep) {
        return currentStep-sendTime <= MESSAGE_LIFETIME;
    }

    public int getAuthorID(){
        return author.getID();
    }

    private void setBroadcasted(){
        this.broadcasted = (null == this.receiver) ? true : false;
    }

//    public boolean checkBroadcasted(){
//        return this.broadcasted;
//    }

    public String getContent(){
        return content;
    }

//    public static int getLastActiveID(){
//        return idCounter-1;
//    }
}
