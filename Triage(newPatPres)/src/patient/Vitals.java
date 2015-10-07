package patient;

import java.io.Serializable;

import patient.triageExceptions.IdenticalTimeException;

/**
 * @author Arthur Przech
 * @editor Harmandeep Sran
 * @version 1.1
 */
public class Vitals implements Serializable {

	private static final long serialVersionUID = -5865280365001480988L;

	/**
	 * Fields
	 * 
	 * timeTaken - time when vitals were taken. temp - patient's temperature.
	 * bloodPressure - patient's blood pressure. heartrate - patient's heart
	 * rate. 
	 * oldVitals - previous vitals taken during this visit.
	 */
	private int[] timeTaken;
	private double temp;
	private double[] bloodPressure;
	private double heartRate;
	private Vitals oldVitals;

	/**
	 * Constructor for a Vitals object
	 * 
	 * @param timeTaken
	 *            An array of six ints where the ints represent year, month,
	 *            day, hour, minute, and second of when these vitals were
	 *            recorded.
	 * @param temp
	 *            A double that contains the temperature.
	 * @param bloodPressure
	 *            An array that contains two doubles where the doubles represent
	 *            systolic bP, and diastolic bP respectively.
	 * @param heartRate
	 *            A double that contains heartRate.
	 * @param oldVitals
	 *            An older vitals recorded at an earlier time. (Possibly null if
	 *            first vitals taken) Vitals ADTs are linked in this way.
	 * 
	 */
	public Vitals(int[] timeTaken, double temp, double[] bloodPressure,
			double heartRate, Vitals oldVitals) {

		this.timeTaken = timeTaken;
		this.temp = temp;
		this.bloodPressure = bloodPressure;
		this.heartRate = heartRate;
		this.oldVitals = oldVitals;
	}

	/**
	 * Return true if this vitals was taken at an earlier time than other. False
	 * otherwise.
	 * 
	 * @param other
	 *            The vitals object whose time_taken we are comparing to.
	 * 
	 * @return whether the other vitals reading was taken at an earlier time.
	 * 
	 * @throws IdenticalTimeException
	 *             Thrown when time_takens are identical for the two vitals
	 *             objects.
	 */
	public boolean earlierThan(Vitals other) throws IdenticalTimeException {

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
	 * Returns patient's heart rate recording.
	 * 
	 * @return stored heart rate recording.
	 */
	public double getHeartRate() {
		return heartRate;
	}

	/**
	 * Returns time when vitals were taken.
	 * 
	 * @return time when vitals were taken.
	 */
	public int[] getTimeTaken() {
		return timeTaken;
	}

	/**
	 * Returns patient's temperature recording.
	 * 
	 * @return stored temperature recording.
	 */
	public double getTemp() {
		return temp;
	}

	/**
	 * Returns patient's blood pressure readings.
	 * 
	 * @return stored blood pressure recording.
	 */
	public double[] getBloodPressure() {
		return bloodPressure;
	}

	/**
	 * Returns patient's old vitals.
	 * 
	 * @return previous vitals recording.
	 */
	public Vitals getOldVitals() {
		return oldVitals;
	}

	// /**
	// * @return string representation of a Vitals object.
	// */
	// public String toString() {
	// return String.format("Time Taken: %s\n  " +
	// "Vitals :: Temperature: %d, Blood Pressure: %f, Heart Rate: %g",timeTaken,temp,bloodPressure,heartRate);
	// }
}
