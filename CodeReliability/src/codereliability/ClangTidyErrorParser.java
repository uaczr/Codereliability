/**
 * 
 */
package codereliability;

import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IErrorParser;
import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.cdt.core.ProblemMarkerInfo;
import org.eclipse.core.resources.IFile;

/**
 * @author christoph
 *
 */
public class ClangTidyErrorParser implements IErrorParser{
	// sample line to parse:
	//
	// [/src/HelloWorld.cpp:19]: (style) The scope of the variable 'i' can be reduced
	// ----------1--------- -2    --3--  ------------------4-------------------------
	//
	// groups:
	// 1: file path and name
	// 2: line where problem was found
	// 3: problem severity
	// 4: problem description
	private static Pattern pattern = 
		    Pattern.compile("(.*):(\\d+):(\\d+):\\s*(.*):\\s*(.*)");
	
	@Override
	public boolean processLine(String line, ErrorParserManager eoParser) {
	    Matcher matcher = pattern.matcher(line);
	    if (!matcher.matches()) {
	      return false;
	    }
	    IFile fileName = eoParser.findFileName(matcher.group(1));
	    if (fileName != null) {
	      int lineNumber = Integer.parseInt(matcher.group(2));
	      String description = matcher.group(5);
	      int severity = findSeverityCode(matcher.group(4));
	      ProblemMarkerInfo info = 
	          new ProblemMarkerInfo(fileName, lineNumber, description, severity, null);
	      eoParser.addProblemMarker(info);
	      return true;
	    }
	    return false;
	}
	
	private static Map<String, Integer> SEVERITY_MAPPING = new HashMap<String, Integer>();
	 
	static {
	  SEVERITY_MAPPING.put("error", IMarkerGenerator.SEVERITY_ERROR_RESOURCE);
	  SEVERITY_MAPPING.put("warning", IMarkerGenerator.SEVERITY_WARNING);
	  SEVERITY_MAPPING.put("style", IMarkerGenerator.SEVERITY_INFO);
	}
	 
	private int findSeverityCode(String text) {
	  Integer code = SEVERITY_MAPPING.get(text);
	  if (code != null) {
	    return code;
	  }
	  return IMarkerGenerator.SEVERITY_INFO;
	}
	

}
