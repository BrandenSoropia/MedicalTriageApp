package user;

import java.util.TreeSet;

/**
 * Abstract class for a user of the Triage application.
 * 
 * @author Harmandeep
 * @version 1.0
 */
public abstract class User {

	/**
	 * This user's login name.
	 */
	private String username;

	/**
	 * This user's login password.
	 */
	private String password;

	/**
	 * Whether or not user is currently logged in.
	 */
	private boolean loggedIn;

	/**
	 * This user's permissions within the Triage app.
	 * 
	 * Kinds of permissions (possible contents of this set): Create Patient
	 * Records, Record Doctor Visit, Get Urgency List, Look Up Patient, Record
	 * Prescription Information, Record Vitals.
	 */
	public TreeSet<String> permissions;

	/**
	 * Constructor for a User. User not logged in by default.
	 * 
	 * @param username
	 *            This user's login name.
	 * @param password
	 *            This user's login password.
	 * @param perm
	 *            This user's permissions within the app.
	 */
	public User(String username, String password, String[] perm) {
		this.username = username;
		this.password = password;
		this.permissions = new TreeSet<String>();

		// Populate set of permissions.
		for (String s : perm) {
			permissions.add(s);
		}

		loggedIn = false;
	}

	/**
	 * Constructor for a User. Option to start logged in.
	 * 
	 * @param username
	 *            This user's login name.
	 * @param password
	 *            This user's login password.
	 * @param permissions
	 *            This user's permissions within the app.
	 * @param loggedIn
	 *            Whether or not user is logged in.
	 */
	public User(String username, String password, String[] perm,
			boolean loggedIn) {
		this.username = username;
		this.password = password;
		this.permissions = new TreeSet<String>();

		// Populate set of permissions.
		for (String s : perm) {
			permissions.add(s);
		}

		this.loggedIn = loggedIn;
	}

	/**
	 * Returns the User's username.
	 * 
	 * @return This User's log in name.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the User's password.
	 * 
	 * @return This User's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Changes the password of this User.
	 * 
	 * @param password
	 *            This User's new password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns this User's permissions.
	 * 
	 * @return This User's permissions.
	 */
	public TreeSet<String> getPermissions() {
		return this.permissions;
	}

	/**
	 * Adds permission p to this User's permissions (if it's not already there).
	 * 
	 * @param p
	 *            The new permission to add.
	 */
	public void addPermission(String p) {
		this.permissions.add(p);
	}

	/**
	 * Removes permission p from this User's permissions (if it's there).
	 * 
	 * @param p
	 *            The permission to remove.
	 */
	public void removePermission(String p) {
		this.permissions.remove(p);
	}

	/**
	 * Returns whether this User has permission p.
	 * 
	 * @return Whether this User has permission p.
	 */
	public boolean hasPermission(String p) {
		return this.permissions.contains(p);
	}

	/**
	 * Returns whether or not this User is logged in.
	 * 
	 * @return Whether or not this User is logged in.
	 */
	public boolean isLoggedIn() {
		return this.loggedIn;
	}

	/**
	 * Tries to log in the user; if they are already logged in, raises an
	 * exception.
	 * 
	 * @param pass
	 *            Password inputted by user.
	 * @throws AlreadyLoggedInException
	 */
	public void logIn(String pass) throws AlreadyLoggedInException {
		if (!this.isLoggedIn()) {

			// Check if password matches
			if (this.password.equals(pass)) {
				this.loggedIn = true;
			}

		} else {
			throw new AlreadyLoggedInException(this.username
					+ " is already logged in!");
		}
	}

	/**
	 * Tries to log out the user; if they are already logged out, raises an
	 * exception.
	 * 
	 * @throws AlreadyLoggedOutException
	 */
	public void logOut() throws AlreadyLoggedOutException {
		if (this.isLoggedIn()) {
			this.loggedIn = false;
		} else {
			throw new AlreadyLoggedOutException(this.username
					+ " is already logged out!");
		}
	}
}
