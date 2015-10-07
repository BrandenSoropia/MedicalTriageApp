package user;

/**
 * The Physician class (a type of User of the triage application).
 * 
 * @author Harmandeep
 * @version 1.0
 */
public class Physician extends User {

	/**
	 * Constructor for Physician - default permissions added. Not logged in by
	 * default.
	 * 
	 * @param username
	 *            Physician's log in name.
	 * @param password
	 *            Physician's password.
	 */
	public Physician(String username, String password) {
		super(username, password, new String[] {
				"Record Prescription Information", "Look Up Patient" });
	}

	/**
	 * Constructor for Physician - specified permissions added. Not logged in by
	 * default.
	 * 
	 * @param username
	 *            Physician's log in name.
	 * @param password
	 *            Physician's password.
	 * @param permissions
	 *            Physician's permissions.
	 */
	public Physician(String username, String password, String[] permissions) {
		super(username, password, permissions);
	}

	/**
	 * Constructor for Physician - default permissions added. Logged in/out
	 * specified.
	 * 
	 * @param username
	 *            Physician's log in name.
	 * @param password
	 *            Physician's password.
	 * @param loggedIn
	 *            Whether or not the Physician is logged in.
	 */
	public Physician(String username, String password, boolean loggedIn) {
		super(username, password, new String[] {
				"Record Prescription Information", "Look Up Patient" },
				loggedIn);
	}

	/**
	 * Constructor for Physician - specified. permissions added. Logged in/out
	 * specified.
	 * 
	 * @param username
	 *            Physician's log in name.
	 * @param password
	 *            Physician's password.
	 * @param loggedIn
	 *            Whether or not the Physician is logged in.
	 */
	public Physician(String username, String password, String[] permissions,
			boolean loggedIn) {
		super(username, password, permissions, loggedIn);
	}
}
