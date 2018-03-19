// https://www.javaworld.com/article/2077562/core-java/java-tip-61--cut--copy--and-paste-in-java.html
package io.jnorthr.toolkit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import io.jnorthr.toolkit.actions.*;

public class Example extends JPanel
{
  // our actions
  Action cutAction, copyAction, pasteAction, exitAction;
  
  // our icons for the actions
  ImageIcon cutIcon, copyIcon, pasteIcon, exitIcon;

  // handle [meta][equals], [meta][equals][shift], and [meta][plus]
  private KeyStroke largerFontSizeKeystroke1 = KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, Event.META_MASK);
  private KeyStroke largerFontSizeKeystroke2 = KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, Event.META_MASK + Event.SHIFT_MASK);
  private KeyStroke largerFontSizeKeystroke3 = KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, Event.META_MASK);

  public Example()
  {
    super(new BorderLayout());
    createActions();
  }

  private void createActions()
  {
    // create our icons (for the toolbar)
    cutIcon = new ImageIcon("redcross.png");
    copyIcon = new ImageIcon("Copy-32.png");
    pasteIcon = new ImageIcon("Paste-32.png");
    exitIcon = new ImageIcon("redcross.png");

    // create our actions
    cutAction = new CutAction("Cut", cutIcon, "Cut stuff onto the clipboard", new Integer(KeyEvent.VK_CUT));
    copyAction = new CopyAction("Copy", copyIcon, "Copy stuff to the clipboard", new Integer(KeyEvent.VK_COPY));
    pasteAction = new PasteAction("Paste", pasteIcon, "Paste whatever is on the clipboard", new Integer(KeyEvent.VK_PASTE));
    exitAction = new ExitAction("Exit", exitIcon, "Quit", new Integer(KeyEvent.VK_ESCAPE));
  }

  /**
   * Create a JMenuBar, populating it with our Actions.
   */
  private JMenuBar createMenuBar()
  {
    // create the menubar
    JMenuBar menuBar = new JMenuBar();
    
    // create our main menu
    JMenu fileMenu = new JMenu("File");
    JMenu editMenu = new JMenu("Edit");

    // create our menu items, using the same actions the toolbar buttons use
    JMenuItem cutMenuItem = new JMenuItem(cutAction);
    JMenuItem copyMenuItem = new JMenuItem(copyAction);
    JMenuItem pasteMenuItem = new JMenuItem(pasteAction);
    JMenuItem exitMenuItem = new JMenuItem(exitAction);

    // add the menu items to the Edit menu
    editMenu.add(cutMenuItem);
    editMenu.add(copyMenuItem);
    editMenu.add(pasteMenuItem);
    fileMenu.add(exitMenuItem);

    // add the menus to the menubar
    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    
    return menuBar;
  }

  /**
   * Create a JToolBar using our Actions.
   */
  private JToolBar createToolBar()
  {
    // create our toolbar
    JToolBar toolBar = new JToolBar();

    // create our toolbar buttons, using the same actions the menuitems use
    JButton cutButton = new JButton(cutAction);
    JButton copyButton = new JButton(copyAction);
    JButton pasteButton = new JButton(pasteAction);
    JButton exitButton = new JButton(exitAction);
    
    //def largerFontSizeAction = new LargerFontSizeAction(mainFrameController, this, "Increase Font Size", largerFontSizeKeystroke1.getKeyCode());
    // add largerFontSizeKeystroke1
    //cutButton.getInputMap().put(largerFontSizeKeystroke1, "largerFontSizeKeystroke");
    //cutButton.getActionMap().put("largerFontSizeKeystroke", largerFontSizeAction);
    
    KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK);
    //pasteButton.setAccelerator(key);

    // add our buttons to the toolbar
    toolBar.add(cutButton);
    toolBar.add(copyButton);
    toolBar.add(pasteButton);
    toolBar.add(exitButton);
    
    return toolBar;
  }

  private static void createAndShowGUI()
  {
    // create a simple jframe
    JFrame frame = new JFrame("Java AbstractAction Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // construct our panel, including our toolbar
    Example panel = new Example();
    panel.add(panel.createToolBar(), BorderLayout.PAGE_START);
    panel.setOpaque(true);
    frame.setContentPane(panel);

    // add the menubar to the jframe
    frame.setJMenuBar(panel.createMenuBar());
    
    // show the jframe
    frame.setPreferredSize(new Dimension(450, 300));
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public static void main(String[] args)
  {
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        createAndShowGUI();
      }
    });
  }
}

/*
https://alvinalexander.com/java/java-action-abstractaction-actionlistener
https://www.javaworld.com/article/2077562/core-java/java-tip-61--cut--copy--and-paste-in-java.html

ImageIcon cutIcon = new ImageIcon(Example.class.getResource("Cut-32.png"));
Action cutAction = new CutAction("Cut", cutIcon, "Cut stuff onto the clipboard", new Integer(KeyEvent.VK_CUT));

// create the java menu item, giving it our action
JMenuItem cutMenuItem = new JMenuItem(cutAction);

<or>

// create the java toolbar
JToolBar toolBar = new JToolBar();

// create a java button (JButton), giving it our Action
JButton cutButton = new JButton(cutAction);

// add the button to the toolbar
toolBar.add(cutButton);
*/