import javax.swing.*;
import Model.*;
import View.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


//Main class that run the games
public class Doodle extends JFrame {
    private DoodleModel model;

    private double scale_width,scale_height;
    //Constructor
    Doodle() {
        super();
        setTitle("So You Think You Can Doodle?");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //-------------------//


        model = new DoodleModel();

        DoodleMenuBar mbview = new DoodleMenuBar(model);
        ColourPallete cpview = new ColourPallete(model);
        PaintCanvas pcanvas = new PaintCanvas(model);
        PlayBack pbview =new PlayBack(model);

        this.setJMenuBar(mbview);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(cpview, BorderLayout.WEST);
        this.getContentPane().add(pcanvas, BorderLayout.CENTER);
        this.getContentPane().add(pbview, BorderLayout.SOUTH);
        //-----------------------------//
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(pcanvas);
        this.add(scrollPane);
        this.pack();

        //this.setMinimumSize(this.getSize());
        //setSize(1280,720);  //720 for now
        setLocationRelativeTo(null);    //centre the window frame
        setResizable(true);
        setVisible(true);
        //SwingUtilities.updateComponentTreeUI(this);
        //----------------------------//
//        this.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                super.componentResized(e);
//                Dimension dim = e.getComponent().getBounds().getSize();
//                scale_width = (double) dim.width/ (double) Constants.APP_WIDTH;
//                scale_height = (double) dim.height/(double) Constants.APP_HEIGHT;
//                model.setScale(scale_width,scale_height);
//            }
//        });

    }



    //main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Doodle doodle = new Doodle();
                doodle.setVisible(true);
            }
        });
    }
}
