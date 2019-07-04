package app;

import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextEditor {
    private final String TITLE = "Basic Text Editor";
    private JFrame    frame;
    private JTextArea textArea;
    private File currentFile;

    public TextEditor() {
        initialise();
    }

    public void initialise() {
        frame = new JFrame("Basic Text Editor");
        frame.setBounds(100,100,850, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes program properly not just the window
        frame.setLayout(new BorderLayout());

        //create area to write and display text
        textArea = new JTextArea(20,60);
        frame.add(textArea, BorderLayout.NORTH);

        //Scroll pane for when text in the text area gets too big
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        //Options to be displayed
        JMenuBar menuBar = new JMenuBar();
        frame.add(menuBar, BorderLayout.NORTH);

        //Create a "File" option to be added onto the menu
        JMenu fileOptions = new JMenu("File");
        menuBar.add(fileOptions);

        frame.setVisible(true); //makes everything in the JFrame visible

        //Action for opening file
        JMenuItem openFile = new JMenuItem("Open");
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        //Action for saving a new file
        JMenuItem saveAs = new JMenuItem("Save As");
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        });

        //Action for saving file
        JMenuItem saveFile = new JMenuItem("Save");
        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        //Action for closing file
        JMenuItem closeFile = new JMenuItem("Close");
        closeFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeFile();
            }
        });

        //Add options to the file menu to form a dropdown
        fileOptions.add(openFile);
        fileOptions.add(saveAs);
        fileOptions.add(saveFile);
        fileOptions.add(closeFile);

    }

    //Method to open the file
    public void openFile() {
        try {
            JFileChooser fc = new JFileChooser();
            // filters for .txt files so the user does not open any other type of file
            FileFilter txtFilter = new FileNameExtensionFilter("Plain Text", "txt");
            fc.setFileFilter(txtFilter);

            fc.setDialogTitle("Select a Text File to open");
            fc.showOpenDialog(null);

            //get the user's selected file and check if it exists
            currentFile = fc.getSelectedFile();
            if (!currentFile.exists()) {
                JOptionPane.showMessageDialog(null,"File does not exist","Error",JOptionPane.ERROR_MESSAGE);
                currentFile = null;
                return;
            }

            //Display the selected file onto the text area
            BufferedReader br = new BufferedReader(new FileReader(currentFile));
            textArea.read(br,null); //reads the selected file onto the screen
            br.close();

            frame.setTitle(TITLE+" - "+currentFile.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method to save a new file
    public void saveAs() {
        try {
            JFileChooser fc = new JFileChooser();
            // filters for .txt files so the user does not open any other type of file
            FileFilter txtFilter = new FileNameExtensionFilter("Plain Text", "txt");
            fc.setFileFilter(txtFilter);

            fc.setDialogTitle("Save new file");
            fc.showSaveDialog(null);

            //create new file for contents to be saved in
            currentFile = fc.getSelectedFile();
            FileWriter fw = new FileWriter(currentFile);
            BufferedWriter bw = new BufferedWriter(fw);
            textArea.write(bw);
            bw.close();

            frame.setTitle(TITLE+" - "+currentFile.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method to save the file
    public void saveFile() {
        try {
            //Check if the file exist
            //if not alert the user and call the saveAs method to create a new file for it to be saved in
            if(currentFile == null){
                JOptionPane.showMessageDialog(null,"File Does not Exist \nSave new File","Error",JOptionPane.ERROR_MESSAGE);
                saveAs();
            }

            FileWriter fw = new FileWriter(currentFile);
            BufferedWriter bw = new BufferedWriter(fw);
            textArea.write(bw);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method to close the file
    public void closeFile() {
        //Check if their is a file to be closed
        if(currentFile == null){
            JOptionPane.showMessageDialog(null,"Failed to Close \nNo file opened","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            //Give the user an option to save before closing the file
            int option = JOptionPane.showConfirmDialog(null, "Save before you close?","Save?",JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION){
                saveFile();
            }
            //resets / clears the text editor
            textArea.setText("");
            currentFile = null;
            frame.setTitle(TITLE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TextEditor();
    }

}



