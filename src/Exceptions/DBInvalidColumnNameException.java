package Exceptions;

public class DBInvalidColumnNameException extends DBEngineException {
	private String message;

	public DBInvalidColumnNameException() {
	}

	public DBInvalidColumnNameException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
