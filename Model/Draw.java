package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Draw implements java.io.Serializable{
    private List<Integer> x_coor = new ArrayList<>();
    private List<Integer> y_coor = new ArrayList<>();
    private Color stroke_colour;
    private float stroke_thickness;
    private List<Long> time  = new ArrayList<>();


    //constructor
    Draw(Color c, float t) {
        this.stroke_colour = c;
        this.stroke_thickness = t;
    }

    public void addCoor(int x, int y,long moment) {
        x_coor.add(x);
        y_coor.add(y);
        time.add(moment);
    }

    public List<Integer> getX(){
        return this.x_coor;
    }

    public List<Integer> getY() {
        return this.y_coor;
    }
    public List<Long> getTime() {return this.time;}

    public int getSize() {
        return x_coor.size();
    }
//    public void setColour(Color col) {
//        stroke_colour = col;
//    }
//
//    public void setThickness(float thickness) {
//        stroke_thickness = thickness;
//    }
    public Color getColour() {
        return this.stroke_colour;
    }

    public float getThickness() {
        return this.stroke_thickness;
    }
}
