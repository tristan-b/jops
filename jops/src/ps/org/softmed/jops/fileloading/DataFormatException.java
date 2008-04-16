package org.softmed.jops.fileloading;

public class DataFormatException extends Exception {
    
    private static final long serialVersionUID = 677610107813204167L;
    
    public DataFormatException() {

	}

	public DataFormatException(Throwable t) {
		super(t);
	}

	public DataFormatException(String msg) {
		super(msg);
	}
	
	public DataFormatException(String msg,Throwable t) {
		super(msg,t);
	}

}
