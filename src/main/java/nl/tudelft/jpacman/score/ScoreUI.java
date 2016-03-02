package nl.tudelft.jpacman.score;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * Created by Jannou on 2/03/16.
 */
public class ScoreUI extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 879938052274070218L;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    protected ScoreUI() {
        initialize();
    }

    /**
     * Frame
     */
    private void initialize(){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Settings");
        // setBounds(100, 100, 450, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        // getContentPane().add(new ScorePanel(this), BorderLayout.NORTH);
        //Dimension dim = getContentPane().getComponent(0).getPreferredSize();
        //setBounds(100, 100, dim.width + 30, dim.height + 45);
        //setVisible(true);
    }

}
