package symulacja;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static symulacja.Config.MAP_HEIGHT;
import static symulacja.Config.MAP_WIDTH;
import static symulacja.Config.NUM_OF_AGENTS;

public class Map {

    private Agent[][] map;
    private ArrayList<Agent> agents;
    private int height;
    private int width;

    private Random rand;

    public Map(){
        this.agents = new ArrayList<Agent>();
        this.rand = new Random();
        initMap();
    }

    private void initMap(){
        this.height = MAP_HEIGHT;
        this.width = MAP_WIDTH;
        this.map = new Agent[height][width];
        fillMapWithNulls();
        randomlySetAgents();
    }

    private void fillMapWithNulls(){
        for (Agent[] row : map){
            Arrays.fill(row, null);
        }
    }

    private void randomlySetAgents(){
        for (int i = 0; i < NUM_OF_AGENTS; i++){
            CoordinatePair agentCoordinates = drawNewPositions();
            while (null != map[agentCoordinates.getY()][agentCoordinates.getX()]){
                agentCoordinates = drawNewPositions();
            }
            Agent newAgent = new Agent(agentCoordinates);
            this.agents.add(newAgent);
        }
    }

    private CoordinatePair drawNewPositions(){
        int agentX = rand.nextInt(MAP_WIDTH);
        int agentY = rand.nextInt(MAP_HEIGHT);
        return new CoordinatePair(agentX, agentY);
    }
}
