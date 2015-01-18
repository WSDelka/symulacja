package symulacja;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.view.mxGraphSelectionModel;

import javax.swing.text.JTextComponent;

public class EventSelectAgentListener implements mxEventSource.mxIEventListener {

    private JTextComponent textPane;

    public EventSelectAgentListener(JTextComponent textPane) {
        this.textPane = textPane;
    }

    @Override
    public void invoke(Object sender, mxEventObject evt) {
        mxGraphSelectionModel model = (mxGraphSelectionModel) sender;
        mxCell vertex = (mxCell) model.getCell();
        if (vertex != null && vertex.isVertex()) {
            Agent agent = (Agent) vertex.getValue();
            textPane.setText("Agent "+agent.getID()+"\nSąsiedzi: "+agent.getNeighbours()+"\nPosiadane wiadomości: "+agent.getMessagesListOfIDS());
        } else {
            textPane.setText("");
        }
    }
}
