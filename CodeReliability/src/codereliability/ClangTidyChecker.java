package codereliability;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.codan.core.cxx.externaltool.AbstractExternalToolBasedChecker;
import org.eclipse.cdt.codan.core.cxx.externaltool.ConfigurationSettings;
import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.cdt.core.ProblemMarkerInfo;


public class ClangTidyChecker extends AbstractExternalToolBasedChecker{
	private static final String ERROR_PROBLEM_ID = 
		    "com.dw.cdt.checkers.codereliability.error";
		 
	private static final Map<Integer, String> PROBLEM_IDS = 
		    new HashMap<Integer, String>();
		 
	static {
		  PROBLEM_IDS.put(
		      IMarkerGenerator.SEVERITY_ERROR_RESOURCE, ERROR_PROBLEM_ID);
		  PROBLEM_IDS.put(
		      IMarkerGenerator.SEVERITY_WARNING, "com.dw.cdt.checkers.codereliability.warning");
		  PROBLEM_IDS.put(
		      IMarkerGenerator.SEVERITY_INFO, "com.dw.cdt.checkers.codereliability.style");
		}
	public ClangTidyChecker() {
		super(new ConfigurationSettings("Clang-Tidy", new File("clang-tidy"), "-checks=readability-*"));
	}

	@Override
	protected String[] getParserIDs() {
		return new String[] { "com.dw.cdt.checkers.ClangTidyErrorParser" };
	}

	@Override
	protected String getReferenceProblemId() {
		return ERROR_PROBLEM_ID;
	}
	
	@Override
	public void addMarker(ProblemMarkerInfo info) {
	  String problemId = PROBLEM_IDS.get(info.severity);
	  String description = String.format("[clang-tidy] %s", info.description);
	  reportProblem(problemId, createProblemLocation(info), description);
	}

}
