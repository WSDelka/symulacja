package symulacja;

import java.util.ArrayList;
import java.util.Arrays;

import static symulacja.Config.CONNECTION_RANGE;
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

    //zwraca agenta z mapy o podanym ID
    //jesli nie ma takiego ID - zwraca null
    public Agent getAgentAtMapByID(int agentID){
        for (Agent[] agentsRow : map){
            for (int i = 0; i < agentsRow.length; i++) {
                if (agentID == agentsRow[i].getID()){
                    return agentsRow[i];
                }
            }
        }
        return null;
    }

    public void makeListOfCandidatesAgentNeighbours(Agent agent){
        Position agentPosition = agent.getPositions();
        agent.setNewCandidates(getAgentsNearPosition(agentPosition));

    }

    //sprawdzam kwadrat wokol danej pozycji (wiecej opisu w configu przy opisie CONNECTION_RANGE)
    //gdzies tu blad? :< nie dodaje wszystkich agentow mimo ze powinien :<
    private ArrayList<Agent> getAgentsNearPosition(Position position){
        ArrayList<Agent> nearAgents = new ArrayList<Agent>();
        int positionY = position.getY();
        int positionX = position.getX();
        for (int i = positionY-CONNECTION_RANGE; i <= positionY+CONNECTION_RANGE; i++){
            for (int j = positionX-CONNECTION_RANGE; j <= positionX+CONNECTION_RANGE; j++){
                if (i>=0 && i<MAP_HEIGHT && j>=0 && j<MAP_WIDTH){
                    if (map[i][j] != null && (i != positionY || j != positionX)){
                        nearAgents.add(map[i][j]);
                    }
                }
            }
        }
        return nearAgents;
    }

    public void addNewAgentToMap(Agent agent){
        Position agentPosition = agent.getPositions();
        this.map[agentPosition.getY()][agentPosition.getX()] = agent;
    }

    public boolean isAgentCollisionAtMap(Position candidatePosition){
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
