package com.tyn.jasca.analyzer.findbugs;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedHashSet;
import java.util.Set;

import org.dom4j.DocumentException;

import com.tyn.jasca.analyzer.Configuration;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.EffortLevel;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.Priority;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.ReportFormat;
import com.tyn.jasca.analyzer.findbugs.reporter.PatchedHTMLBugReporter;

import edu.umd.cs.findbugs.AbstractBugReporter;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugRanker;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.CategoryFilteringBugReporter;
import edu.umd.cs.findbugs.ClassScreener;
import edu.umd.cs.findbugs.DetectorFactoryCollection;
import edu.umd.cs.findbugs.EmacsBugReporter;
import edu.umd.cs.findbugs.FindBugsProgress;
import edu.umd.cs.findbugs.IFindBugsEngine;
import edu.umd.cs.findbugs.PrintingBugReporter;
import edu.umd.cs.findbugs.Project;
import edu.umd.cs.findbugs.SortedBugCollection;
import edu.umd.cs.findbugs.SortingBugReporter;
import edu.umd.cs.findbugs.SystemProperties;
import edu.umd.cs.findbugs.TextUIBugReporter;
import edu.umd.cs.findbugs.TextUIProgressCallback;
import edu.umd.cs.findbugs.XDocsBugReporter;
import edu.umd.cs.findbugs.XMLBugReporter;
import edu.umd.cs.findbugs.charsets.UTF8;

/**
 * 
 * @author S.J.H.
 */
public class FindBugsConfiguration implements Configuration {
	
	//--------------------------------------------------------------------------
	// optional values
	//--------------------------------------------------------------------------
	private boolean debug = false;
	private String input = null;
	private EffortLevel effortLevel = EffortLevel.DEFAULT;
	private boolean progress = false;
	private FindBugsProgress progressCallback = null;
	private boolean scanNestedArchives = true;
	private Priority priority = Priority.NORMAL;
	private String output = null;
	private ReportFormat reportFormat = ReportFormat.PRINTING;
	private BugReporter bugReporter = null;
	//--------------------------------------------------------------------------
	// default values
	//--------------------------------------------------------------------------
	// -adjustExperimental >> true
	final boolean adjustExperimental = false;
	// -redoAnalysis filename >> filename
	final String redoAnalysisFile = null;
	// -xml:withMessages >> true
	final boolean xmlWithMessages = false;
	// -xml:minimal >> true
	final boolean xmlMinimal = false;
	// -html:stylesheet.xsl >> stylesheet.xsl
	final String stylesheet = "default.xsl";
	// -quiet >> true
	final boolean quiet = false;
	// -maxRank rankThreshold >> rankThreshold
	final int rankThreshold = SystemProperties.getInt("findbugs.maxRank", BugRanker.VISIBLE_RANK_MAX);
	// -longBugCodes >> true
	final boolean useLongBugCodes = false;
	// -bugCategories bugCategorySet >> FindBugs.handleBugCategories(bugCategorySet)
	final Set<String> bugCategorySet = null;
	// -onlyAnalyze >> "modify classScreener"
	final ClassScreener classScreener = new ClassScreener();
	// -relaxed >> true
	final boolean relaxedReportingMode = false;
	// -xml:withAbridgedMessages >> true
	final boolean xmlWithAbridgedMessages = false;
	// -train:outputDir >> outputDir
	final String trainingOutputDir = null;
	// -useTraining:inputDir >> inputDir
	final String trainingInputDir = null;
	// -sourceInfo filename >> filename
	final String sourceInfoFile = null;
	// -dontCombineWarnings >> false
	final boolean mergeSimilarWarnings = true;
	// -release releaseName >> releaseName
	final String releaseName = "";
	// -projectName projectName >> projectName
	final String projectName = "";
	// -noClassOk >> true
	final boolean noClassOk = false;
	// -bugReporters >> "modify enabledBugReporterDecorators"
	final Set<String> enabledBugReporterDecorators = new LinkedHashSet<String>();
	// -bugReporters >> "modify disabledBugReporterDecorators"
	final Set<String> disabledBugReporterDecorators = new LinkedHashSet<String>();
	// -applySuppression >> true
	final boolean applySuppression = false;
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 * @return
	 */
	public boolean isDebug() {
		return debug;
	}
	
	/**
	 * Prints a trace of detectors run and classes analyzed to standard output.
	 * Useful for troubleshooting unexpected analysis failures. 
	 * 
	 * <pre>
	 * -debug
	 * (default = false)
	 * </pre>
	 * 
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getInput() {
		return input;
	}
	
	/**
	 * <pre>
	 * [jar/zip/class files, directories...]
	 * (*)
	 * </pre>
	 * 
	 * @param input
	 */
	public void setInput(String input) {
		this.input = input;
	}
	
	/**
	 * 
	 * @return
	 */
	public EffortLevel getEffortLevel() {
		return effortLevel;
	}
	
	/**
	 * set analysis effort level
	 * 
	 * <pre>
	 * -effort[:min|less|default|more|max]
	 * (default = default)
	 * (-workHard : -effort 설정이 max 보다 작을 경우 more 로 만듬)
	 * (-conserveSpace : -effort:min 과 동일)
	 * </pre>
	 * 
	 * @param effortLevel
	 */
	public void setEffortLevel(EffortLevel effortLevel) {
		this.effortLevel = effortLevel;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isProgress() {
		return progress;
	}
	
	/**
	 * display progress in terminal window
	 * 
	 * <pre>
	 * -progress
	 * (default = false)
	 * </pre>
	 * 
	 * @param progress
	 */
	public void setProgress(boolean progress) {
		if (progressCallback != null && !progress) {
			throw new IllegalStateException();
		}
		this.progress = progress;
	}
	
	/**
	 * 
	 * @param progressCallback
	 */
	public void setProgress(FindBugsProgress progressCallback) {
		setProgressCallback(progressCallback);
	}
	
	/**
	 * 
	 * @return
	 */
	public FindBugsProgress getProgressCallback() {
		return progressCallback;
	}
	
	/**
	 * 
	 * @param progressCallback
	 */
	public void setProgressCallback(FindBugsProgress progressCallback) {
		this.progress = true;
		this.progressCallback = progressCallback;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isScanNestedArchives() {
		return scanNestedArchives;
	}
	
	/**
	 * analyze nested jar/zip archives (default=true)
	 * 
	 * <pre>
	 * -nested[:true|false]
	 * (default = true)
	 * </pre>
	 * 
	 * @param scanNestedArchives
	 */
	public void setScanNestedArchives(boolean scanNestedArchives) {
		this.scanNestedArchives = scanNestedArchives;
	}
	
	/**
	 * 
	 * @return
	 */
	public Priority getPriority() {
		return priority;
	}
	
	/**
	 * set priority threshold
	 * 
	 * <pre>
	 * -low	report warnings of any confidence level
	 * -medium	report only medium and high confidence warnings
	 * -high	report only high confidence warnings
	 * (default = medium)
	 * </pre>
	 * 
	 * @param priority
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getOutput() {
		return output;
	}
	
	/**
	 * Save output in named file
	 * 
	 * <pre>
	 * -output {filename}
	 * (default = System.out)
	 * </pre>
	 * 
	 * @param output
	 */
	public void setOutput(String output) {
		this.output = output;
	}
	
	/**
	 * 
	 * @return
	 */
	public ReportFormat getReportFormat() {
		return reportFormat;
	}
	
	/**
	 * set reporting format
	 * 
	 * <pre>
	 * -xml[:withMessage]	XML output (optionally with messages)
	 * -html[:stylesheet]	generate HTML output (default stylesheet is default.xsl)
	 * -xdocs			xdoc XML output to use with Apache Maven
	 * -emacs			use emacs reporting format
	 * (default = ?)
	 * </pre>
	 * 
	 * @param reportFormat
	 */
	public void setReportFormat(ReportFormat reportFormat) {
		this.reportFormat = reportFormat;
	}
	
	/**
	 * 
	 * @return
	 */
	public BugReporter getBugReporter() {
		return bugReporter;
	}
	
	/**
	 * 
	 * @param bugReporter
	 */
	public void setBugReporter(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 * @param findBugs
	 * 
	 * @see edu.umd.cs.findbugs.TextUICommandLine#configureEngine(IFindBugsEngine findBugs)
	 */
	protected void configureEngine(IFindBugsEngine findBugs) {
		
		/*
		 * debug
		 */
		System.setProperty("findbugs.debug", String.valueOf(debug));
		
		/*
		 * set the Project
		 */
		Project project = new Project();
		project.addFile(input);
		
		/*
		 * set the BugInstance
		 */
		if (adjustExperimental) {
			BugInstance.setAdjustExperimental(true);
		}
		
		/*
		 * set the DetectorFactoryCollection
		 */
		findBugs.setDetectorFactoryCollection(DetectorFactoryCollection.instance());
		
		/*
		 * redoAnalysisFile
		 */
		if (redoAnalysisFile != null) {
			SortedBugCollection bugs = new SortedBugCollection();
			try {
				bugs.readXML(redoAnalysisFile);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			catch (DocumentException de) {
				IOException ioe = new IOException("Unable to parse " + redoAnalysisFile);
				ioe.initCause(de);
				
				ioe.printStackTrace();
			}
			project = bugs.getProject().duplicate();
		}
		
		/*
		 * set the BugReporter
		 */
		switch (reportFormat) {
		case PRINTING:
			bugReporter = new PrintingBugReporter();
			break;
		case SORTING:
			bugReporter = new SortingBugReporter();
			break;
		case XML:
			XMLBugReporter xmlBugReporter = new XMLBugReporter(project);
			xmlBugReporter.setAddMessages(xmlWithMessages);
			xmlBugReporter.setMinimalXML(xmlMinimal);
			bugReporter = xmlBugReporter;
			break;
		case EMACS:
			bugReporter = new EmacsBugReporter();
			break;
		case HTML:
		//	bugReporter = new HTMLBugReporter(project, stylesheet);
			bugReporter = new PatchedHTMLBugReporter(project, stylesheet);
			break;
		case XDOCS:
			bugReporter = new XDocsBugReporter(project);
			break;
		case CUSTOM:
			if (bugReporter == null) {
				throw new IllegalStateException("custom bug reporter is not set");
			}
			break;
		default:
			throw new IllegalStateException();
		}
		if (bugReporter instanceof AbstractBugReporter) {
			if (quiet) {
				((AbstractBugReporter) bugReporter)
					.setErrorVerbosity(BugReporter.SILENT);
			}
		}
		bugReporter.setPriorityThreshold(priority.getPriorityThreshold());
		if (bugReporter instanceof AbstractBugReporter) {
			((AbstractBugReporter) bugReporter)
				.setRankThreshold(rankThreshold);
		}
		if (bugReporter instanceof TextUIBugReporter) {
			((TextUIBugReporter) bugReporter)
				.setUseLongBugCodes(useLongBugCodes);
		}
		findBugs.setRankThreshold(rankThreshold);
		if (output != null) {
			PrintStream outputStream = null;
			try {
				File outputFile = new File(output);
				outputStream = UTF8.printStream(
						new BufferedOutputStream(
								new FileOutputStream(outputFile)));
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			if (bugReporter instanceof TextUIBugReporter) {
				((TextUIBugReporter) bugReporter)
					.setOutputStream(outputStream);
			}
		}
		if (bugCategorySet != null) {
			bugReporter = new CategoryFilteringBugReporter(bugReporter, bugCategorySet);
		}
		findBugs.setBugReporter(bugReporter);
		
		/*
		 * set the FindBugs Engine
		 */
		findBugs.setProject(project);
		if (progress) {
			if (progressCallback == null) {
				progressCallback = new TextUIProgressCallback(System.out);
			}
			findBugs.setProgressCallback(progressCallback);
		}
		findBugs.setUserPreferences(project.getConfiguration());
		findBugs.setClassScreener(classScreener);
		findBugs.setRelaxedReportingMode(relaxedReportingMode);
		findBugs.setAbridgedMessages(xmlWithAbridgedMessages);
		if (trainingOutputDir != null) {
			findBugs.enableTrainingOutput(trainingOutputDir);
		}
		if (trainingInputDir != null) {
			findBugs.enableTrainingInput(trainingInputDir);
		}
		if (sourceInfoFile != null) {
			findBugs.setSourceInfoFile(sourceInfoFile);
		}
		findBugs.setAnalysisFeatureSettings(effortLevel.getAnalysisFeatureSettings());
		findBugs.setMergeSimilarWarnings(mergeSimilarWarnings);
		findBugs.setReleaseName(releaseName);
		findBugs.setProjectName(projectName);
		findBugs.setScanNestedArchives(scanNestedArchives);
		findBugs.setNoClassOk(noClassOk);
		findBugs.setBugReporterDecorators(enabledBugReporterDecorators, disabledBugReporterDecorators);
		if (applySuppression) {
			findBugs.setApplySuppression(true);
		}
		
		findBugs.finishSettings();
	}
	
	//--------------------------------------------------------------------------
	
	@Override
	public void addOption(String... args) {
		throw new UnsupportedOperationException();
	}
}
