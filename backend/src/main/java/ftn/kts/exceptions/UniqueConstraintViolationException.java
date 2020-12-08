package ftn.kts.exceptions;

public class UniqueConstraintViolationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3705136647381567050L;

	private String causeField;
	private String causeMessage;
	
	public UniqueConstraintViolationException() {
	}

	public UniqueConstraintViolationException(String message, String causeField, String causeMessage) {
		super(message);
		this.causeField = causeField;
		this.causeMessage = causeMessage;
	}

	public String getCauseField() {
		return causeField;
	}

	public void setCauseField(String causeField) {
		this.causeField = causeField;
	}

	public String getCauseMessage() {
		return causeMessage;
	}

	public void setCauseMessage(String causeMessage) {
		this.causeMessage = causeMessage;
	}
	
	
}
