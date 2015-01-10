package symulacja;

public class CoordinatePair {

    private int x;
    private int y;

    public CoordinatePair(int beginX, int beginY){
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
}
