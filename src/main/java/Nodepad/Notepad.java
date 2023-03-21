/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




package Nodepad;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Ahmed Afifi
 */

public class Notepad extends JFrame implements ActionListener   {
                
    
    private static final long serialVersionUID = 1L;
    
    public static void main (String []args)
    {
        Notepad pad = new Notepad();
        pad.setVisible(true);
    }
    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
    
    FontHelper font = new FontHelper();
    
    JMenuBar menuBar = new JMenuBar ();
    
    JMenu file = new JMenu("File");
    JMenu edit = new JMenu("Edit");
    JMenu font01 = new JMenu("Font");
    
    JMenuItem font02 = new JMenuItem("Font Type");
    JMenuItem new0 = new JMenuItem("New");
    JMenuItem open = new JMenuItem("Open");
    JMenuItem save = new JMenuItem("Save");
    JMenuItem exit = new JMenuItem("Exit");
    JMenuItem cut = new JMenuItem("Cut");
    JMenuItem copy = new JMenuItem("Copy");
    JMenuItem paste = new JMenuItem("Paste");
    JMenuItem color = new JMenuItem("Font Color");
    JMenuItem backcolor = new JMenuItem("Background Color");

    JTextArea txt = new JTextArea();
    
    public Notepad()
    {
        setTitle("Notepad");
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        file.add(new0);
        new0.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N , ActionEvent.CTRL_MASK));
        
        file.add(open);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O , ActionEvent.CTRL_MASK));
        
        file.add(save);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S , ActionEvent.CTRL_MASK));
        
        file.add(exit);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4 , ActionEvent.CTRL_MASK));;
        
        edit.add(font01);
        edit.add(backcolor);
        
        edit.add(copy);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C , ActionEvent.CTRL_MASK));
        
        edit.add(cut);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X , ActionEvent.CTRL_MASK));
        
        edit.add(paste);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V , ActionEvent.CTRL_MASK));
        
        font01.add(font02);
        font01.add(color);
        menuBar.add(file);
        menuBar.add(edit);
        
        this.add(txt);
        this.setJMenuBar(menuBar);
	this.setSize(400,350);
        
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        
        open.addActionListener(this);
        save.addActionListener(this);
        new0.addActionListener(this);
        exit.addActionListener(this);
        
        color.addActionListener(this);
        backcolor.addActionListener(this);
        
        font02.addActionListener(this);
        font.getOk().addActionListener(this);
        font.getCancel().addActionListener(this);

    }
 
    
    @Override
    public void actionPerformed(ActionEvent ev) 
    {
        
        if(ev.getSource() == copy)
        {
           txt.copy();
        }
        
        else if(ev.getSource() == cut)
        {
           txt.cut();
        }
        
        else if(ev.getSource() == paste)
        {
           txt.paste();
        }
        
         if(ev.getSource() == font02)
        {
           font.setVisible(true);
        }
        
        else if(ev.getSource() == font.getOk())
        {
           txt.setFont(font.font());
           font.setVisible(false);
        }
        
        else if(ev.getSource() == font.getCancel())
        {
           font.setVisible(false);
        }
        
        else if(ev.getSource() == color)
        {
            Color initialcolor = null;    
            Color colorFont=JColorChooser.showDialog(this,"Select a color",initialcolor);   
            txt.setForeground(colorFont);
        }
        
        else if(ev.getSource() == backcolor)
        {
            Color initialcolor = null;    
            Color color=JColorChooser.showDialog(this,"Select a color",initialcolor);    
            txt.setBackground(color);
        }
        
        else if(ev.getSource() == save)
        {
           /* PrintWriter fout = null ;
             try {
                    fout = new PrintWriter(new FileWriter("Test.txt"));
                    String s = txt.getText();
                    StringTokenizer st = new StringTokenizer(s , System.getProperty("line.separator"));
                    while(st.hasMoreElements())
                    {
                        fout.println(st.nextToken());
                    }
                    JOptionPane.showMessageDialog(rootPane, "File Saved");
             }
             catch (IOException ex)
             {
                
             }
            finally
             {
                 fout.close();
             }*/
            
            
            JFileChooser save = new JFileChooser(); // again, open a file chooser
	    int option = save.showSaveDialog(this); // similar to the open file, only this time we call
			// showSaveDialog instead of showOpenDialog
			// if the user clicked OK (and not cancel)
	    if (option == JFileChooser.APPROVE_OPTION)
            {
		try
                {
                    try (BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath())))// create a buffered writer to write to a file
                    {
                        out.write(this.txt.getText()); // write the contents of the TextArea to the file
                           // close the file stream
                    }// write the contents of the TextArea to the file
		}
                catch (IOException ex)
                {
                   
		}
	    }
        }
        
        else if(ev.getSource() == open)
        {
            JFileChooser open01 = new JFileChooser(); // open up a file chooser (a dialog for the user to browse files to open)
		    int option = open01.showOpenDialog(this); // get the option that the user selected (approve or cancel)
			// NOTE: because we are OPENing a file, we call showOpenDialog~
			// if the user clicked OK, we have "APPROVE_OPTION"
			// so we want to open the file
		    if (option == JFileChooser.APPROVE_OPTION)
                    {
		        this.txt.setText(""); // clear the TextArea before applying the file contents
			try
                        {
				// create a scanner to read the file (getSelectedFile().getPath() will get the path to the file)
			    Scanner scan = new Scanner(new FileReader(open01.getSelectedFile().getPath()));
			    while (scan.hasNext())
                            {
                                //
                                this.txt.append(scan.nextLine() + "\n"); // append the line to the TextArea
                            }
			}
                        catch (FileNotFoundException ex) 
                        {
                            
                        }
		    }
        }
        
        else if(ev.getSource() == new0)
        {
            JFileChooser save = new JFileChooser(); //open a file chooser
	    int option = save.showSaveDialog(this);
			// showSaveDialog instead of showOpenDialog
			// if the user clicked OK (and not cancel)
	    if (option == JFileChooser.APPROVE_OPTION)
            {
		try
                {
                    try (BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath())))// create a buffered writer to write to a file
                    {
                        out.write(this.txt.getText()); // write the contents of the TextArea to the file
                           // close the file stream
                    }// write the contents of the TextArea to the file
		}
                catch (IOException ex)
                {
                   
		}
	    }
            this.txt.setText(" ");
            //txt.setText("");
        }
        
        else if(ev.getSource() == exit)
        {
           System.exit(0);
        }
    }              

}
