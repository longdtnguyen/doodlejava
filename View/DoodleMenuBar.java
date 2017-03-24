package View;

import Model.DoodleModel;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

//not really a view class
public class DoodleMenuBar extends JMenuBar {
    private DoodleModel model;
    private JMenu view = new JMenu("View");
    private JMenu about = new JMenu("About");
    private JMenu file = new JMenu("File");
    private JRadioButton full_view = new JRadioButton("Full View");
    private JMenuItem fileNew = new JMenuItem("New");
    private JMenuItem fileSave = new JMenuItem("Save");
    private JMenuItem fileSaveAs = new JMenuItem("Save As..");
    private JMenuItem fileLoad = new JMenuItem("Load");
    private JMenuItem fileExit = new JMenuItem("Exit");

    public DoodleMenuBar(DoodleModel dmodel) {
        super();
        this.model = dmodel;
        makeMenuBar();
        this.registerControlers();
    }

    public void makeMenuBar() {
        //file

        file.add(fileNew);
        file.addSeparator();
        file.add(fileLoad);
        file.addSeparator();
        file.add(fileSave);
        file.add(fileSaveAs);
        file.addSeparator();
        file.add(fileExit);


        view.add(full_view);

        this.add(file);
        this.add(view);
        this.add(about);



    }


    //------------------------layout and controllers helpers------------------------------//
    private void registerControlers() {
        //
        about.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(null,
                        "Made by: Long Nguyen\n" +
                        "This Message is created in recognition of the " +
                        "professors MVP act by extending the assignment.\n" +
                        "Thank you for making this assignment happens.",
                        "About This Project",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });


        fileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int response = JOptionPane.showConfirmDialog(null, "Do you want to save your work before exiting?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION) {
                    System.exit(0);
                } else if (response == JOptionPane.YES_OPTION) {
                    model.saveFileAs();
                    System.exit(0);
                }
            }
        });

        fileNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int response = JOptionPane.showConfirmDialog(null, "Do you want to save your work?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION) {
                    model.newFile();
                } else if (response == JOptionPane.YES_OPTION) {
                    model.saveFileAs();
                }
            }
        });

        fileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File temp_file = model.getCurrentFile();
                if (temp_file == null) {
                    model.saveFileAs();
                }else {
                    model.saveFile();
                }
            }
        });

        fileSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.saveFileAs();
            }
        });
        fileLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File fileName = model.getFileName();
                if  (fileName != null){
                    model.loadFile(fileName);
                    model.closeFile();
                }
            }
        });

        full_view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setScaleFlag(true);
            }
        });
    }
}
