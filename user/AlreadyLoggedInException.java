package user;

/**
 * Thrown when User is already logged in.
 * 
 * @author Harmandeep
 * @version 1.0
 */
public class AlreadyLoggedInException extends Exception {

	/**
	 * default serial...
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor (parameterless)
	 */
	public AlreadyLoggedInException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            Detailed error message.
	 */
	public AlreadyLoggedInException(String message) {
		super(message);
	}
}
