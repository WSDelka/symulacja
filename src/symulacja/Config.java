package symulacja;

public class Config {

    public static final int MAP_WIDTH = 30;
    public static final int MAP_HEIGHT = 10;

    public static final int NUM_OF_AGENTS = 9;

    //oznacza to, ze bedzie losowal z wartosci o 1 mniejszej, bo nie bierze on
    //po uwage gornej wartosci (czyli dla 3 losuje z <0,2>)
    public static final int RANGE_OF_MOVE = 3;

    public static final int NEIGHBOURS_CAPACITY = 5;

    public static final int CONNECTION_RANGE = 5;

    public static final int TIME_OF_ROUND_IN_MS = 5000;
}
