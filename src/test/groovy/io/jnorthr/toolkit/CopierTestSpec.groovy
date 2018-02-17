package io.jnorthr.toolkit;
import io.jnorthr.toolkit.Copier;

/*
* see: http://code.google.com/p/spock/wiki/SpockBasics
* A spock test wrapper around the base class
*/
import java.util.logging.Logger;
import spock.lang.*
import java.lang.*;

/**
  * Tests a tool to interact with the System Clipboard for most operatng systems
  */
class CopierTestSpec extends Specification 
{
  // fields
  Copier ck;
  
  // Fixture Methods
  
  // run before every feature method
  def setup() 
  { 
      ck = new Copier();
  }          

  // run after every feature method
  def cleanup() {}
  
  
  // Note: The setupSpec() and cleanupSpec() methods may not reference instance fields.
  def setupSpec() 
  {
  } // run before the first feature method
  
  
  def cleanupSpec() {}   // run after the last feature method}


/*
Feature methods are the heart of a specification. They describe the features (properties, aspects) that you expect to find in the system under specification. By convention, feature methods are named with String literals. Try to choose good names for your feature methods, and feel free to use any characters you like!

Conceptually, a feature method consists of four phases:

 . Set up the feature's fixture
 . Provide a stimulus to the system under specification
 . Describe the response expected from the system
 . Clean up the feature's fixture
 . Whereas the first and last phases are optional, the stimulus and response phases are always present (except in interacting feature methods), and may occur more than once.

*/

  // Feature Methods

  // First Test
  def "1st Test: Setup Copier for default copy to system clipboard"() {
    given:
  		println "1st Test: Use Copier to put text on system clipboard"
 
    when:
      ck.setClipboard("set by CopierTestSpec");

    then:
    
		// Asserts are implicit and not need to be stated.
    	true == ck!=null;
  } // end of test

} // end of spec
