package com.tyn.jasca;


/**
 * 
 * @author S.J.H.
 */
public class Violation {
	
	private String engine;
	private String filename;
	private int line;
	private String message;
	private int severity;
	private String type;
	
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public String getFilename() {
		return to(filename);
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getSeverity() {
		return severity;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	private String to(String path) {
		String to = null;
		if ("F".equals(engine)) {
			to = "/src/" + path;
		}
		else if ("P".equals(engine)) {
			to = path.replace('\\', '/');
			to = to.substring(35);
		}
		return to;
	}
}
