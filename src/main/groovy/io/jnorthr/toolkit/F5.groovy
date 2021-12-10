//package net.codejava.swing;
package io.jnorthr.toolkit;

import io.jnorthr.toolkit.Copier;
import io.jnorthr.toolkit.IO;
import io.jnorthr.toolkit.Mapper;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.LineBorder;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.*;


/**
 * This Swing program demonstrates function key usage from a keyboard and/or clickable Jbutton.
 *
 * https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html for more on keystrokes
 *
 * @author james.northrop@orange.fr
 *
 */
public class F5 extends JFrame {

    /** a hook to use of the Escape key */
	KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");

    /** a button to stop this */
	JButton quitbutton = new JButton("Quit");

    /** a handle to our IO module to do read/write stuff */
	IO io = new IO();

    /** If we need to println audit log to work, this will be true */ 
    boolean audit = false;

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

    /**
     * Default constructor to build a tool to support function key usage to copy text to the System clipboard
     */
	public F5() throws HeadlessException {
		super("F5");
		getContentPane().setBackground(Color.CYAN);

		Action quitAction = new AbstractAction("ESC") {
			@Override
			public void actionPerformed(ActionEvent evt) {
				say("\nQuit quitAction run when ESCAPE function key WAS pressed ...");
				System.exit(0);
			} // end of ActionPerformed
		};

		//quitbutton.setPreferredSize(new Dimension(20, 16));
		quitbutton.setFont(new Font("Arial", Font.BOLD, 8));
		quitbutton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(stroke, "Quit");
		quitbutton.getActionMap().put("Quit", quitAction);
		quitbutton.setAction(quitAction); // when button mouse clicked
		quitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_ESCAPE);
		add(quitbutton);

		//KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.SHIFT_MASK);

		//setLayout(new BoxLayout(BoxLayout.X_AXIS));  //this, BoxLayout.X_AXIS));
		//setLayout(new FlowLayout(FlowLayout.CENTER));
		//BoxLayout bl = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
		def bl = new GridLayout(1, 0, 0, 0);
		setLayout(bl);


		(1..12).eachWithIndex{ num, ix -> 
			//say "... ix=$ix num=$num  F${num}" 
			add(makeButton("F${num}"));
		} // end of each

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(665, 50);
		//validate();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension windowSize = this.getSize();

		int windowX = Math.max(0, (screenSize.width  - windowSize.width ) / 2);
		int windowY = Math.max(0, (screenSize.height - windowSize.height) / 2);
		windowY = screenSize.height - (windowSize.height+50);
		//say "... windowSize.width=${windowSize.width} and windowSize.height=${windowSize.height}"
		//say "... screenSize.width=${screenSize.width} and screenSize.height=${screenSize.height}"
		this.setLocation(windowX, windowY);  // Don't use "f." inside constructor.
		validate();
	} // end of constructor

	
    /**
     * Utility method to build a single JButton to copy text to the System clipboard
	 * containing logic to copy text from external file due to being clicked or pressing
	 * it's associate Function Key.
     *
     * @param  text string to copy onto system clipboard
     * @return JButton containing logic to copy text from external file 
     */
	public JButton makeButton(String key) {
		JButton mybutton = new JButton();
		mybutton.setFont(new Font("Arial", Font.PLAIN, 10));
		mybutton.setMargin(new Insets(0,-1,-3,0));
		//mybutton.setPreferredSize(new Dimension(22, 15));
		mybutton.setToolTipText("Press ${key} function key to copy to Clipboard");
		//mybutton.setBorder(new LineBorder(Color.BLACK,2));
		mybutton.setBackground(Color.CYAN)

		mybutton.addMouseListener(new MouseAdapter() 
		{
            public void mouseEntered(MouseEvent mEvt) 
            {
        		mybutton.setToolTipText("Press ${key} function key to copy to Clipboard");
		    	//mybutton.setToolTipText("lakshman");
    		}
		});


		Action editAction = new AbstractAction("${key}") {
			@Override
			public void actionPerformed(ActionEvent evt) {
				say("\nEDITAction run when SHIFT+VK_${key} function key WAS pressed ...");
        		String tx = io.getPayload(key);
        		if (tx.length() > 0)
        		{
					setTitle("F5 -> ${key} function key has ${tx.length()} bytes");  
					TemplateMaker obj = new TemplateMaker(key, tx);                    
        		} // end of else

			} // end of ActionPerformed
		};


		Action myAction = new AbstractAction("${key}") {
			@Override
			public void actionPerformed(ActionEvent evt) {
				say("\n${key} myAction run when ${key} function key pressed ...");
				
        		String tx = io.getPayload(key);
        		if (tx.length() > 0)
        		{
        			Mapper ma = new Mapper();
        			Map map = ma.getMap(tx);
        			tx = ma.getTemplate(map);

        			Copier ck = new Copier();
        			ck.copy(tx);
					setTitle("F5 -> ${key} function key copied ${tx.length()} bytes to Clipboard");  
					((JButton) evt.getSource()).setForeground(Color.BLUE);
					((JButton) evt.getSource()).setFont(new Font("Arial", Font.BOLD, 12));
        		} // end of if
        		else
        		{
					setTitle("F5 -> ${key} function key has no text for Clipboard");  
					((JButton) evt.getSource()).setForeground(Color.RED);
					TemplateMaker obj = new TemplateMaker(key);                    
        		} // end of else

        		// these don't change color
        		//((JButton) evt.getSource()).setBackground(Color.BLUE);
 				//((JButton) evt.getSource()).setContentAreaFilled(false);
                //((JButton) evt.getSource()).setOpaque(true);

        		//JButton button = (JButton) evt.getSource()
        		//button.setBackground(Color.CYAN);

				//getContentPane().setBackground(Color.MAGENTA);

			} // end of ActionPerformed
		};

		//String key = "F5";
		mybutton.setAction(myAction); // when button mouse clicked
		myAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_ESCAPE);
		
		// when function key pressed, this is done
		mybutton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent."VK_${key}", 0), key);
		mybutton.getActionMap().put(key, myAction);

		// when function key pressed with SHIFT key down, this edit event is done
		mybutton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent."VK_${key}", KeyEvent.SHIFT_MASK), "Edit");
		mybutton.getActionMap().put("Edit", editAction);
		
		return mybutton;
	} // end of makeButton



    // =============================================================================    
    /**
      * The primary method to execute this class. Can be used to test and examine logic and performance issues. 
      * 
      * argument is a list of strings provided as command-line parameters. 
      * 
      * @param  args a list of possibly zero entries from the command line; first arg[0] if present, is
      *         taken as a simple file name of a groovy script-structured configuration file;
    */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				new F5().setVisible(true);
			}
		}
		);
	} // end of main

} // end of class
