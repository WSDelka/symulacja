package symulacja;

import java.util.ArrayList;

public class Agent {

    private ArrayList<Agent> neighboursAgents;
    private ArrayList<Agent> candidates;
    private Position positions;
    private ArrayList<Message> messages;
    private int id;
    private static int idCounter = 1;

    public Agent(Position initPositions){
        setNewID();
        this.positions = initPositions;
        this.messages = new ArrayList<Message>();
        this.candidates = new ArrayList<Agent>();
    }

    private void setNewID(){
        this.id = idCounter;
        idCounter++;
    }

    public Position getPositions(){
        return positions;
    }

    public int getID(){
        return this.id;
    }

    public void receiveNewMessage(Message msg){
        this.messages.add(msg);
    }

    public void setNewPosition(Position newPosition){
        this.positions = newPosition;
    }

    public void setNewCandidates(ArrayList<Agent> newCandidates){
        candidates.clear();
        candidates.addAll(newCandidates);
    }

    public void printNeighboursIDs(){
        System.out.println("Aktualna lista sasiadow dla agenta o ID: " + this.getID());
        for (Agent eachAgent : candidates){
            System.out.println(eachAgent.getID());
        }
    }

}
