package user;

/**
 * The Nurse class (a type of User of the triage application).
 * 
 * @author Harmandeep
 * @version 1.0
 */
public class Nurse extends User {

	/**
	 * Constructor for Nurse - default permissions added. Not logged in by
	 * default.
	 * 
	 * @param username
	 *            Nurse's log in name.
	 * @param password
	 *            Nurse's password.
	 */
	public Nurse(String username, String password) {
		super(username, password, new String[] { "Create Patient Records",
				"Record Doctor Visit", "Look Up Patient", "Record Vitals" });
	}

	/**
	 * Constructor for Nurse - specified permissions added. Not logged in by
	 * default.
	 * 
	 * @param username
	 *            Nurse's log in name.
	 * @param password
	 *            Nurse's password.
	 * @param permissions
	 *            Nurses's permissions.
	 */
	public Nurse(String username, String password, String[] permissions) {
		super(username, password, permissions);
	}

	/**
	 * Constructor for Nurse - default permissions added. Logged in/out
	 * specified.
	 * 
	 * @param username
	 *            Nurse's log in name.
	 * @param password
	 *            Nurse's password.
	 * @param loggedIn
	 *            Whether or not the Nurse is logged in.
	 */
	public Nurse(String username, String password, boolean loggedIn) {
		super(username, password, new String[] { "Create Patient Records",
				"Record Doctor Visit", "Look Up Patient", "Record Vitals" },
				loggedIn);
	}

	/**
	 * Constructor for Nurse - specified. permissions added. Logged in/out
	 * specified.
	 * 
	 * @param username
	 *            Nurse's log in name.
	 * @param password
	 *            Nurse's password.
	 * @param loggedIn
	 *            Whether or not the Nurse is logged in.
	 */
	public Nurse(String username, String password, String[] permissions,
			boolean loggedIn) {
		super(username, password, permissions, loggedIn);
	}
}
