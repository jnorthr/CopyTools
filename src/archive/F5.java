//package net.codejava.swing;
package io.jnorthr.toolkit;

import io.jnorthr.toolkit.Copier;
import io.jnorthr.toolkit.IO;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
import java.awt.Color;
import javax.swing.*;


/**
 * This Swing program demonstrates setting shortcut key and hotkey for Jbutton.
 *
 * https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html for more on keystrokes
 *
 * @author www.codejava.net
 *
 */
public class F5 extends JFrame {
	private JButton f5button = new JButton();
	public boolean flag = false;
	
    /**
     * Default constructor to build a tool to demonstrate setting shortcut keys,hotkeys for menu and buttons
     */
	public F5() throws HeadlessException {
		super("F5");
		//makeMenu();
		makeButton("F5");
		//add(createToolBar(),"North"); //, new FlowLayout(FlowLayout.TRAILING));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(120, 250);
		setLocationRelativeTo(null);
	}
	
	private void makeButton(String key) {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(f5button);

		Action myAction = new AbstractAction("${key} Press") {
			@Override
			public void actionPerformed(ActionEvent evt) {
				System.out.println("\n${key} myAction run when ${key} function key pressed ...");
				if (flag) { getContentPane().setBackground(Color.GREEN); flag=false; }
				else { getContentPane().setBackground(Color.RED); flag=true; }

		        IO io = new IO();
        		String tx = io.getPayload(key);
        		if (tx.length() > 0)
        		{
        			Copier ck = new Copier();
        			ck.copy(tx);
        		} // end of if
			} // end of ActionPerformed
		};

		//String key = "F5";
		f5button.setAction(myAction); // when button mouse clicked
		myAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_ESCAPE);
		
		// when function key pressed, this isdone
		f5button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), key);
		f5button.getActionMap().put(key, myAction);
	} // end of makeButton
	

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
