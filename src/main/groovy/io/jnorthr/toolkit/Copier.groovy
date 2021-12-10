package io.jnorthr.toolkit;

import groovy.transform.*;

// https://www.developer.com/java/data/how-to-code-java-clipboard-functionality.html
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.awt.Image;
import java.io.IOException;

/*
 * Feature to copy text to system clipboard
 */
public class Copier
{
    /** an O/S specific char. as a file path divider */
    String fs = java.io.File.separator;

    /** an O/S specific location for the user's home folder name */ 
    String home = System.getProperty("user.home");
    
    /** If we need to println audit log to work, this will be true */ 
    boolean audit = false;

    /**
     * Default constructor builds a tool to interact with the System Clipboard for most operatng systems
     */
    public Copier()
    {
    } // end of default constructor


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
     * Method to place provided String of text on the System Clipboard for most operatng systems
     *
     * @param  text string to copy onto system clipboard
     * @return boolean is true if copy was successful
     */
    public boolean copy(String s) 
    {
        boolean tf = true;
        try{
            ClipboardOwner owner = null;
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable transferable = new StringSelection(s);
            clipboard.setContents(transferable, owner);
            println "... Copier.copy() placed on clipboard:\n${s}"
        } 
        catch (def x)
        { 
            tf = false;
            println "... Copier.copy() failed to write to clipboard:\n${x.toString()}"
        } // end of catch    

        return tf;
    } // end of method


    /**
     * Method to retrieve a String of text from the System Clipboard for most operatng systems
     *
     * @return text currently held on System Clipboard
     */
    public String paste() 
    {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        DataFlavor flavor = DataFlavor.stringFlavor;
        String text = " - could not paste from clipboard";

        if (clipboard.isDataFlavorAvailable(flavor)) 
        {
            try 
            {
                text = (String)clipboard.getData(flavor);
            } 
            catch (UnsupportedFlavorException e) 
            {
                text = e.getMessage();
            } 
            catch (IOException e) 
            {
                text = e.getMessage();
            }
        } // end of if
        
        return text;
    } // end of method


    /**
     * Duplicate method to retrieve a String of text from the System Clipboard for most operatng systems
     *
     * @return text currently held on System Clipboard
     */
    public String get() 
    {
        return paste();
    } // end of method


    /**
     * Method to copy provided image data on the System Clipboard for most operatng systems
     * into java variable. This method can help handle the "paste" portion of a copy and paste operation.
     *
     * @return Image object
     */
    public Image getImageFromClipboard() throws Exception
    {
        Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor))
        {
          return (Image) transferable.getTransferData(DataFlavor.imageFlavor);
        }
        else
        {
          return null;
        }
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
        println "Hello from Copier.groovy"
        Copier ck = new Copier();

        ck.copy("Hi from Copier.groovy");
        // writeToClipboard(textArea.getText(), null);
        
        assert "Hi from Copier.groovy" == ck.paste();
        assert "Hi from Copier.groovy" == ck.get();

        println "--- the end---"
    } // end of main 

} // end of class
