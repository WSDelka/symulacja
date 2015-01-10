package symulacja;

import java.util.ArrayList;

public class Agent {

    private ArrayList<Agent> neighboursAgents;
    private CoordinatePair coordinates;
    private ArrayList<Message> messages;

    private static int id = 0;

    public Agent(CoordinatePair initCoordinates){
        this.id++;
        this.coordinates = initCoordinates;
        this.messages = new ArrayList<Message>();
    }

    public int getID(){
        return this.id;
    }

    public void receiveNewMessage(Message msg){
        this.messages.add(msg);
    }

    public void setNewCoordinates(CoordinatePair newCoordinates){
        this.coordinates = newCoordinates;
    }

}
