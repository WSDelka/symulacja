package symulacja;

public class Config {

    public static final int MAP_WIDTH = 10;
    public static final int MAP_HEIGHT = 10;

    public static final int NUM_OF_AGENTS = 9;

    //oznacza to, ze bedzie losowal z wartosci o 1 mniejszej, bo nie bierze on
    //po uwage gornej wartosci (czyli dla 3 losuje z <0,2>)
    public static final int RANGE_OF_MOVE = 3;

    public static final int NEIGHBOURS_CAPACITY = 5;

    //zakladamy, ze okrag aproksymujemy kwadratem xD
    //agent dla ktorego sprawdzamy zasieg znajduje sie w samym srodku kwadratu
    //czyli np dla CONNECTION_RANGE = 2 mamy taka mapke (x to pozycja agenta, - to obejmowany obszar)
    // z lewej strony dla 2, z prawej dla 1:
    //                  -----
    //                  -----           ---
    //                  --x--           -x-
    //                  -----           ---
    //                  -----
    //wiem ze to wyglada raczej jak prostokat ale tak to graficznie wyglada ;p
    public static final int CONNECTION_RANGE = 3;

    public static final int TIME_OF_ROUND_IN_MS = 5000;

    //stałe związane z widokiem
    public static final int MAP_CELL_SIZE = 40;
    public static final int VERTEX_SIZE = 30;

    public static final int CONNECTIONS_NUMBER = 4;
}
