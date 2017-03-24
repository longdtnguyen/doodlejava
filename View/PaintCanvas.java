package View;

import Model.Constants;
import Model.DoodleModel;
import Model.Draw;
import Model.IView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class PaintCanvas extends JComponent implements IView {

    private DoodleModel model;
    private int current_X, current_Y, old_X, old_Y;
    private Graphics2D g2d;
    private Image image;
    private long moment;
    private int scale_width,scale_height;

    //constructor
    public PaintCanvas(DoodleModel dmodel) {
        super();
        setDoubleBuffered(true);
        setIgnoreRepaint(true);
        this.model = dmodel;
        this.model.addView(this);           //add the view to the model
        this.setPreferredSize(new Dimension(1280,720));
        this.layoutView();
        this.registerControlers();

//        this.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                super.componentResized(e);
//                System.out.println("resizing");
//                Dimension dim = e.getComponent().getBounds().getSize();
//
//                scale_height = (int)dim.getHeight();
//                scale_width = (int) dim.getWidth();
//            }
//        });
    }

    //-----------------updateView---------------//
    public void updateView() {

        //signal need to resize
//        if (model.getScaleFlag()) {
//            System.out.println("scaling");
//            this.setSize(new Dimension(scale_width,scale_height));
//            image.getScaledInstance((int)model.getScaleWidth(),(int)model.getScaleHeight(),Image.SCALE_DEFAULT);
//            g2d.scale(model.getScaleWidth(),model.getScaleHeight());
//            model.setScaleFlag(false);
//            this.revalidate();
//            this.repaint();
//        }
        //pressed clear button
        if(model.getClearButton()) {
            clear();
            model.setClearButton(false);
        }

        if(model.getNewFile()) {
            clear();
        }

        //pressed replay button
        if(model.getStartButton()) {
            model.setStartButton(false);
            model.setRunningButton(true);
            //redrawing the canvas according to the location
            int where_is_slider = model.getSliderLoc();
            int what_stroke = 0;
            int what_point = 0;
            //finding what stroke to draw
            for (int i = 0;i< model.getNumTick();++i) {
                if (where_is_slider < model.getTickTableVal(i)) {
                    what_stroke = i-1;
                    what_point = where_is_slider - model.getTickTableVal(i-1);
                    break;
                }else {
                    if (where_is_slider > model.getTickTableVal(model.getNumTick()-1)) {
                        what_stroke = model.getNumTick() - 1;
                        what_point = where_is_slider - model.getTickTableVal(model.getNumTick() -1);
                    }
                }
            }
            if (what_point == 0) what_point = 1;
            clear();
            rewind(what_stroke,what_point);

        }
    }
    //------------------------------------------//



    //------------layout and controllers helpers---------------------------//
    private void layoutView() {
        //this.setPreferredSize(new Dimension(Constants.APP_WIDTH,Constants.APP_HEIGHT));
    }

    private void registerControlers() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                //saving the old coor
                if (!model.getRunningButton()) {
                    old_X = e.getX();
                    old_Y = e.getY();
                    moment = System.currentTimeMillis();
                    //model.updateCoor(e.getX(),e.getY(),false);
                    model.addStroke();
                    model.addStrokePoint(e.getX(), e.getY(), moment);
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (!model.getRunningButton()) {
                    model.addTickTable();
                    model.setTick(true);
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if(!model.getRunningButton()) {
                    current_X = e.getX();
                    current_Y = e.getY();
                    moment = System.currentTimeMillis();
                    // model.updateCoor(e.getX(),e.getY(),true);
                    model.addStrokePoint(e.getX(), e.getY(), moment);

                    if (g2d != null) {
                        g2d.setPaint(model.getColour());
                        g2d.setStroke(new BasicStroke(model.getStroke()));
                        g2d.drawLine(old_X, old_Y, current_X, current_Y);
                        repaint();
                        old_X = current_X;
                        old_Y = current_Y;
                    }
                }
            }
        });
    }
    //---------------------------------------------------------------------//


    //------------------------PAINT COMPONENT-----------------------------//
    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            // image to draw null ==> we create
            super.paintComponent(g);
            g2d = (Graphics2D) g;

            image = createImage(getSize().width, getSize().height);
            g2d = (Graphics2D) image.getGraphics();
            // enable antialiasing
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // clear draw area
            clear();
        }

        g.drawImage(image, 0, 0, null);
    }

    // now we create exposed methods
    public void clear() {
        g2d.setPaint(Color.white);
        // draw white on entire draw area to clear
        g2d.fillRect(0, 0, getSize().width, getSize().height);
        g2d.setPaint(model.getColour());
        repaint();
    }
    //------------------------PAINT COMPONENT-----------------------------//

    //--------------------------------------------//
    private void rewind(int strokeID, int pointID) {
        new Thread() {

            public void run() {
                //clear();
                boolean break_flag = false;
                boolean flag = false;
                int start_point = pointID;
                ArrayList<Draw> drawing = model.getDrawArray();
                long sleep_between_stroke = 0;
                for (int d= strokeID;d< drawing.size();++d) {
                    List<Integer> temp_x = drawing.get(d).getX();
                    List<Integer> temp_y = drawing.get(d).getY();
                    List<Long> cur_time = drawing.get(d).getTime();
                    if(flag) {
                        start_point = 1;
                    }
                    //sleep according to the time interval between points
                    for (int i = start_point; i < temp_x.size(); ++i) {
                        flag = true;
                        if (model.stop_button) {
                            break_flag = true;
                            break;
                        }
                        try {
                            long sleep_time;
                            //first one of the stroke -> sleep time taken from the last stroke
                            if (i == 1) {
                                sleep_time = cur_time.get(i) - sleep_between_stroke;
                            }else {
                                sleep_time = cur_time.get(i) - cur_time.get(i - 1);
                            }
                            //if the stroke delay is too long, cut it down to 1s
                            if (sleep_time > 1000) sleep_time = 1000;
                            Thread.sleep(sleep_time);

                        }catch (Exception e) {
                            System.out.println("Too much, I am gonna die now");
                        }

                        //checking for update in the model
                        checkUpdate();
                        g2d.setPaint(drawing.get(d).getColour());
                        g2d.setStroke(new BasicStroke(drawing.get(d).getThickness()));
                        g2d.drawLine(temp_x.get(i - 1), temp_y.get(i - 1), temp_x.get(i), temp_y.get(i));
                        repaint();
                    }
                    if (break_flag) break;
                    sleep_between_stroke = cur_time.get(cur_time.size()-1);
                }
                model.setRunningButton(false);
               // model.stop_button = false;
            }

            private void checkUpdate() {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        model.updateAllViews();
                    }
                });
            }
        }.start();


    }
}

