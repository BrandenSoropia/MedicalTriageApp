package user;

/**
 * Thrown when User is already logged out.
 * 
 * @author Harmandeep
 * @version 1.0
 */
public class AlreadyLoggedOutException extends Exception {

	/**
	 * default serial...
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor (parameterless)
	 */
	public AlreadyLoggedOutException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            Detailed error message.
	 */
	public AlreadyLoggedOutException(String message) {
		super(message);
	}
}
