package com.tyn.jasca.analyzer.findbugs.reporter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.io.DocumentSource;

import edu.umd.cs.findbugs.BugCollection;
import edu.umd.cs.findbugs.BugCollectionBugReporter;
import edu.umd.cs.findbugs.FindBugs;
import edu.umd.cs.findbugs.HTMLBugReporter;
import edu.umd.cs.findbugs.Project;

/**
 * edu.umd.cs.findbugs.HTMLBugReporter 클래스와
 * TransformerFactory factory;
 * 의 구현체만 다름.
 * 
 * PMD 의 dependency 로 인해
 * TransformerFactory.newInstance() 가
 * net.sf.saxon.TransformerFactoryImpl 클래스 객체가 반환 됨
 * 
 * @see edu.umd.cs.findbugs.HTMLBugReporter
 * 
 * @author S.J.H.
 */
public class PatchedHTMLBugReporter extends BugCollectionBugReporter {
	private final String stylesheet;

	private Exception fatalException;

	public PatchedHTMLBugReporter(Project project, String stylesheet) {
		super(project);
		this.stylesheet = stylesheet;
	}

	@Override
	public void finish() {
		try {
			BugCollection bugCollection = getBugCollection();
			bugCollection.setWithMessages(true);
			// Decorate the XML with messages to display
			Document document = bugCollection.toDocument();
			// new AddMessages(bugCollection, document).execute();

			// Get the stylesheet as a StreamSource.
			// First, try to load the stylesheet from the filesystem.
			// If that fails, try loading it as a resource.
			InputStream xslInputStream = getStylesheetStream(stylesheet);
			StreamSource xsl = new StreamSource(xslInputStream);
			xsl.setSystemId(stylesheet);

			// Create a transformer using the stylesheet
		//	TransformerFactory factory = TransformerFactory.newInstance();
			
			/**
			 * S.J.H. modify..
			 */
			String implClassName = null;
		//	if ("51.0".equals(System.getProperty("java.class.version"))) {		// TODO JRE 버전별로 다를 수 있음
				implClassName = "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl";
		//	}
			TransformerFactory factory = TransformerFactory.newInstance(implClassName, null);
			Transformer transformer = factory.newTransformer(xsl);
			
			// Source document is the XML generated from the BugCollection
			DocumentSource source = new DocumentSource(document);

			// Write result to output stream
			StreamResult result = new StreamResult(outputStream);

			// Do the transformation
			transformer.transform(source, result);
		}
		catch (Exception e) {
			logError("Could not generate HTML output", e);
			fatalException = e;
			if (FindBugs.DEBUG) {
				e.printStackTrace();
			}
		}
		outputStream.close();
	}

	public Exception getFatalException() {
		return fatalException;
	}

	private static InputStream getStylesheetStream(String stylesheet) throws IOException {
		if (FindBugs.DEBUG) {
			System.out.println("Attempting to load stylesheet " + stylesheet);
		}
		try {
			URL u = new URL(stylesheet);
			return u.openStream();
		} catch (Exception e) {
			assert true; // ignore it
		}
		try {
			return new BufferedInputStream(new FileInputStream(stylesheet));
		} catch (Exception fnfe) {
			assert true; // ignore it
		}
		InputStream xslInputStream = HTMLBugReporter.class.getResourceAsStream("/" + stylesheet);
		if (xslInputStream == null) {
			throw new IOException("Could not load HTML generation stylesheet " + stylesheet);
		}
		return xslInputStream;
	}
}
