// http://www.herongyang.com/Swing/JMenuBar-Set-Keyboard-Accelerator-on-Menu-Items.html
/*
Keyboard mnemonics allows users to invoke a menu item with a single key. 
But the menu contains that menu item must be popped up first.

For users to invoke a menu item without its menu popped up, we need to assign an accelerator, key combination, 
to that menu item with the setAccelerator() method. Here is how it works:

1. Assign difference accelerators, representing different key combinations, to different menu items.
2. Adding an action listener to each menu item.
3. When an accelerator, key combination, is pressed at any time, the menu item with the matching accelerator will fire an action event.

special masks key:
ActionEvent.META_MASK = appple cmd* key
ActionEvent.ALT_MASK
ActionEvent.CTRL_MASK
ActionEvent.SHIFT_MASK

Here is an example program I wrote to test the setAccelerator() method:
*/
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class JMenuItemSetAcceleratorTest implements ActionListener {
   JFrame myFrame = null;

/*
   anAction = new AbstractAction("F2 key...") 
   {
        public void actionPerformed(ActionEvent e) {
            println "... f2 key was hit";
        }
   };
*/

   public static void main(String[] a) {
      (new JMenuItemSetAcceleratorTest()).test();
   }
   
   private void test() {
      myFrame = new JFrame("Menu Item Accelerator Test");
      myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      myFrame.setBounds(50,50,250,150);
      myFrame.setContentPane(new JDesktopPane());

      JMenuBar myMenuBar = new JMenuBar();
      JMenu myMenu = getFileMenu();
      myMenuBar.add(myMenu);
      myMenu = getColorMenu();
      myMenuBar.add(myMenu);
      myMenu = getOptionMenu();
      myMenuBar.add(myMenu);

      JMenuItem myItem = new JMenuItem("Help");
      myItem.setMnemonic(KeyEvent.VK_H);
      myItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.META_MASK) ); // apple cmd * key = META
      myItem.addActionListener(this);
      myMenuBar.add(myItem);

      myFrame.setJMenuBar(myMenuBar);
      myFrame.setVisible(true);
   }
   private JMenu getFileMenu() {
      JMenu myMenu = new JMenu("File");
      JMenuItem myItem = new JMenuItem("Open");
      myMenu.add(myItem);
      myItem = new JMenuItem("Close");
      myMenu.add(myItem);
      myMenu.addSeparator();
      myItem = new JMenuItem("Exit");
      myMenu.add(myItem);
      return myMenu;
   }
   
   private JMenuItem setAction(JMenuItem myItem, String key)
   {
        Action myAction = new AbstractAction("${key} Press") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("\n${key} myAction was run after ${key} function key pressed ...");
            } // end of ActionPerformed
        };
        myItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent."VK_${key}", 0), key);
        myItem.getActionMap().put(key, myAction );

        return myItem;
   } // end of method
   
   
   private JMenu getColorMenu() {
      JMenu myMenu = new JMenu("Color");
      ButtonGroup myGroup = new ButtonGroup();

      JRadioButtonMenuItem myItem = new JRadioButtonMenuItem("Red");
      myItem.setSelected(true);
      myItem.setMnemonic(KeyEvent.VK_R);
      myItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK) );
      myItem.addActionListener(this);
      myGroup.add(myItem);
      myMenu.add(myItem);

      myItem = new JRadioButtonMenuItem("Green");
      myItem.setMnemonic(KeyEvent.VK_G);
      myItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK) );
      myItem.addActionListener(this);
      myGroup.add(myItem);
      myMenu.add(myItem);

      myItem = new JRadioButtonMenuItem("Blue");
      myItem.setMnemonic(KeyEvent.VK_B);
      myItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK) );
      myItem.addActionListener(this);
      myGroup.add(myItem);
      myMenu.add(myItem);

      myItem = new JRadioButtonMenuItem("Exit");
      myItem.setMnemonic(KeyEvent.VK_F3);
      myItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK) );
      myItem.addActionListener(this);

      //String key = "F5";
      //myItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), key);
      //myItem.getActionMap().put(key, getAction("F5") );
      setAction(myItem, "F5")

      //myItem.getInputMap().put(KeyStroke.getKeyStroke("F5"), "doSomething");
      //myItem.getActionMap().put("doSomething", f5Action);

      myGroup.add(myItem);
      myMenu.add(myItem);

      return myMenu;
   }
   private JMenu getOptionMenu() {
      JMenu myMenu = new JMenu("Option");
      JMenuItem myItem = new JMenuItem("Sound");
      myMenu.add(myItem);
      myItem = new JMenuItem("Auto save");
      myMenu.add(myItem);
      return myMenu;
   }
   public void actionPerformed(ActionEvent e) {
      System.out.println("Item clicked: "+e.getActionCommand());
   }
}