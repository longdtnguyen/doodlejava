package Model;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.*;


public class DoodleModel extends JComponent {

    //------------------Variables--------------//
    private ArrayList<IView> views = new ArrayList<IView>();        //list of model's Views
    private Color atm_colour = Color.BLACK;
    private int num_tick =0;
    private int num_point = 0;
    private float stroke_thickness  = 1f;
    private boolean clear_button = false;
    private boolean start_button = false, add_tick= false, running_button = false;
    private ArrayList<Draw> time_line = new ArrayList<>();
    private List<Integer> tick_table = new ArrayList<>();
    private int slider_location = 0;
    public ObjectOutputStream output;
    public ObjectInputStream input;
    private double scale_width,scale_height;
    private boolean scale_flag = false;
    private boolean new_file = false;
    public boolean stop_button = false;
    private boolean update_slider_only = false;
    private File current_file;
    //-----------------------------------------//

    //constructor
    public DoodleModel(){}


    //DOODLE CALLED THIS
    public void setScale(double width, double height) {
        scale_width = width;
        scale_height = height;
    }
    public double getScaleHeight() {
        return scale_height;
    }

    public double getScaleWidth() {
        return scale_width;
    }
    public void setScaleFlag(boolean flag) {
        scale_flag = flag;
        updateAllViews();
    }

    public boolean getScaleFlag() {
        return scale_flag;
    }

    //-----------Views manipulation------------//

    public void addView(IView view) {

        this.views.add(view);
        view.updateView();
    }

    public void removeView(IView view) {
        this.views.remove(view);
    }

    public void updateAllViews() {

        //this kind of syntax is magic... you dont mess with magic
        for (IView view: this.views) {
            view.updateView();
        }
    }
    //-----------------------------------------//



    //--------------------------------------Colour Pallate------------------------------------------//
    public void setColour(Color new_color) {
        this.atm_colour = new_color;
        updateAllViews();

    }

    public Color getColour() {
        return atm_colour;
    }
    //----------------------------------------------------------------------------------------------//





    //------------------------------------------Canvas--------------------------------------------//
    public void setStroke(float stroke) {
        this.stroke_thickness = stroke;
        updateAllViews();
    }

    public float getStroke() {
        return this.stroke_thickness;
    }

    public void setClearButton(boolean status) {
        clear_button = status;
        //if need to clear canvas, tell the canvas that it needs to clear
        updateAllViews();
    }

    public boolean getClearButton() {
        return clear_button;
    }

    //-------------------------------------------------------------------------------------------//







    //-------------------------------------------PlayBack---------------------------------------------//
    public void setUpdateSliderOnly(boolean state) {
        update_slider_only = state;
    }

    public boolean getUpdateSliderOnly() {
        return update_slider_only;
    }
    public int getNumTick() {
        return num_tick;
    }

    public void setRunningButton(boolean state) {
        running_button = state;
        updateAllViews();
    }
    public void setSliderLoc(int loc) {
        slider_location = loc;
    }
    public int getSliderLoc() {
        return slider_location;
    }
    public boolean getRunningButton() {
        return running_button;
    }

    public int getNumPoint() {
        return num_point;
    }

    public boolean getTick() {
        return add_tick;
    }
    public void setTick(boolean status) {
        add_tick = status;
        updateAllViews();
    }
    public boolean getStartButton() {
        return start_button;
    }

    public void setStartButton(boolean state) {
        start_button = state;
        updateAllViews();
    }
    public void addStroke() {
        Draw new_draw = new Draw(atm_colour,stroke_thickness);
        num_tick++;
        tick_table.add(num_point);
        time_line.add(new_draw);
    }

    public int getTickTableVal(int loc) {
        return this.tick_table.get(loc);
    }

    public void addTickTable() {

    }
    public void addStrokePoint(int x,int y,long moment) {
        num_point++;
        Draw temp = time_line.get(time_line.size()-1);      //gettin the current drawing stroke
        temp.addCoor(x,y,moment);
    }

    public ArrayList<Draw> getDrawArray() {
        return time_line;
    }
    //----------------------------------------------------------------------------------------------//





    //----------------------------------MenuBar----------------------------------------------------//

    public boolean getNewFile() {
        return new_file;
    }

    public File getCurrentFile() {
        return current_file;
    }
    public void setNewFile(boolean state) {
        new_file = state;
    }

    public void newFile() {
        atm_colour = Color.BLACK;
        num_tick =0;
        num_point = 0;
        stroke_thickness  = 1f;
        clear_button = false;
        start_button = false;
        add_tick= false;
        running_button = false;
        time_line = new ArrayList<>();
        tick_table = new ArrayList<>();
        slider_location = 0;
        scale_width= 0;
        scale_height = 0;
        scale_flag = false;
        new_file = true;
        update_slider_only = false;
        updateAllViews();
    }

    public File getFileName(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
       // fileChooser.setCurrentDirectory(new File("/Users/Long Nguyen/Desktop"));
        fileChooser.showOpenDialog(this);
       return fileChooser.getSelectedFile();
    }

    private void toFile(File file,BufferedWriter bufferedWriter) {
        try {
            String extension = file.getName();
            extension = extension.substring(extension.length() - 3, extension.length());

            if (extension.equals("txt")) {
                //---------start writing the file
                //setColour(new Color(Integer.parseInt(Integer.toString(Color.black.getRGB()))));

                bufferedWriter.write(Integer.toString(time_line.size()));
                bufferedWriter.newLine();
                for (int i = 0; i < time_line.size(); ++i) {
                    bufferedWriter.write(Integer.toString(time_line.get(i).getColour().getRGB()));      //colour
                    bufferedWriter.newLine();
                    bufferedWriter.write(Float.toString(time_line.get(i).getThickness()));      //thickness
                    bufferedWriter.newLine();
                    bufferedWriter.write(Integer.toString(time_line.get(i).getSize()));
                    bufferedWriter.newLine();
                    //writing the points
                    for (int j = 0; j < time_line.get(i).getSize(); ++j) {
                        List<Integer> x_coor = time_line.get(i).getX();
                        List<Integer> y_coor = time_line.get(i).getY();
                        List<Long> time = time_line.get(i).getTime();
                        bufferedWriter.write(Integer.toString(x_coor.get(j)) + " " +
                                Integer.toString(y_coor.get(j)) + " " +
                                Long.toString(time.get(j)));
                        bufferedWriter.newLine();
                    }
                }

                //numpoint
                bufferedWriter.write(Integer.toString(num_point));
                bufferedWriter.newLine();
                bufferedWriter.write(Integer.toString(num_tick));
                bufferedWriter.newLine();
                bufferedWriter.write(Integer.toString(slider_location));
                bufferedWriter.newLine();
                bufferedWriter.write(Integer.toString(tick_table.size()));
                bufferedWriter.newLine();
                for (int k = 0; k < tick_table.size(); ++k) {
                    bufferedWriter.write(Integer.toString(tick_table.get(k)));
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            } else {
                bufferedWriter.close();
                openFile(file);
                output.writeObject(time_line.size());
                for (int i = 0; i < time_line.size(); ++i) {
                    output.writeObject(time_line.get(i));
                }
                output.writeObject(num_point);
                output.writeObject(num_tick);
                output.writeObject(slider_location);
                output.writeObject(tick_table.size());
                for (int i = 0; i < tick_table.size(); ++i) {
                    output.writeObject(tick_table.get(i));
                }
                closeFile();
            }
        }catch (IOException e) {
            return;
        }
    }

    public void saveFile() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(current_file));

            bufferedWriter.close();
            toFile(current_file,bufferedWriter);
        }
        catch (IOException exception)
        {
            System.err.println("Error saving to new file.");
        }
    }

    public void saveFileAs() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()));
                current_file = fileChooser.getSelectedFile();

                toFile(fileChooser.getSelectedFile(),bufferedWriter);

            }
        }catch (IOException exception) {
            System.err.println("Error saving to new file.");
        }
    }

    public void closeFile() {
        try {
            if (output != null)
                output.close();
        }catch (IOException exception) {
            System.err.println("Error closing file");
            System.exit(1);
        }
    }

    public void openFile(File fileName) {
        try {
            output = new ObjectOutputStream(new FileOutputStream(fileName));

        }catch(IOException ioException) {
            System.err.println("Error loading file: "+fileName);
            return;
        }
    }

    public void loadFile(File fileName) {
        String extension = fileName.getName();
        extension = extension.substring(extension.length()-3,extension.length());
        newFile();
        //if the file is .txt
        if (extension.equalsIgnoreCase("txt")) {
            //GET THE FILE AND READ EACH LINE
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                line = br.readLine();
                int time_line_size = Integer.valueOf(line);  //read the time_line size

                //loop to add to timeline
                for (int i=0;i< time_line_size;++i) {
                    //get the color and thickness of the stroke
                    Color temp_color = new Color(Integer.valueOf(br.readLine()));
                    float temp_stroke = Float.valueOf(br.readLine());
                    Draw new_draw = new Draw(temp_color,temp_stroke);
                    int arr_size = Integer.valueOf(br.readLine());  //getting the coor array size
                    //add coor the the stroke
                    for (int j=0;j < arr_size;++j) {
                        line = br.readLine();
                        String[] s = line.split(" ");
                        new_draw.addCoor(Integer.valueOf(s[0]),Integer.valueOf(s[1]),Long.valueOf(s[2]));
                    }
                        time_line.add(new_draw);    //add the stroke to the timeline
                    }

                num_point = Integer.valueOf(br.readLine());
                num_tick = Integer.valueOf(br.readLine());
                slider_location = Integer.valueOf(br.readLine());
                int temp_tick_size = Integer.valueOf(br.readLine());
                for (int k =0;k< temp_tick_size;++k) {
                    tick_table.add(Integer.valueOf(br.readLine()));
                }
                setTick(true);
                update_slider_only = true;
                updateAllViews();
            }catch (IOException e){
                return;
            }

        //custom format
        }else {
            try {
                input = new ObjectInputStream(new FileInputStream(fileName));
                while (true) {
                    int time_line_size = (int) input.readObject();
                    for (int i = 0; i < time_line_size; ++i) {
                        time_line.add((Draw) input.readObject());
                    }
                    num_point = (int) input.readObject();
                    num_tick = (int) input.readObject();
                    slider_location = (int) input.readObject();
                    int tick_table_size = (int) input.readObject();
                    for (int i = 0; i < tick_table_size; ++i) {
                        tick_table.add((int) input.readObject());
                    }
                    setTick(true);
                    update_slider_only = true;
                    updateAllViews();
                }
            } catch (IOException exception) {
                //reach end of file, simply return to finish operation
                return;
            } catch (ClassNotFoundException classNotFoundException) {
                System.err.println("Unable to create object.");
            }
        }
    }

    //---------------------------------------------------------------------------------------------//
}
