package io.jnorthr.toolkit;
import groovy.transform.*;
import javax.swing.*

/*
 * Feature to find template replacement names within a template string
 */
public class Mapper
{
    /** an O/S specific char. as a file path divider */
    String fs = java.io.File.separator;

    /** an O/S specific location for the user's home folder name */ 
    String home = System.getProperty("user.home");     

    /** If null keys found within the template String, this will be true */ 
    boolean hasMap = false;


    /** If we need to println audit log to work, this will be true */ 
    boolean audit = true;

    /** a variable holding Groovy template String */ 
    String tempText = "";

    /**
     * Default constructor builds a tool 
     */
    public Mapper()
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


    private String prompt(String i) 
    {
        JFrame jframe = new JFrame()
        String ss = "Enter value for {${i}} parameter ";
          String answer = JOptionPane.showInputDialog(jframe, ss)
          jframe.dispose()
          answer
    } // end of method



    protected Map getMap(String s) 
    {
        tempText = s;
        Map map=[:]
        say s;
        int b = 0;

        def x = s.split('\\$')
        say "... x.size()="+x.size()
        x.each{e-> 
            b+=1;
            say " and e=|${e}| (b=${b}) ";
            int k = (e.size() > 1) ? 1 : 0;
            String y = e.substring(0,k);
            println " & y=|${y}| ";
            if (y=='{')
            { 
                say " has { "; 
                def i = e.indexOf('}');
                if (i > 0)
                {
                    say " and } i=|${i}| ";
                    y = e.substring(1,i).trim();
            
                    if(y.size()>0)
                    { 
                        say " has y=|${y}| has y.size() ="+y.size();
                        if(map.containsKey(y))
                        {
                        }
                        else
                        {
                            hasMap = true;
                            map[y]=""
                            say " and added map key of =|${y}| ";
                
                            def ans = prompt(y); 
                            if (!(ans==null)) 
                            {
                				ans = ans.trim();
                                map[y]=ans;
	                            say " added map[${y}] value of |${map[y]}|";
                            } // end of if 
                        } // end of else
                        
                    } // end of if
                        
                }
                else
                {
                    //if (b>1) {buff+='$';}
                }
            } // end of if    
            else    
            {
                //if (b>1) {buff+='$';}
            } // end of else

            say " ";
        } // end of each
        
        return map;
    } // end of method



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
        println "Hello from Mapper.groovy"
        def s = '''Hello. My name is ${name} and live in ${country} with the name of ${name}.'''.toString();

        Mapper ma = new Mapper();
        Map map = ma.getMap(s);
        
        println "----------------\neach:";
        map.each{k,v->
            println "... map[${k}]=|${v}|"
        } // end of each

        s = ma.getTemplate(map);
        println "----------------\ntemplate:\n"+s;


        // second test
        s = '''Hello.'''.toString();
        map = ma.getMap(s);
        println "----------------\neach:";
        map.each{k,v->
            println "... map[${k}]=|${v}|"
        } // end of each

        s = ma.getTemplate(map);
        println "----------------\ntemplate:\n"+s;

	s = '''This is F4.F5 code in
the user folder using ${stuff} values.

/*
* Class to Write Things
*/
public class ${classname}
{

} // end of class
'''.toString();
        map = ma.getMap(s);

        println "----------------\neach:";
        map.each{k,v->
            println "... map[${k}]=|${v}|"
        } // end of each

        s = ma.getTemplate(map);
        println "----------------\ntemplate:\n"+s;

        println "--- the end---"
    } // end of main 

} // end of class