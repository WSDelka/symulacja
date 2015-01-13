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
            } while(map.isAgentsPositionCollision(agentPositions));
            Agent newAgent = new Agent(agentPositions);
            map.addNewAgentToMap(newAgent);
            addAgentToList(newAgent);
        }
    }

    private void addAgentToList(Agent agent){
        this.agents.add(agent);
    }

    public void moveAgentsRandomly(){
        for (Agent eachAgent : agents){
            Position beginningAgentPosition = eachAgent.getPositions();
            Position candidatePosition;
            do {
                candidatePosition = positionRandomizer
                        .randomizeDependsOnOldPosition(beginningAgentPosition.getX(), beginningAgentPosition.getY());
            } while(map.isAgentsPositionCollision(candidatePosition));
            eachAgent.setNewPosition(candidatePosition);
        }
        updateMapWithNewAgentPositions();
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
        controller.drawMap();
        controller.moveAgentsRandomly();
        for (int i = 0; i < 1; i++) {
            System.out.println();
        }
        System.out.println("Mapa po randomowym przesunieciu agentow:");
        controller.drawMap();
    }
}
