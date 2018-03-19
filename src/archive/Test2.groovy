package io.jnorthr.toolkit;
import javax.swing.*

/*
 * Feature to read text from a File
 */
public class Test2
{

Map m=[:];

/*
 * A method to ask our user to provide a value for unknown keys within a Groovy Template-provided string 
 */
String prompt(String i) {
  JFrame jframe = new JFrame()
  String answer = JOptionPane.showInputDialog(jframe, i)
  jframe.dispose()
  answer
} // end of method

/*
 * A method to winkle out a series of keys within a Groovy Template provided string plus their values 
 */
public String getKeys(def s)
{
    m=[:]
    def v=[]
    v = s.tokenize( '${' )
    v.eachWithIndex{e,ix->
        int j = e.indexOf('}');
        if (j > -1 )
        {
            def x = e.substring(0,j)
            if ( !m.containsKey(x) )
            {
                def first = prompt("Enter a value for \${${x}}")
                if (first==null){ first = ""; }
                m[x]=first;
            } // end of if
        
        } // end of if 
    } // end of each

    println "m="
    m.each{k,va-> println "k=|${k}| and va=|${va}|"; }

    def engine = new groovy.text.SimpleTemplateEngine() 
    def template = engine.createTemplate(s).make(m) 

    return template;
} // end of Map


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
        println "Hello from Test2.groovy"
        Test2 ck = new Test2();

        def s = '''1128${name} and my first name is ${prenom}. My address is ${addr} and it's nice. And ${prenom}.'''; 
        def template = ck.getKeys(s);
        println template

        println "--- the end---"
    } // end of main 

} // end of class
