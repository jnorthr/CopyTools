package io.jnorthr.toolkit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * Feature to create an Action 
 */
public class CutAction extends AbstractAction
{
  public CutAction(String name, ImageIcon icon, String shortDescription, Integer mnemonic)
  {
    super(name, icon);
    putValue(SHORT_DESCRIPTION, shortDescription);
    putValue(MNEMONIC_KEY, mnemonic);
  }

  public void actionPerformed(ActionEvent e)
  {
    JOptionPane.showMessageDialog(null, "Would have done the 'Cut' action.");
  }
}

/*
https://alvinalexander.com/java/java-action-abstractaction-actionlistener

ImageIcon cutIcon = new ImageIcon(JavaAbstractActionExample.class.getResource("Cut-32.png"));
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