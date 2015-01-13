package symulacja;

//Klasa reprezentujaca polaczenie miedzy dwoma agentami 1 -> 2 (potrzebna bo musimy
//reprezentowac stan polaczenia, ze wyszlo od agenta 1 i zeby agent 2 sobie go nie wyrzucil
//Niestety sprawi to tez dublowanie i jednoczesne dodawanie ich do list, co moze byc problematyczne :<
//Trzeba jeszcze to dokladnie przemyslec //L.W.
public class Connection {

    private ConnectionState state;
    private Agent initiator;
    private Agent receiver;

    public Connection(Agent initiator, Agent receiver){
        this.initiator = initiator;
        this.receiver = receiver;
        this.state = ConnectionState.INITIATED;
    }

    public void setStateToOutdated(){
        this.state = ConnectionState.OUTDATED;
    }

    public ConnectionState getState(){
        return this.state;
    }

    public Agent getReceiver(){
        return this.receiver;
    }

}
