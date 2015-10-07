package patient;

import java.io.Serializable;

import java.util.ArrayList;

import patient.triageExceptions.CurrentPatientException;

/**
 * @author Arthur Przech
 * @editor Harmandeep Sran
 * @version 1.1
 */
public class PatientRecord implements Serializable {

	private static final long serialVersionUID = 6789038897927951698L;

	/**
	 * Fields
	 * 
	 * name - patient's name. dateOfBirth - patient's dob. healthCardNumber -
	 * patient's health card number. currentPatient - whether or not patient is
	 * currently checked in. patientVisitRecords - all the patient's visit
	 * records on file.
	 */
	private String name;
	private int[] dateOfBirth;
	private String healthCardNumber;
	private boolean currentPatient;
	private ArrayList<PatientVisitRecord> patientVisitRecords;

	/**
	 * Constructor for a patient record object.
	 * 
	 * @param name
	 *            - patient's name.
	 * @param dateOfBirth
	 *            - patient's date of birth [YYYY, MM, DD].
	 * @param healthCardNumber
	 *            - patient's health card number.
	 * @param currentPatient
	 *            - whether or not patient is currently visiting.
	 * @param patientVisitRecords
	 *            - patient's visit records.
	 */
	public PatientRecord(String name, int[] dateOfBirth,
			String healthCardNumber, boolean currentPatient,
			ArrayList<PatientVisitRecord> patientVisitRecords) {

		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.healthCardNumber = healthCardNumber;
		this.currentPatient = currentPatient;
		this.patientVisitRecords = patientVisitRecords;
	}

	/**
	 * Returns the current save file buffer.
	 * 
	 * @return the current save file buffer.
	 */
	public String[] getCurrentSaveFileBuffer() {
		return this.getCurrentVisitRecord().getSaveFileBuffer();

	}

	/**
	 * Clear the file buffer for the latest visit record.
	 */
	public void flushCurrentBuffer() {
		this.getCurrentVisitRecord().flushBuffer();
	}

	/**
	 * Returns the patient't latest visit record.
	 * 
	 * @return the most current patient visit record.
	 */
	public PatientVisitRecord getCurrentVisitRecord() {

		// Gets and returns the latest visit record (most current)
		return this.patientVisitRecords
				.get(this.patientVisitRecords.size() - 1);
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
	 * Create a new patient visit record.
	 * 
	 * @param arrivalTime
	 *            - time when visit began.
	 * @throws CurrentPatientException
	 *             - when patient is already visiting.
	 */
	public void createNewVisitRecord(int[] arrivalTime)
			throws CurrentPatientException {
		// If they are not a current patient...
		if (!currentPatient) {
			String firstLineOfBuffer = "\\NewVisit "
					+ timeFormatBuffer(arrivalTime) + "\n";
			String[] saveFileBuffer = { this.healthCardNumber,
					firstLineOfBuffer };
// TODO Added "null" value for Prescription in PatientRecord when it creates a new PatientVisit
			PatientVisitRecord visitRecord = new PatientVisitRecord(
					arrivalTime, this.dateOfBirth, null, null, false, null,
					saveFileBuffer, null);
			this.patientVisitRecords.add(visitRecord);
			this.currentPatient = true;
		} 
		
		// If they are a current patient...
		else {
			throw new CurrentPatientException();
		}

	}

	/**
	 * @return patient's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return patient's date of birth.
	 */
	public int[] getDateOfBirth() {
		return dateOfBirth;
	}

	public void setPatientCheckedIn() {

		this.currentPatient = false;

	}

	/**
	 * @return patient's health card number
	 */
	public String getHealthCardNumber() {
		return healthCardNumber;
	}

	/**
	 * @return whether patient is currently visiting.
	 */
	public boolean isCurrentPatient() {
		return currentPatient;
	}

	/**
	 * @return all the visit records for this patient.
	 */
	public ArrayList<PatientVisitRecord> getPatientVisitRecords() {
		return patientVisitRecords;
	}

	/**
	 * @return patient's entire record.
	 */
	public String toString() {
		// This must be structured in a way suitable to write to file.
		return "";
	}
}
