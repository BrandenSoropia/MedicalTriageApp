package patient;

import patient.triageExceptions.IdenticalTimeException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Arthur Przech
 * @editor Harmandeep Sran
 * @version 1.1
 */
public class Symptoms implements Serializable {

	private static final long serialVersionUID = 5675174483049940582L;

	/*
	 * Fields
	 */
	private int[] timeTaken;
	private ArrayList<String> symptomsDescription;
	private Symptoms pastSymptoms;

	/**
	 * Constructor for a Symptoms object
	 * 
	 * @param timeTaken
	 *            An array of six ints where the ints represent year, month,
	 *            day, hour, minute, and second of when these symptoms were
	 *            recorded.
	 * @param symptomsDescription
	 *            An array of multiple strings that each contain a string
	 *            stating a symptom.
	 * @param pastSymptoms
	 *            An older symptoms recorded at an earlier time. (Possibly null
	 *            if first vitals taken) Vitals ADTs are linked in this way.
	 */
	public Symptoms(int[] timeTaken, ArrayList<String> symptomsDescription,
			Symptoms pastSymptoms) {

		this.timeTaken = timeTaken;
		this.symptomsDescription = symptomsDescription;
		this.pastSymptoms = pastSymptoms;
	}

	/**
	 * Return true if this vitals was taken at an earlier time than other. False
	 * otherwise.
	 * 
	 * @param other
	 *            The Symptoms object whose time_taken we are comparing to.
	 * 
	 * @return whether the other symptoms reading was taken at an earlier time.
	 * 
	 * @throws IdenticalTimeException
	 */
	public boolean earlierThan(Symptoms other) throws IdenticalTimeException {

		// Compare each time value; start with year, end with seconds.
		for (int i = 0; i < 6; i++) {

			if (other.timeTaken[i] < this.timeTaken[i]) {

				return false;
			}

			else if (other.timeTaken[i] > this.timeTaken[i]) {

				return true;
			}
		}

		// when the time_taken is the exact same, raise IdenticalTimeException
		throw new IdenticalTimeException(other.toString()
				+ " matches the time taken for " + this.toString());
	}

	/**
	 * @return time when symptoms were taken.
	 */
	public int[] getTimeTaken() {
		return timeTaken;
	}

	/**
	 * @return symptoms description.
	 */
	public ArrayList<String> getSymptomsDescription() {
		return symptomsDescription;
	}

	/**
	 * @return previous symptoms.
	 */
	public Symptoms getPastSymptoms() {
		return pastSymptoms;
	}

	/**
	 * @return string representation of a Symptoms object.
	 */
	// public String toString() {
	// return String.format("Time Taken: %s\n  " +
	// "Symptoms :: %d",timeTaken, this.getSymptomsDescription());
	// }
}