package nl.tudelft.jpacman.score;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * JFrame Hall of Frame
 * Created by Jannou on 2/03/16.
 */
public class ScoreUI extends JFrame{
    private static JFrame frmScore;

    protected ScoreUI(){
        initialize();
    }
    private void initialize() {
        frmScore = new JFrame();
        frmScore.setTitle("Hall of Fame");
        //frmScore.setVisible(true);
    }


//
//    /**
//     *
//     */
//    private static final long serialVersionUID = 879938052274070218L;
//    private JPanel contentPane = new JPanel();
//    //private JPanel contentPane2 = new MyScorePanel();
//    private JPanel contentPane3 = new JPanel();
//
//    /**
//     * Create the frame.
//     */
//    protected ScoreUI() {
//        initialize();
//    }
//
//    /**
//     * Frame
//     */
//    private void initialize(){
//        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        setTitle("Hall of Fame");
//        setBounds(100, 100, 450, 400);
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        contentPane.setLayout(new BorderLayout(0, 0));
//        contentPane3.setLayout(new BorderLayout(0, 0));
//        setContentPane(contentPane);
//        getContentPane().add(contentPane2, BorderLayout.NORTH);
//        JButton okButton = new JButton("OK");
//        okButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("yeap");
//            }
//        });
//        JButton resetButton = new JButton("Reset");
//        resetButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                HandleScore.rest();
//            }
//        });
//        contentPane3.add(okButton, BorderLayout.WEST);
//        contentPane3.add(resetButton, BorderLayout.EAST);
//        getContentPane().add(contentPane2, BorderLayout.NORTH);
//        getContentPane().add(contentPane3, BorderLayout.SOUTH);
//        setVisible(true);
//    }

}
