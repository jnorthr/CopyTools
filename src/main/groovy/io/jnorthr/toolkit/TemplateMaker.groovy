package io.jnorthr.toolkit;

import groovy.transform.*;

import javax.swing.*
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import io.jnorthr.toolkit.IO;

/*
 * Class to gain text for this function key and save it with known filename.F5 
 */
class TemplateMaker implements ActionListener
{
    /** a handle for our GUI framework */
    def f = new JFrame();

    /** a simple name, like F12, for our function key filename */
    String filename = "";


    /** a button to remove existing text in our GUI */
    JButton b1 = new JButton("Clear");


    /** a button to copy existing text from our GUI to System clipboard */
    JButton b2 = new JButton("Copy");


    /** a button to write existing text in our GUI to an external file */
    JButton b3 = new JButton("Save");

    /** a button to kill this app */
    JButton b4 = new JButton("Exit");

    JTextArea area=new JTextArea("Welcome to java");  

    JPanel jp = new JPanel();

    /** If we need to println audit log to work, this will be true */ 
    boolean audit = true;

    public TemplateMaker(String fn)
    {
        filename = fn;

        // ask if user wants to use a GUI to create a template
        def ans = ask(); 

        // Yes, 
        if (ans==0)
        {
            setup();
        } // end of if
    
    } // end of constructor

    public void setup()
    {
        //UIManager.getCrossPlatformLookAndFeelClassName();
        f.add(new JLabel("Enter text for ${filename} function key :"), BorderLayout.NORTH);
        jp.setLayout(new GridLayout(4,1));
        area.setBounds(10,20, 200,200);  
        area.setLineWrap(true);
        Font font = new Font("Courier", Font.BOLD, 12);
        area.setFont(font);

        f.add(new JScrollPane(area), BorderLayout.CENTER);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

        jp.add(b1);
        jp.add(b2);
        jp.add(b3);
        jp.add(b4);

        //def ks = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
        //b2.setAccelerator(ks);

        jp.setSize(20,60)
        f.add(jp,BorderLayout.EAST);  
        b2.addActionListener(this);

        //f.add(b2);  
        f.setSize(600,300);  
        //f.setLayout(null);  
        f.setVisible(true);      
    }
    
    private int ask() 
    {
        def jframe = new JFrame()
        String ss = "Do you want to construct a ${filename} template file now ? ";
          int answer = JOptionPane.showConfirmDialog(jframe, ss, "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          jframe.dispose()
          answer
    } // end of method

    /**
     * A method to print an audit log if audit flag is true
     *
     * @param  is text to show user via println
     * @return void
     */
    public void say(String text)
    {
        if (audit) { println text; }
    } // end of method



    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==b1)
        { 
            area.setText("");
        } // end of if

        // copy
        if(e.getSource()==b2)
        { 
            Copier co = new Copier();
            String s2 = area.getText();
            co.copy(s2);
        } // end of if

        // use IO code to write new template
        if(e.getSource()==b3)
        { 
            String s1 = area.getText();
            //area.setText( " ");
            IO ck = new IO();
            String fi = filename.trim()+".F5"
            String tx = ck.write(fi, s1);
        }
        
        if(e.getSource()==b4)
        { 
            System.exit(0);
        } // end of if
        
    } // end of method
    

    // =============================================================================    
    /**
      * The primary method to execute this class. Can be used to test and examine logic and performance issues. 
      * 
      * argument is a list of strings provided as command-line parameters. 
      * 
      * @param  args a list of possibly zero entries from the command line; first arg[0] if present, is
      *         taken as a simple file name of a groovy script-structured configuration file;
    */
    public static void main(String[] args)
    {
        println "TemplateMaker starting ..."
        def obj=new TemplateMaker("F14");                    

        //String s = area.getText();    
        println "--- the end of TemplateMaker ---"
    }
}    