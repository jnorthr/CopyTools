package io.jnorthr.toolkit;

/*
 * Feature to read text from a File
 */
public class IO
{
    /** an O/S specific char. as a file path divider */
    String fs = java.io.File.separator;

    /** an O/S specific location for the user's home folder name */ 
    String home = System.getProperty("user.home");

    /** an O/S specific location for the user's current project folder name */ 
    String dir = System.getProperty("user.dir");

    /** If we need to println audit log to work, this will be true */ 
    boolean audit = true;

    /**
     * Default constructor builds a tool to read text from a file
     */
    public IO()
    {
    	say "... IO() home=[${home}${fs}]"
        say "... current project folder name is : "+dir;
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
     * Method to read String of text from a file
     *
     * @param  String name of text file to read
     * @return String content of file, if it exists or null if not
     */
    public String read(String s) 
    {
        say "... read(${s})"
        boolean present = new File(s).exists();
        String cb = null;
        if (present)
        {
        	say "... found ${s}";
            cb =  new File(s).getText('UTF-8');
        } // end of if
        else
        {
            cb = "* no file exists for filename '${s}'; "
        } // end of else

        return cb;
    } // end of method



    /**
     * Method to read String of text from an F5 file
     *
     * @param  String name of text file to read, like 'F6'
     * @return String content of file, if it exists or blank if not
     */
    public String getPayload(String s) 
    {
        String cb = "";
        boolean ok = s.trim().toUpperCase().endsWith(".F5");
        if (!ok) { s += ".F5"; }

        say "... IO.getPayload(${s}) looking for ${s}"

        // first check for this function key file in current folder
        def f =  new File( s );
        boolean present = f.exists();

        if (present)
        {
        	say "... found ${s}";
            cb = f.getText('UTF-8');
        } // end of if
        else
        {
        	say "... did not find ${s}";

        	// then check for this function key file in user's project folder
        	say "... trying to find:[${dir}${fs}${s}]"
        	f =  new File("${dir}${fs}${s}")
        	present = f.exists();

        	if (present)
        	{
	        	say "... found:[${dir}${fs}${s}]"
        		cb = f.getText('UTF-8');
        	} // end of if
        	else
        	{
	        	say "... did not find:[${dir}${fs}${s}]"
        	}
        } // end of else

        return cb;
    } // end of method


    /**
     * Method to write String of text to a named file
     *
     * @param  String simple name of text file to write
     * @param  String of text to write to file
     * @return true, if write was successful
     */
    public boolean write(String s, String payload) 
    {   say "... about to write to file:"+s;
		def file3 = new File(s)
        
        boolean present = file3.exists();
        say "... does file ${s} exist ? "+present;

        try
        {
            file3.withWriter('UTF-8') { writer ->
                writer.write(payload)
			}
            
            present = true;
        } // end of try
        catch(IOException x)
        {
        	say "... failed to write ${s} due to:"+x.msg;
        	present = false;
        }

        return present;
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
        println "Hello from IO.groovy"
        IO ck = new IO();

        String tx = ck.read("F5.F5");
        println "... found:\n"+tx;

        boolean ok = ck.write("F15.F5", tx);
        println "... write back to F15.F5:"+ok;

        tx = ck.getPayload("F6");
        println "... found F6 ? :\n"+tx;
        if (tx.size() < 1) { println "... none found for F6"}

        tx = ck.getPayload("F4");
        println "... found F4 ? :"+tx;
        if (tx.size() < 1) { println "... none found for F4"}


        tx = ck.getPayload("F15.F5");
        println "... found F15 ? :"+tx;
        if (tx.size() < 1) { println "... none found for F15.F5"}

        println "--- the end---"
    } // end of main 

} // end of class
