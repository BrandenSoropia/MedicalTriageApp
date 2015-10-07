package patient.triageExceptions;

/**
 * 
 * Raised when recordings are given with identical 
 * time taken values
 * 
 * @author c3sranha
 *
 */
public class PatientNotFoundException extends Exception{

	private static final long serialVersionUID = -191997424907651316L;

	/**
	 * Parameterless constructor
	 */
	public PatientNotFoundException() {
		super();
	}
	
	/**
	 * Contructor
	 * @param Message - error message to be displayed
	 */
	public PatientNotFoundException(String msg) {
		
		super(msg);
	}
}
