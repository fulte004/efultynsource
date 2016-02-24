package Csla.Validation;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.net.URLDecoder;

/**
 * Parses a rule:// URI to provide easy access to the parts of the URI.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:42 PM
 */
public class RuleDescription {

	private HashMap<String, String> _arguments;
	private String _methodName;
	private String _propertyName;
	private String _scheme;

	/**
	 * Creates an instance of the object by parsing the provided rule:// URI.
	 * 
	 * @param ruleString    The rule:// URI.
	 * @throws UnsupportedEncodingException 
	 * @throws URISyntaxException 
	 */
	public RuleDescription(String ruleString) throws UnsupportedEncodingException, URISyntaxException{
		URI uri = new URI(ruleString);
		        
		        _scheme = uri.getRawSchemeSpecificPart();
		        _methodName = uri.getHost();
		        _propertyName = uri.getPath().substring(1);
		        
		        String args = uri.getQuery();
		        if (!(args.isEmpty()))
		        {
		          if (args.startsWith("?"))
		          {
		            args = args.substring(1);
		          }
		          _arguments = new HashMap<String, String>();
		          String[] argArray = args.split("[&]",0);
		          for (String arg : argArray)
		          {
		            String[] argParams = arg.split("[=]",0);
		            _arguments.put(URLDecoder.decode(argParams[0],"UTF-8"), 
		            		URLDecoder.decode(argParams[1],"UTF-8"));
		          }
		        }
	}

	/**
	 * Gets a Dictionary containing the name/value arguments provided to the rule
	 * method.
	 */
	public HashMap<String, String> getArguments(){
		  return _arguments; 
	}

	/**
	 * Gets the name of the rule method.
	 */
	public String getMethodName(){
		  return _methodName; 
	}

	/**
	 * Parses a rule:// URI.
	 * 
	 *        @returns A populated RuleDescription object.
	 * 
	 * @param ruleString    Text representation of a rule:// URI.
	 * @throws URISyntaxException 
	 * @throws UnsupportedEncodingException 
	 */
	public static RuleDescription Parse(String ruleString) throws UnsupportedEncodingException, URISyntaxException{
		return new RuleDescription(ruleString);
	}

	/**
	 * Gets the name of the property with which the rule is associated.
	 */
	public String getPropertyName(){
		  return _propertyName; 
	}

	/**
	 * Gets the scheme of the URI (should always be rule://).
	 */
	public String getScheme(){
		  return _scheme; 
	}

}