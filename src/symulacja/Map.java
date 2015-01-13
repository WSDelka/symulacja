package symulacja;

import java.util.ArrayList;
import java.util.Arrays;

import static symulacja.Config.MAP_HEIGHT;
import static symulacja.Config.MAP_WIDTH;

public class Map {

    private Agent[][] map;

    public Map(){
        initMap();
    }

    private void initMap(){
        this.map = new Agent[MAP_HEIGHT][MAP_WIDTH];
        fillMapWithNulls();
    }

    private void fillMapWithNulls(){
        for (Agent[] row : map){
            Arrays.fill(row, null);
        }
    }

    public Agent getAgentAtMapPosition(int x, int y){
        return map[y][x];
    }

    public void addNewAgentToMap(Agent agent){
        Position agentPosition = agent.getPositions();
        this.map[agentPosition.getY()][agentPosition.getX()] = agent;
    }

    public boolean isAgentsPositionCollision(Position candidatePosition){
        int candidateX = candidatePosition.getX();
        int candidateY = candidatePosition.getY();
        if (map[candidateY][candidateX]!=null){
            return true;
        } else {
            return false;
        }
    }

    public void updateMapWithAgentPositions(ArrayList<Agent> agents){
        fillMapWithNulls();
        for (Agent agent : agents){
            addNewAgentToMap(agent);
        }
    }

    //metoda testowa rysujaca plansze i agentow w konsoli
    //cyfra oznacza id agenta
    public void draw(){
        drawWidthBorder();
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH + 2; j++) {
                if(j==0 || j==MAP_WIDTH+1){
                    drawHeightBorder();
                } else {
                    if (map[i][j-1] != null){
                        int agentID = map[i][j-1].getID();
                        System.out.print(agentID);
                    } else {
                        System.out.print(" ");
                    }
                }
            }
            System.out.print("\n");
        }
        drawWidthBorder();
    }

    private void drawWidthBorder(){
        for (int i = 0; i < MAP_WIDTH + 2; i++) {
            System.out.print("-");
        }
        System.out.print("\n");
    }

    private void drawHeightBorder(){
        System.out.print("|");
    }

}
