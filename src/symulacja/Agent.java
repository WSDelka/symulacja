package symulacja;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static symulacja.Config.CONNECTIONS_NUMBER;

public class Agent {

    private ArrayList<Agent> neighboursAgents;
    private ArrayList<Connection> connections;
    private ArrayList<Agent> candidates;
    private Position positions;
    private HashMap<Integer, Message> messages;
    private Integer id;
    private int currentTime;
    private static int idCounter = 1;

    public Agent(Position initPositions){
        setNewID();
        this.positions = initPositions;
        this.messages = new HashMap<Integer, Message>();
        this.candidates = new ArrayList<Agent>();
        this.neighboursAgents = new ArrayList<Agent>();
        this.connections = new ArrayList<Connection>(CONNECTIONS_NUMBER);
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

    public void setNewPosition(Position newPosition){
        this.positions = newPosition;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    //---------------------------------- NEIGHBOURS ---------------------

    public void setNewCandidates(ArrayList<Agent> newCandidates){
        candidates.clear();
        candidates.addAll(newCandidates);
    }

    //przed nowymi polaczeniami czyscimy obie listy
    public void clearNeighboursAndConnectionsLists(){
        this.neighboursAgents.clear();
        this.connections.clear();
    }

    //na podstawie kandydatow zbuduj liste sasiadow (ale tylko do connections_number, nie wiecej!)
    //bierzemy pierwszych kandydatow w liczbie: connections_number i umieszczamy ich na liscie neighboursAgents
    //przy budowaniu trzeba sprawdzic, czy agent do ktorego budujemy polaczenie ma miejsce na liscie polaczen
    //jesli nie ma to bierzemy nastepnego z listy
    //dodatkowo do connections dodajemy nowe polaczenie u obu agentow (zeby zapewnic, ze obaj do siebie maja polaczenie)
    //no i pamietac ze dla tych dalszych agentow, u ktorych juz ktos zestawil polaczenie to iterujemy nie od 0 tylko
    //od pierwszego wolnego slotu na polaczenie az do tego connection_number
    // UWAGA! po zaimplementowaniu zmien funkcje sendMessageToNeighbours (znajduje sie w tej klasie troche nizej)
    public void buildNeighboursFromCandidates(){
        int candidates_iterator = findFirstFreeSlot();
        if (candidates_iterator < 0) {      //agent ma już wystarczającą liczbę połączeń, nie tworzymy kolejnych
            return;
        }
        Agent candidate = null;
        while(connections.size() < CONNECTIONS_NUMBER && candidates_iterator < candidates.size()){
            candidate = candidates.get(candidates_iterator);
            if (candidate == null){        //nie powinno się zdarzyć, ale warto sprawdzic
                return;                     //nie ma sensu dalej tworzyć listy
            }
            if (candidate.hasFreePlaceOnConnectionList() && !this.isCandidateAlreadyAtList(candidate)){     //znaleźliśmy dobrego kandydata
                addNewConnection(candidate);
            }
            ++candidates_iterator;
        }
    }

    //znajduje pierwsze wolne miejsce na utworzenie nowego połączenia, jezeli nie ma juz miejsc zwraca -1
    private int findFirstFreeSlot() {
        int connectionSize = connections.size();
        return connectionSize < CONNECTIONS_NUMBER ? connectionSize : -1;
    }

    public void setTime(int time) {
        currentTime = time;
    }

    //tworzy polaczenie pomiedzy dwoma agentami
    private void addNewConnection(Agent otherAgent) {
        neighboursAgents.add(otherAgent);
        otherAgent.addNeighbour(this);
        Connection conn = new Connection(this, otherAgent);
        this.addConnection(conn);
        otherAgent.addConnection(conn);
    }

    public void addConnection(Connection conn){
        this.connections.add(conn);
    }

    private void addNeighbour(Agent otherAgent) {
        neighboursAgents.add(otherAgent);
    }

    //sprawdzanie, czy mozna dodac jeszcze polaczenie
    public boolean hasFreePlaceOnConnectionList(){
        return connections.size() < CONNECTIONS_NUMBER ? true : false;
    }

    private boolean isCandidateAlreadyAtList(Agent candidate){
        if (null != candidate){
            for (Agent neighbour : neighboursAgents){
                if(candidate.getID() == neighbour.getID()){
                    return true;
                }
            }
        }
        return false;
    }

//    public void printCandidateNeighboursIDs(){
//        System.out.println("Aktualna lista kandydatow na sasiadow dla agenta o ID: " + this.getID());
//        for (Agent eachAgent : candidates){
//            System.out.println(eachAgent.getID());
//        }
//    }

    public ArrayList<Agent> getNeighbours(){
        return neighboursAgents;
    }

    //--------------------------- MESSAGES -------------------------------
    public ArrayList<Integer> getMessagesListOfIDS(){
        ArrayList<Integer> msgIDS = new ArrayList<Integer>();
        Iterator<Integer> keyIterator = messages.keySet().iterator();
        while(keyIterator.hasNext()){
            Integer key = keyIterator.next();
            msgIDS.add(key);
        }
        return msgIDS;
    }

    public void sendMessagesToNeighbours() {
        for (Message message : messages.values()) {
            if (message.isValid(currentTime)) {
                sendMessageToNeighbours(message.getID());
            }
        }
    }

    public void sendMessageToNeighbours(int msgID){
        if (messages.containsKey(msgID)){
            for (Agent eachNeighbour : neighboursAgents){
                eachNeighbour.receiveNewMessage(messages.get(msgID));
            }
        }
    }

    public void receiveNewMessage(Message msg){
        System.out.println("msg ID: " + msg.getID() + " autor ID: " + msg.getAuthorID() + " tresc: " + msg.getContent());
        if (!isMsgAlreadyArrived(msg)){
            messages.put(msg.getID(), msg);
        }
    }

    private boolean isMsgAlreadyArrived(Message msg){
        return messages.containsKey(msg.getID()) ? true : false;
    }

    public void addNewMessage(Message msg){
        this.messages.put(msg.getID(), msg);
    }
}