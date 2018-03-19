import javax.swing.*

    private prompt(String i) 
    {
        JFrame jframe = new JFrame()
        String ss = "Enter value for {${i}} parameter ";
          int answer = JOptionPane.showConfirmDialog(jframe, ss)
          jframe.dispose()
          answer
    } // end of method

    protected Map getMap(String s) 
    {
        Map map=[:]
        println s;
        int b = 0;

        def x = s.split('\\$')
        //println "... x.size()="+x.size()
        x.each{e-> 
            b+=1;
            println " and e=|${e}| (b=${b}) ";
            int k = (e.size() > 1) ? 1 : 0;
            def y = e.substring(0,k);
            //print " & y=|${y}| ";
            if (y=='{')
            { 
                //print " has { "; 
                def i = e.indexOf('}');
                if (i > 0)
                {
                    //print " and } i=|${i}| ";
                    y = e.substring(1,i).trim();
            
                    if(y.size()>0)
                    { 
                        if(!map[y])
                        {
                            map[y]=""
                            //print " and key=|${y}| ";
                
                            def ans = prompt(y); 
                            if (!(ans==null)) 
                            {
                                map[y]=ans.trim();
                                //buff+=ans.trim();
                            } // end of if 
                        } // end of if
                    } // end of if
            
                    //buff+=y;
                    //buff+=e.substring(i+1);
                }
                else
                {
                    if (b>1) {buff+='$';}
                    //buff+=e;
                }
            } // end of if    
            else    
            {
                if (b>1) {buff+='$';}
                //buff+=e;
            } // end of else

            println " ";
        } // end of each
    } // end of method


package io.jnorthr.toolkit;

import groovy.transform.*;


/*
 * Feature to copy text to system clipboard
 */
public class Copier
{
    /** an O/S specific char. as a file path divider */
    String fs = java.io.File.separator;

    /** an O/S specific location for the user's home folder name */ 
    String home = System.getProperty("user.home");
    

    /** If null keys found within the template String, this will be true */ 
    boolean hasMap = false;


    /** If we need to println audit log to work, this will be true */ 
    boolean audit = true;


    /**
     * Default constructor builds a tool 
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

def s = '''Hello. My name is ${name} and live in ${country} with the name of ${name}.'''.toString();
def s1 = "Hi";

//def buff="";


/*
def j = s.indexOf('$');
//println "... the location of \$ is "+j;
if (j > -1)
{
    j+=1;
    def t = s.substring(j);
    //println "... t=|${t}|"
} // end of if
*/


/*
x.each{m-> 
    //println "|${m}|"
}
*/


//println "----------------\n"+buff;

println "\n----------------\n";
map.each{k,v->
    println "... map[${k}]=|${v}|"
} // end of each
        println "--- the end---"
    } // end of main 

} // end of class