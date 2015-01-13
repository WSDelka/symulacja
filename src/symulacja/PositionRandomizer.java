package symulacja;

import java.util.Random;

import static symulacja.Config.MAP_HEIGHT;
import static symulacja.Config.MAP_WIDTH;
import static symulacja.Config.RANGE_OF_MOVE;

public class PositionRandomizer {

    private Random rand;

    public PositionRandomizer(){
        this.rand = new Random();
    }

    public Position drawNewPositions(){
        int agentX = rand.nextInt(MAP_WIDTH);
        int agentY = rand.nextInt(MAP_HEIGHT);
        return new Position(agentX, agentY);
    }


    public Position randomizeDependsOnOldPosition(int oldX, int oldY){
        int diffOfX, diffOfY, newX, newY;
        do {
            diffOfX = getRandomIntForRangeMove();
            newX = oldX + diffOfX;
        } while (newX < 0 || newX >= MAP_WIDTH);
        do {
            diffOfY = getRandomIntForRangeMove();
            newY = oldY + diffOfY;
        } while (newY < 0 || newY >= MAP_HEIGHT);
        return new Position(newX, newY);
    }

    //te dodawanie i odejmowanie, powoduje ze losowanie bedzie z przedzialu <-RANGE_OF_MOVE, RANGE_OF_MOVE>
    //czyli np dla RANGE_OF_MOVE = 3 losowanie bedzie z przedzialu <0, 4> (bo on 5 nie wlicza) -2 czyli <-2, 2>
    //pogmatwane troche wiem, ale no takie api jest wystawione ;ps
    private int getRandomIntForRangeMove(){
        return rand.nextInt(RANGE_OF_MOVE+RANGE_OF_MOVE-1)-RANGE_OF_MOVE+1;
    }

    //losowanie - 0 lub 1 czy agent powinien sie ruszac
    public boolean isAgentHasToMove(){
        int result = rand.nextInt(2);
        return (result == 1) ? true : false;
    }
}
