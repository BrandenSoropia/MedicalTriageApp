package patient.triageExceptions;

/**
 * 
 * Raised when recordings are given with identical 
 * time taken values
 * 
 * @author c3sranha
 *
 */
public class IdenticalTimeException extends Exception{
	
	private static final long serialVersionUID = 6738407917216526149L;

	/**
	 * Parameterless constructor
	 */
	public IdenticalTimeException() {
		super();
	}
	
	/**
	 * Constructor
	 * @param Message - error message to be displayed
	 */
	public IdenticalTimeException(String msg) {
		
		super(msg);
	}
}
