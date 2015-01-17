package symulacja;

import static symulacja.Config.*;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class View {

    private mxGraphComponent graphComponent;
    private JTextComponent textPane;
    private MapController controller;
    private java.util.Map<Integer, mxCell> vertices;

    public View(MapController controller) {
        this.controller = controller;
        mxGraph graph = new mxGraph();
        graphComponent = new mxGraphComponent(graph);
        vertices = createVerticesMap(graph);
        textPane = new JTextPane();
    }

    public void createFrame()
    {
        JFrame frame = new JFrame("Symulacja");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(createMenu());
        graphComponent.setMinimumSize(new Dimension(MAP_CELL_SIZE * MAP_WIDTH, MAP_CELL_SIZE * MAP_HEIGHT));
        frame.setPreferredSize(new Dimension(600, 600));
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, graphComponent, new JScrollPane(textPane));
        splitPane.setOneTouchExpandable(true);
        frame.add(splitPane, BorderLayout.CENTER);
        frame.pack();
        setScreenCenterLocation(frame);
        frame.setVisible(true);
    }

    public JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu symulacja = new JMenu("symulacja");
        JMenuItem item = new JMenuItem("Wykonaj 1 krok");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setNewNeighboursToAgents();
                controller.moveAgentsRandomly();
                updateVertices();
            }
        });
        symulacja.add(item);
        menuBar.add(symulacja);
        return menuBar;
    }

    private void setScreenCenterLocation(JFrame frame) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    private java.util.Map<Integer, mxCell> createVerticesMap(mxGraph graph) {
        java.util.Map<Integer, mxCell> vertices = new HashMap<Integer, mxCell>();
        Object parent = graph.getDefaultParent();
        for (Agent agent : controller.getAgents()) {
            Position positions = agent.getPositions();
            Integer id = agent.getID();
            mxCell vertex = (mxCell) graph.insertVertex(parent, id.toString(), id,
                    positions.getX() * MAP_CELL_SIZE, positions.getY() * MAP_CELL_SIZE, VERTEX_SIZE, VERTEX_SIZE);
            vertices.put(id, vertex);
        }
        return vertices;
    }

    private void updateVertices() {
        for (Agent agent : controller.getAgents()) {
            mxCell vertex = vertices.get(agent.getID());
            updatePositions(vertex, agent.getPositions());
        }
        graphComponent.refresh();
    }

    private void updatePositions(mxCell vertex, Position positions) {
        mxGeometry geometry = vertex.getGeometry();
        geometry.setX(positions.getX() * MAP_CELL_SIZE);
        geometry.setY(positions.getY() * MAP_CELL_SIZE);
    }
}
