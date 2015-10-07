/*TO-DO:
 * How will the currently logged in user be managed, added, logged out?
 * Will this object be given a file, scanner or pre-made list of users?
 * Getters/Setters...
 * Exceptions?
 * Test suite for user package classes
 */




package user;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A UserManager maintains a list of all the users (found in passwords.txt). It
 * sets and returns the current user of the application and ensures only one
 * user is logged in at any time.
 * 
 * @author Harmandeep
 * @version 1.0
 */
public class UserManager {

	/**
	 * An array list of every user.
	 */
	private ArrayList<User> users;
	
	/**
	 * 
	 */

	/**
	 * Constructor for a user manager. (Build array list from scanner).
	 * 
	 * @param scanner
	 *            An fis scanner set to the users' information file. Scanner is
	 *            not closed.
	 */
	public UserManager(Scanner scanner) {
		this.users = new ArrayList<User>();

		// Each line of the scanner is a user...
		while (scanner.hasNextLine()) {

			String[] userInfo = scanner.nextLine().split(",");

			// If the next line corresponds to a nurse...
			if (userInfo[2].equals("nurse")) {
				User nurse = new Nurse(userInfo[0], userInfo[1]);
				users.add(nurse);
			}

			// IF the next line corresponds to a physician...
			else if (userInfo[2].equals("physician")) {
				User physician = new Physician(userInfo[0], userInfo[1]);
				users.add(physician);
			}
		}
	}

	/**
	 * Constructor for a user manager. (Pre-built array list).
	 * 
	 * @param users
	 *            An array list of all the users.
	 */
	public UserManager(ArrayList<User> users){
		this.users = users;
	}
	
	
}
