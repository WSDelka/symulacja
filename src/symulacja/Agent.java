package symulacja;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Agent {

    private ArrayList<Agent> neighboursAgents;
    private ArrayList<Agent> candidates;
    private Position positions;
    private HashMap<Integer, Message> messages;
    private Integer id;
    private static int idCounter = 1;

    public Agent(Position initPositions){
        setNewID();
        this.positions = initPositions;
        this.messages = new HashMap<Integer, Message>();
        this.candidates = new ArrayList<Agent>();
        neighboursAgents = new ArrayList<Agent>();
    }

    private void setNewID(){
        this.id = idCounter;
        idCounter++;
    }

    public Position getPositions(){
        return positions;
    }

    public Integer getID(){
        return this.id;
    }

    public void receiveNewMessage(Message msg){
        System.out.println("msg ID: " + msg.getID() + " tresc: " + msg.getContent());
        if (!isMsgAlreadyArrived(msg)){
            messages.put(msg.getID(), msg);
        }
    }

    private boolean isMsgAlreadyArrived(Message msg){
        return messages.containsKey(msg.getID()) ? true : false;
    }

    public void setNewPosition(Position newPosition){
        this.positions = newPosition;
    }

    public void setNewCandidates(ArrayList<Agent> newCandidates){
        candidates.clear();
        candidates.addAll(newCandidates);
    }

    public void printCandidateNeighboursIDs(){
        System.out.println("Aktualna lista kandydatow na sasiadow dla agenta o ID: " + this.getID());
        for (Agent eachAgent : candidates){
            System.out.println(eachAgent.getID());
        }
    }

    public ArrayList<Agent> getNeighbours(){
        return candidates;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public ArrayList<Integer> getMessagesListOfIDS(){
        ArrayList<Integer> msgIDS = new ArrayList<Integer>();
        Iterator<Integer> keyIterator = messages.keySet().iterator();
        while(keyIterator.hasNext()){
            Integer key = keyIterator.next();
            msgIDS.add(key);
        }
        return msgIDS;
    }

    public void sendMessageToNeighbours(int msgID){
        if (messages.containsKey(msgID)){
            for (Agent eachNeighbour : candidates){
                eachNeighbour.receiveNewMessage(messages.get(msgID));
            }
        }
    }

    public void addNewMessage(Message msg){
        this.messages.put(msg.getID(), msg);
    }
}
