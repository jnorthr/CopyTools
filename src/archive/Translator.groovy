package io.jnorthr.toolkit;
import javax.swing.*

/*
 * Feature to read text from a File
 */
public class Translator
{
    /** a Groovy Map of keys found within the template String */ 
	Map m=[:];

    /** a variable holding Groovy template String */ 
	String tempText = "";

    /** If ${} keys found within the template String, this will be true */ 
	boolean hasMap = false;

    /** If we need to println audit log to work, this will be true */ 
	boolean audit = true;


    /**
     * Non-Default constructor for a tool to convert Strings with Groovy Template characteristics into a 
     * string with replacement keys having an actual value
	 *
     * @param  is possible Groovy Template text to translate
     */
	public Translator(String text)
	{
		tempText = text;
	} // end of constructor

	/**
	 * A method to ask our user to provide a value for unknown keys within a Groovy Template-provided string 
	 *
     * @param  i is text to show user when asking for key replacement value;
     * @return text response from user to replace value of key within our Map
 	 */
	private String prompt(String i) 
	{
		JFrame jframe = new JFrame()
  		String answer = JOptionPane.showInputDialog(jframe, i)
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


	/**
	 * A method to see if there are ${} keys within a Groovy Template provided string
	 *
     * @return true when string from our constructor has any ${} pairs
 	 */
	public boolean hasKeys()
	{
    	def v=[]
    	v = tempText.split('\\$')
    	say "... v has "+v.size()+" entries"
    	hasMap = (v.size() > 1) ? true : false;
    	return tempText.contains('${');  //hasMap;
	} // end of method


	/**
	 * A method to winkle out a series of keys within a Groovy Template and return as a Map
	 *
     * @return Map of key/value pairs found within a Groovy Template; values are all blank until getKeys()
     */
	public Map getMap()
	{
	    Map m=[:]
	    if (!hasMap) { return m; }

	    def v=[]
      v = tempText.split('\\$')
    	v.eachWithIndex{e,ix->
        	int j = e.indexOf('}');
        	if (j > -1 )
        	{
            	def x = e.substring(0,j);
            	if ( x.trim().size() > 0 && !m.containsKey(x.trim()) )
            	{
                	m[x.trim()]="";
            	} // end of if
	        } // end of if 
    	} // end of each

	    return m;
	} // end of method


	/**
 	 * A method to get a value for each key within a Groovy Template provided string 
 	 *
     * @param  m is a Map of possibly zero entries;
     * @return Map of key/value pairs found within a Groovy Template; values are after asking user for a value for each key
     */
	public Map getKeys(Map m)
	{
	    if (!hasMap) { return m; }

    	say "m="
    	m.each{k,va-> 
    		  say "k=|${k}| and va=|${va}|"; 
        	def first = prompt("Enter a value for ${k}")
        	if (first==null){ first = ""; }
        	m[k]=first;
    	} // end of each

	    return m;
	} // end of Map


	/**
 	 * A method to translate a Groovy Template  
 	 *
     * @param  m is a Map of possibly zero entries
     * @return text string after template translation, or original constructor text if no ${} symbols exist
     */
	public String getTemplate(Map m)
	{
	    if (!hasMap) { return tempText; }

		  def engine = new groovy.text.SimpleTemplateEngine() 
    	def template = engine.createTemplate(tempText).make(m);

    	return template.toString(); 
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
        println "Hello from Translator.groovy"

        def s = '''1128${name} and my first name is ${prenom}. My address is ${addr} and it's nice. And ${prenom}. '''.toString(); 

        Translator ck = new Translator(s);

    		println "... hasKeys() ? " + ck.hasKeys();


        def map = ck.getMap();
		    println "... getMap() found " + map.size() + " entries";

        Map m = ck.getKeys(map);

        String template = ck.getTemplate(m);
        println template

        println "\n... Try broken template"
        s = '''Happy and my first ${ println text; }'''.toString()
		    ck = new Translator(s);
		    if (ck.hasKeys())
        {
        	println "... string has keys"
	        map = ck.getMap();
			    println "... getMap() found " + map.size() + " entries";
			    map.each{k,va->
				    println "... found key=|${k}| and value of |${va}|"
			    } // end of each 

			    map = ck.getKeys(map);
	        template = ck.getTemplate(map);
    	    println template
        }
        else
        {
        	println "... string has no keys"
        } // end of else


        println "\n... Try template without replacements"
        s = "Happy is my name";
    		ck = new Translator(s);
		    if (!ck.hasKeys())
        {
        	println "... string has no keys"
	        template = ck.getTemplate([:]);
    	    println template
        } // end of else

        println "\n... Try empty template" 
        s = "";
		    ck = new Translator(s);
		    if (!ck.hasKeys())
        {
        	println "... string has no keys"
			    map = ck.getKeys();
	        template = ck.getTemplate([:]);
    	    println "|"+template+"|";
        } // end of else

        println "--- the end---"
    } // end of main 

} // end of class
