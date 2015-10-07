package patient;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Calendar;

import patient.triageExceptions.IdenticalTimeException;

/**
 * @author Arthur Przech
 * @editor Harmandeep Sran
 * @version 1.1
 */
public class PatientVisitRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6127320530644781582L;

	/**
	 * Fields
	 * 
	 * arrivalTime - time when visit started. dateOfBirth - patient's dob.
	 * currentVisitVitals - patient's latest recorded vitals. currentSymptoms -
	 * patient's latest recorded symptoms. visitedByDoctor - whether or not
	 * patient has been visited by doctor. urgency - patient's urgency.
	 * saveFileBuffer - buffer for changes made during this visit record (to be
	 * pushed later).
	 */
	private int[] arrivalTime;
	private int[] dateOfBirth;
	private Vitals currentVisitVitals;
	private Symptoms currentVisitSymptoms;
	private boolean visitedByDoctor;
	private int[] doctorVisitTime;
	private int urgency;
	private String[] saveFileBuffer;
// Just added
	private Prescription currentPrescription;
	/**
	 * Constructor for a patient visit record object.
	 * 
	 * @param arrivalTime
	 *            An array of six ints where the ints represent year, month,
	 *            day, hour, minute, and second of when the patient arrived.
	 * @param dateOfBirth
	 *            The patient's date of birth [YYYY, MM, DD].
	 * @param currentVisitVitals
	 *            Vitals object for this visit.
	 * @param currentVisitSymptoms
	 *            Symptoms object for this visit.
	 * @param visitedByDoctor
	 *            Whether or not patient has been visited by a doctor.
	 * @param doctorVisitTime
	 *            Time when doctor visited. (same setup as arrivalTime).
	 */
	public PatientVisitRecord(int[] arrivalTime, int[] dateOfBirth,
			Vitals currentVisitVitals, Symptoms currentVisitSymptoms,
			boolean visitedByDoctor, int[] doctorVisitTime,
			String[] saveFileBuffer, Prescription prescription) {

		this.arrivalTime = arrivalTime;
		this.dateOfBirth = dateOfBirth;
		this.currentVisitVitals = currentVisitVitals;
		this.currentVisitSymptoms = currentVisitSymptoms;
		this.visitedByDoctor = visitedByDoctor;
		this.doctorVisitTime = doctorVisitTime;
		this.saveFileBuffer = saveFileBuffer;
// Just added
		this.currentPrescription = prescription;
	}

	/**
	 * Format the time array into a string that is suitable for writing to file.
	 * 
	 * @param time
	 *            - An array of six ints where the ints represent year, month,
	 *            day, hour, minute, and second respectively.
	 * @return a file writer-friendly version of the time.
	 */
	private String timeFormatBuffer(int[] time) {

		String formattedTime = new String();

		formattedTime += time[0] + "-" + time[1] + "-" + time[2] + "-"
				+ time[3] + "-" + time[4] + "-" + time[5];

		return formattedTime;

	}

	/**
	 * Return patient's current age, based on dob.
	 * 
	 * @return patient's current age.
	 */
	private int getCurrentAge() {

		// Pull the current system time from java.util.Calender
		Calendar time = Calendar.getInstance();
		int year = time.get(Calendar.YEAR);
		int month = time.get(Calendar.MONTH);
		int day = time.get(Calendar.DAY_OF_MONTH);

		int age = year - this.dateOfBirth[0];

		// Month of birth later than current month
		if (this.dateOfBirth[1] > month) {
			age = age - 1;
		}

		// Same month, but day of birth later than current day
		else if (this.dateOfBirth[2] > day) {
			age = age - 1;
		}

		return age;
	}

	/**
	 * Return true if this patient arrived earlier than the other patient. False
	 * otherwise.
	 * 
	 * @param other
	 *            - another patient's visit record.
	 * @return whether this patient arrived at an earlier time.
	 * @throws IdenticalTimeException
	 */
	public boolean isEarlierThan(PatientVisitRecord other)
			throws IdenticalTimeException {

		// Check each time value, from years to seconds.
		for (int i = 0; i < 6; i++) {

			if (other.arrivalTime[i] < this.arrivalTime[i]) {
				return false;
			} else if (other.arrivalTime[i] > this.arrivalTime[i]) {
				return true;
			}
		}

		// when the time_taken is the exact same.
		throw new IdenticalTimeException(other.getArrivalTime()
				+ " matches the arrival time for " + this.getArrivalTime());
	}

	/**
	 * End this visit record.
	 * 
	 * @param timeLeft
	 *            - time when patient's visit ended.
	 */
	public void endPatientVisit(int[] timeLeft) {
		saveFileBuffer[1] += "\\EndVisit " + timeFormatBuffer(timeLeft) + "\n";
	}

	/**
	 * Record a new update to the patient's vitals.
	 * 
	 * @param timeTaken
	 *            - time when vitals were recorded.
	 * @param temp
	 *            - patient's temperature.
	 * @param heartRate
	 *            - patient's heart rate
	 * @param bloodPressure
	 *            - patient's blood pressure
	 */
	public void updateVitals(int[] timeTaken, double temp, double heartRate,
			double[] bloodPressure) {

		// Create a new most current vitals object
		this.currentVisitVitals = new Vitals(timeTaken, temp, bloodPressure,
				heartRate, currentVisitVitals);

		// Update patient's urgency with new vitals information.
		this.urgency = 0;

		if (temp >= 39.0) {
			this.urgency += 1;
		}

		if (bloodPressure[0] >= 140 || bloodPressure[1] >= 90) {
			this.urgency += 1;
		}

		if (heartRate >= 100 || heartRate <= 50) {
			this.urgency += 1;
		}

		if (getCurrentAge() < 2) {
			this.urgency += 1;
		}

		// Append changes to buffer; to be written to file later.
		saveFileBuffer[1] += "\\UpdateVitals "
				+ timeFormatBuffer(currentVisitVitals.getTimeTaken()) + "$"
				+ temp + "$" + bloodPressure[0] + "-" + bloodPressure[1] + "$"
				+ heartRate + "\n";
	}
	
// TODO Update in PatientVisitRecord that constructs a new Prescription object that gets set as the current prescription and add to the buffer so it'll ne written
	/**
	 * Updates the patient's Prescription.
	 * 
	 * @param medication
	 * @param instructions
	 */
	public void updatePrescription(String medication, String instructions, int[] timeTaken) {
		// Creates a new Prescription object with updated data
		Prescription newPrescription = new Prescription(medication, instructions, timeTaken);
		// sets this PatientVisitRecord's prescription as the new Prescription 
		this.currentPrescription = newPrescription;
		
		// Add to buffer so it will be written in patient's text file
		saveFileBuffer[1] += "\\UpdatePrescription " + timeFormatBuffer(currentPrescription.getTimeTaken()) + "$" + 
				newPrescription.getMedication() + "$" + newPrescription.getInstructions() + "\n";
	}

	/**
	 * Record a new update to patient's symptoms.
	 * 
	 * @param timeTaken
	 *            - time when symptoms were recorded.
	 * @param currentSymptomsDescription
	 *            - description of patient's symptoms.
	 */
	public void updateSymptoms(int[] timeTaken,
			ArrayList<String> currentSymptomsDescription) {

		// Create a new most current symptoms object
		this.currentVisitSymptoms = new Symptoms(timeTaken,
				currentSymptomsDescription, currentVisitSymptoms);

		// Append the change to the buffer; to be written to file later.
		saveFileBuffer[1] += "\\UpdateSymptoms " + timeFormatBuffer(timeTaken)
				+ "$";

		for (int i = 0; i < currentSymptomsDescription.size() - 1; i++) {
			saveFileBuffer[1] += currentSymptomsDescription.get(i) + "&";
		}

		saveFileBuffer[1] += currentSymptomsDescription
				.get(currentSymptomsDescription.size() - 1) + "\n";
	}

	/**
	 * Record a new visit by the doctor
	 * 
	 * @param doctorVisitTime
	 *            - time when the doctor visited.
	 */
	public void updateDoctorVisitInformation(int[] doctorVisitTime) {
		this.visitedByDoctor = true;
		this.doctorVisitTime = doctorVisitTime;

		// Append the change to the buffer to be written to file later.
		saveFileBuffer[1] += "\\UpdateDoctorVisitInformation "
				+ timeFormatBuffer(doctorVisitTime) + "\n";
	}

	/**
	 * Clear the buffer's contents.
	 */
	public void flushBuffer() {
		saveFileBuffer[1] = new String();
	}

	/**
	 * @return patient's arrival time for this visit.
	 */
	public int[] getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * @return patient's most recent vitals for this visit.
	 */
	public Vitals getCurrentVisitVitals() {
		return currentVisitVitals;
	}
	
// TODO Added a getPrescription method in PatientVisitRecords
	/**
	 * Return the current Prescription of the patient.
	 * 
	 * @return
	 */
	public Prescription getCurrentPrescription() {
		return currentPrescription;
	}
	/**
	 * @return patient's most recent symptoms for this visit.
	 */
	public Symptoms getCurrentVisitSymptoms() {
		return currentVisitSymptoms;
	}

	/**
	 * @return whether patient has been visited by a doctor.
	 */
	public boolean isVisitedByDoctor() {
		return visitedByDoctor;
	}

	/**
	 * @return time when doctor visited.
	 */
	public int[] getDoctorVisitTime() {
		return doctorVisitTime;
	}

	/**
	 * @return patient's (current) urgency.
	 */
	public int getUrgency() {
		return urgency;
	}

	/**
	 * @return the current save file buffer.
	 */
	public String[] getSaveFileBuffer() {
		return saveFileBuffer;
	}
}
