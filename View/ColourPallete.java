package View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Model.*;

public class ColourPallete extends JPanel implements IView {
    private DoodleModel model;
    //-------------ALL DAT BUTTONS--------------//
    private JPanel colour_panel = new JPanel();
    private JButton black = new JButton();
    private JButton red = new JButton();
    private JButton blue = new JButton();
    private JButton green = new JButton();
    private JButton yellow = new JButton();
    private JButton cyan = new JButton();
    private JButton magenta= new JButton();
    private JButton gray = new JButton();
    private JButton pink = new JButton();
    private JButton custom1 = new JButton();
    private JButton colour_display = new JButton();
    //-------------------------------------------//

    private JSlider paint_stroke = new JSlider(JSlider.VERTICAL,1,11,1);
    private JButton clear_button = new JButton("clear");

    //constructor
    public ColourPallete(DoodleModel dmodel) {
        super();

        this.model = dmodel;
        this.model.addView(this);

        this.setSize(new Dimension(60,10));
        this.layoutView();
        this.registerControllers();
    }

    //-----------------updateView---------------//
    public void updateView() {
        //nothing to update
        fillButton(colour_display,model.getColour());
    }
    //------------------------------------------//


    //------------------------layout and controllers helpers------------------------------//
    private void layoutView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(80,500));
        setupPallete();
        colour_display.setPreferredSize(new Dimension(50,50));

        colour_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        colour_display.setAlignmentX(Component.LEFT_ALIGNMENT);
        paint_stroke.setAlignmentX(Component.LEFT_ALIGNMENT);
        clear_button.setAlignmentX(Component.LEFT_ALIGNMENT);
        paint_stroke.setPreferredSize(new Dimension(80,100));

        this.add(colour_panel);
        colour_display.setPreferredSize(new Dimension(120,120));
        this.add(Box.createVerticalStrut(5));
        this.add(colour_display);
        this.add(Box.createVerticalGlue());
        this.add(paint_stroke);
        this.add(Box.createVerticalStrut(5));
        this.add(clear_button);
        //displaying the current colour
        colour_display.setEnabled(false);
        fillButton(colour_display,model.getColour());

        paint_stroke.setMajorTickSpacing(2);
        paint_stroke.setPaintTicks(true);
        paint_stroke.setSnapToTicks(true);

    }


    //---------color pallete----------//
    private void fillButton(JButton button,Color bcolor) {
        button.setBackground(bcolor);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
    }

    private void setupPallete() {
        Dimension d = new Dimension();
        d.width = 20;
        d.height = 20;
        fillButton(black,Color.black);
        fillButton(green,Color.green);
        fillButton(blue,Color.blue);
        fillButton(pink,Color.pink);
        fillButton(magenta,Color.magenta);
        fillButton(red,Color.red);
        fillButton(cyan,Color.cyan);
        fillButton(gray,Color.gray);
        fillButton(yellow,Color.yellow);
        colour_panel.setPreferredSize(new Dimension(40,120));
        colour_panel.setLayout(new GridLayout(5,2));

        colour_panel.add(black);
        black.setPreferredSize(d);
        colour_panel.add(red);
        red.setPreferredSize(d);
        colour_panel.add(blue);
        blue.setPreferredSize(d);
        colour_panel.add(green);
        green.setPreferredSize(d);
        colour_panel.add(yellow);
        yellow.setPreferredSize(d);
        colour_panel.add(cyan);
        cyan.setPreferredSize(d);
        colour_panel.add(magenta);
        magenta.setPreferredSize(d);
        colour_panel.add(gray);
        gray.setPreferredSize(d);
        colour_panel.add(pink);
        pink.setPreferredSize(d);
        colour_panel.add(custom1);
        custom1.setPreferredSize(d);




    }
    //--------------------------------------//

    //controllers
    private void registerControllers() {

        //slider
        this.paint_stroke.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float thickness = paint_stroke.getValue();
                model.setStroke(thickness);
            }
        });

        //clear button
        this.clear_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setClearButton(true);
            }
        });

        //color pallete
        this.black.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                model.setColour(Color.black);
            }
        });

        this.red.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                model.setColour(Color.red);
            }
        });

        this.blue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                model.setColour(Color.blue);
            }
        });

        this.green.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                model.setColour(Color.green);
            }
        });

        this.yellow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                model.setColour(Color.yellow);
            }
        });

        this.cyan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                model.setColour(Color.cyan);
            }
        });
        this.gray.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                model.setColour(Color.gray);
            }
        });
        this.magenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                model.setColour(Color.magenta);
            }
        });
        this.pink.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                model.setColour(Color.pink);
            }
        });

        this.custom1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color old_colour = custom1.getBackground();
                Color new_colour = JColorChooser.showDialog(null, "Pick a Color",
                        old_colour);
                if (new_colour != null) {
                    custom1.setBackground(new_colour);
                    model.setColour(new_colour);
                }

            }
        });
    //---------------------------------------------------------------------------------------//







    }


}
