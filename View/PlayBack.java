package View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class PlayBack extends JPanel implements IView {

    private DoodleModel model;
    private JButton play = new JButton("Play");
    private JButton start = new JButton("Start");
    private JButton end = new JButton("End");
    private JSlider slider = new JSlider(1,100);
    private Hashtable label_table = new Hashtable();
    private int slider_pos =0;

    //constructor
    public PlayBack(DoodleModel dmodel) {
        super();
        this.model = dmodel;
        this.model.addView(this);

        this.layoutView();
        this.registerControlers();

    }

    //-----------------updateView---------------//
    public void updateView() {
        if (slider.getValue() == 0) model.stop_button= false;

        //new file update
        if (model.getNewFile()) {
            slider.setMaximum(100);
            slider.setValue(0);
            label_table = new Hashtable();
            model.setNewFile(false);
        }


        //add the slider tick label when loading new file
        if (model.getUpdateSliderOnly()) {
            label_table = new Hashtable();
            for (int i =0;i< model.getNumTick();++i) {
                label_table.put(model.getTickTableVal(i), new JLabel("|") );
                model.setUpdateSliderOnly(false);
            }
            slider.setLabelTable(label_table);
            slider.setPaintLabels(true);
        }

        //if the canvas signal that the stroke is done, need to update UI on the slider
        if (model.getTick()) {
            //update the max of the slider
            slider.setMaximum(model.getNumPoint());
            label_table.put(model.getTickTableVal(model.getNumTick()-1), new JLabel("|") );
            slider.setLabelTable(label_table);
            slider.setPaintLabels(true);
           //slider.setPaintLabels(true);
            model.setTick(false);
        }

        //if the playback is running, update the slider, disable buttons,
        // if not, enable it
        if (model.getRunningButton()) {
            slider_pos++;
            slider.setValue(slider_pos);
            start.setEnabled(false);
            end.setEnabled(false);
            slider.setEnabled(false);
        }else{
            start.setEnabled(true);
            end.setEnabled(true);
            slider.setEnabled(true);
        }

    }
    //------------------------------------------//


    //------------------------layout and controllers helpers------------------------------//
    private void layoutView() {
        this.setPreferredSize(new Dimension(1280,50));
        // THIS ORDER---- DO NOT CHANGE//
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(Box.createHorizontalStrut(70));
        this.add(play);
        this.add(Box.createHorizontalStrut(20));
        this.add(slider);
        this.add(Box.createHorizontalStrut(40));
        this.add(start);
        this.add(Box.createHorizontalStrut(10));
        this.add(end);
        this.add(Box.createHorizontalStrut(50));
        //----------------------------//


        this.slider.setValue(0);
        this.slider.setPaintTicks(true);
        //Create the label table
        slider.setLabelTable(label_table);



    }

    //controllers
    private void registerControlers() {
        this.play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.stop_button) model.stop_button = false;
                if (model.getRunningButton()) model.stop_button = true;

                model.setStartButton(true);
            }
        });

        this.start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int location = slider.getValue();
                //looking for the tick on the bar by finding the interval in the bar)
                for(int i = 0;i < model.getNumTick();++i) {
                    //if further than the last starting point -> to that point
                    if (location >= model.getTickTableVal(model.getNumTick()-1)) {
                        slider.setValue(model.getTickTableVal(model.getNumTick()-1));
                        model.setSliderLoc(model.getTickTableVal(model.getNumTick()-1));
                        break;
                    }else {
                        if ((model.getTickTableVal(i) <= location) &&
                            (model.getTickTableVal(i + 1) >= location)) {
                            slider.setValue(model.getTickTableVal(i));
                            model.setSliderLoc(model.getTickTableVal(i));
                            break;
                        }
                    }
                }
            }
        });

        this.end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //samething like start button
                int location = slider.getValue();
                for(int i = 0;i < model.getNumTick();++i) {
                    //if further than the last starting point -> to the end
                    if (location >= model.getTickTableVal(model.getNumTick()-1)) {
                        slider.setValue(model.getNumPoint());
                        model.setSliderLoc(model.getNumPoint());
                        break;
                    }else {
                        if ((model.getTickTableVal(i) <= location) &&
                                (model.getTickTableVal(i + 1) > location)) {
                            slider.setValue(model.getTickTableVal(i+1));
                           model.setSliderLoc(model.getTickTableVal(i+1));
                            break;
                        }
                    }
                }
            }
        });

        this.slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                slider_pos = slider.getValue();
                model.setSliderLoc(slider.getValue());
            }
        });
    }
    //-------------------------------------------------------------------------------//

}
