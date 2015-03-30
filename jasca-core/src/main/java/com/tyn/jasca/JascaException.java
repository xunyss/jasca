package com.tyn.jasca;

/**
 * 
 * @author S.J.H.
 */
public class JascaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public JascaException() {
		super();
	}
	
	/**
	 * 
	 * @param message
	 */
	public JascaException(String message) {
		super(message);
	}
	
	/**
	 * 
	 * @param cause
	 */
	public JascaException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public JascaException(String message, Throwable cause) {
		super(message, cause);
	}
}
