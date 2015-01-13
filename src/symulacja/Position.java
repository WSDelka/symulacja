package symulacja;

public class Position {

    private int x;
    private int y;

    public Position(int beginX, int beginY){
        this.x = beginX;
        this.y = beginY;
    }

    public void setX(int newX){
        this.x = newX;
    }

    public void setY(int newY){
        this.y = newY;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void updateOld(int diffOfX, int diffOfY){
        x = x + diffOfX;
        y = y + diffOfY;
    }
}
