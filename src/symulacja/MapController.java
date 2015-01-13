package symulacja;

import java.util.ArrayList;

import static symulacja.Config.NUM_OF_AGENTS;

//klasa odpowiadajaca za obsluge mapy - klasa sterujaca, dzieki ktorej bedziemy wysylac wiadomosci,
//wymuszac nowe pozycje agentow itp. Cos w rodzaju kontrolera w modelu MVC. Mysle, ze przyda sie
//przy budowaniu widoku
public class MapController {

    private Map map;
    private ArrayList<Agent> agents;
    private PositionRandomizer positionRandomizer;

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

    private void addAgentToList(Agent agent){
        this.agents.add(agent);
    }

    public Agent getAgentFromList(int index){
        return agents.get(index);
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
            }
        }
        updateMapWithNewAgentPositions();
    }

    //dla kazdego agenta z listy robi nowa, aktualna liste wszystkich kandydatow na przyszle polaczenia
    public void setNewNeighboursToAgents(){
//        for (Agent eachAgent : agents){
//            map.makeListOfAgentNeighbours(eachAgent);
//        }
        Agent agent0 = agents.get(7);
        map.makeListOfAgentNeighbours(agent0);
    }

    public void printAgentNeighbours(Agent agent){
        agent.printNeighboursIDs();
    }

    private void updateMapWithNewAgentPositions(){
        map.updateMapWithAgentPositions(agents);
    }

    public void drawMap(){
        map.draw();
    }

    public static void main(String[] args){
        Map testMap = new Map();
        MapController controller = new MapController(testMap);
        Agent agent0 = controller.getAgentFromList(7);
        controller.drawMap();
        controller.setNewNeighboursToAgents();
        controller.printAgentNeighbours(agent0);
        controller.moveAgentsRandomly();
        System.out.println();
        System.out.println("Mapa po randomowym przesunieciu agentow:");
        controller.drawMap();
        controller.setNewNeighboursToAgents();
        controller.printAgentNeighbours(agent0);
    }
}
