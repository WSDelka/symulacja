package symulacja;

import javax.swing.*;
import java.util.ArrayList;

import static symulacja.Config.NUM_OF_AGENTS;

//klasa odpowiadajaca za obsluge mapy - klasa sterujaca, dzieki ktorej bedziemy wysylac wiadomosci,
//wymuszac nowe pozycje agentow itp. Cos w rodzaju kontrolera w modelu MVC. Mysle, ze przyda sie
//przy budowaniu widoku
public class MapController {

    private Map map;
    private ArrayList<Agent> agents;
    private PositionRandomizer positionRandomizer;
    private static int currentStep;

    public MapController(Map simulationMap){
        this.map = simulationMap;
        this.agents = new ArrayList<Agent>();
        this.positionRandomizer = new PositionRandomizer();
        randomlySetAgents();
    }

    private void randomlySetAgents(){
        for (int i = 0; i < NUM_OF_AGENTS; i++){
            Position agentPositions;
            do {
                agentPositions = positionRandomizer.drawNewPositions();
            } while(map.isAgentCollisionAtMap(agentPositions));
            Agent newAgent = new Agent(agentPositions);
            map.addNewAgentToMap(newAgent);
            addAgentToList(newAgent);
        }
    }

    public void setCurrentStep(int step) {
        currentStep = step;
        setAgentsTime();
    }

//    public int getCurrentStep() {
//        return currentStep;
//    }

    public void setAgentsTime() {
        for (Agent agent : agents) {
            agent.setTime(currentStep);
        }
    }

    private void addAgentToList(Agent agent){
        this.agents.add(agent);
    }

//    public Agent getAgentFromList(int index){
//        return agents.get(index);
//    }

    public Agent getAgentFromListByID(int agentID){
        for (Agent eachAgent : agents){
            if (eachAgent.getID() == agentID){
                return eachAgent;
            }
        }
        return null;
    }

    //iterujemy po liscie agentow
    //losujemy (50% szans) czy dany agent sie przemieszcza
    //jesli tak to losujemy mu nowe pozycje dopoki te wylosowane powoduja kolizje
    //jesli sie zgadza to danemu agentowi ustalamy te nowe pozycje
    //na koncu odswiezamy mape
    public void moveAgentsRandomly(){
        for (Agent eachAgent : agents){
            if (positionRandomizer.isAgentHasToMove()){
                Position beginningAgentPosition = eachAgent.getPositions();
                Position candidatePosition;
                do {
                    candidatePosition = positionRandomizer
                            .randomizeDependsOnOldPosition(beginningAgentPosition.getX(), beginningAgentPosition.getY());
                } while(map.isAgentCollisionAtMap(candidatePosition));
                eachAgent.setNewPosition(candidatePosition);
                updateMapWithNewAgentPosition(eachAgent, beginningAgentPosition);
            }
        }
//        updateMapWithNewAgentPositions();
    }

    //dla kazdego agenta z listy robi nowa, aktualna liste wszystkich kandydatow na przyszle polaczenia
    public void setNewNeighboursToAgents(){
        for (Agent eachAgent : agents){
            map.makeListOfCandidatesAgentNeighbours(eachAgent);
        }
    }

    // 1. czyscimy listy agentow (neighbours i connections)
    // 2. na podstawie kandydatow budujemy liste sasiadow zachowujac reguly opisane w klasie agent
    public void connectAgentsWithNeighbours(){
        for (Agent eachAgent : agents) {
            eachAgent.clearNeighboursAndConnectionsLists();
        }
        for (Agent eachAgent : agents) {
            eachAgent.buildNeighboursFromCandidates();
        }
    }

//    public void printAgentNeighbours(Agent agent){
//        agent.printCandidateNeighboursIDs();
//    }

//    private void updateMapWithNewAgentPositions(){
//        map.updateMapWithAgentPositions(agents);
//    }

    private void updateMapWithNewAgentPosition(Agent agent, Position oldAgentPositions){
        map.updateMapWithNewAgentPosition(agent, oldAgentPositions);
    }

//    public void drawMap(){
//        map.draw();
//    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    //---------------------- MESSAGES -------------------------------------
    public void sendMessageByAgent(Integer agentID, String msgContent){
        Agent sender = getAgentFromListByID(agentID);
        Message msg = new Message(msgContent, sender, null, currentStep);
        sender.addNewMessage(msg);
        sender.sendMessageToNeighbours(msg.getID());
    }

    public void sendMessagesByAllAgents(){
        for (Agent agent : agents){
            agent.sendMessagesToNeighbours();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Map testMap = new Map();
        MapController controller = new MapController(testMap);
        View view = new View(controller);
        view.createFrame();
    }
}
