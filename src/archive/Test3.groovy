import javax.swing.*
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import io.jnorthr.toolkit.IO;

class Test3 implements ActionListener
{
        def f =     new JFrame();

        JButton b1 = new JButton("Clear");
        JButton b2 = new JButton("Copy");
        JButton b3 = new JButton("Save");
        JButton b4 = new JButton("Exit");

        JTextArea area=new JTextArea("Welcome to java");  
        JPanel jp = new JPanel();

    public void setup()
    {
        UIManager.getCrossPlatformLookAndFeelClassName();
        
        jp.setLayout(new GridLayout(4,1));
        area.setBounds(10,20, 200,200);  
        area.setLineWrap(true);
        Font font = new Font("Courier", Font.BOLD, 12);
        area.setFont(font);
        //f.add(new JScrollPane(area));
        f.add(new JScrollPane(area), BorderLayout.CENTER);
        b1.addActionListener(this);
        b3.addActionListener(this);

        jp.add(b1);
        jp.add(b2);
        jp.add(b3);
        jp.add(b4);

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
        String ss = "Do you want to construct a template file now ? ";
          int answer = JOptionPane.showConfirmDialog(jframe, "Do you want to construct a template ?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          jframe.dispose()
          answer
    } // end of method


    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==b3)
        { 
            String s1 = area.getText();
            area.setText( " ");
            println "... doing action"
            IO ck = new IO();
            String tx = ck.write("XXX.F5", s1);
            println "... done."
        }
        
        if(e.getSource()==b4)
        { 
            System.exit(0);
        } // end of if
        
        if(e.getSource()==b1)
        { 
            area.setText("");
        } // end of if

    } // end of method
    

    public static void main(String[] args)
    {
        def obj=new Test3();                    

        def ans = obj.ask(); 
        println "... ok, the ans=|${ans}|"
    
        if (ans==0)
        {
            obj.setup();
        } // end of if
    
        //String s = area.getText();    
        println "--- the end ---"
    }
}    