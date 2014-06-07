package Exceptions;

public class SyntaxErrorException extends DBEngineException {

	public SyntaxErrorException() {
	}

	public SyntaxErrorException(String m) {
		super(m);
	}

}
