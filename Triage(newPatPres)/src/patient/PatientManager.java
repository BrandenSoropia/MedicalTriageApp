package patient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import patient.triageExceptions.CurrentPatientException;
//import patient.triageExceptions.IdenticalTimeException;
import patient.triageExceptions.PatientNotFoundException;

/**
 * @author Arthur Przech
 * @editor Harmandeep Sran
 * @version 1.1
 */
public class PatientManager implements Serializable {

	/**
	 * Fields
	 * 
	 * serialVersionUID - serialization ID.
	 * 
	 * patientRecords - every patient record on file.
	 */
	private static final long serialVersionUID = -7495688024720438992L;
	private ArrayList<PatientRecord> patientRecords;

	/**
	 * Constructor for a patient manager object.
	 * 
	 * @param patientRecords
	 *            - a list of patient record objects (one per patient).
	 */
	public PatientManager(ArrayList<PatientRecord> patientRecords) {

		this.patientRecords = patientRecords;
	}

	/**
	 * Returns patient record for patient with given health card number.
	 * 
	 * @return the patient record corresponding to given health card number.
	 * @param healthCardNum
	 *            - patient's health card number
	 * @throws PatientNotFoundException
	 */
	public PatientRecord getPatientRecord(String healthCardNum)
			throws PatientNotFoundException {

		// Locate patient, if notrray found, throw exception
		for (PatientRecord record : this.patientRecords) {
			if (healthCardNum.equals(record.getHealthCardNumber())) {
				return record;
			}
		}

		throw new PatientNotFoundException(
				healthCardNum
						+ " does not correspond to a health card number of any existing patient.");
	}

	/**
	 * Returns the current system time.
	 * 
	 * @return the current system time in the form of an array of 6 integers
	 *         (year, ... seconds).
	 */
	public static int[] getCurrentTime() {

		Calendar time = Calendar.getInstance();
		int year = time.get(Calendar.YEAR);
		int month = time.get(Calendar.MONTH);
		int day = time.get(Calendar.DAY_OF_MONTH);
		int hour = time.get(Calendar.HOUR);
		int minute = time.get(Calendar.MINUTE);
		int second = time.get(Calendar.SECOND);
		int[] currentTime = { year, month, day, hour, minute, second };
		return currentTime;
	}

	/**
	 * Given a health card number, create a new visit for this patient.
	 * 
	 * @param healthCardNum
	 *            - patient's health card number
	 * @throws PatientNotFoundException
	 * @throws CurrentPatientException
	 */
	public void createNewVisitRecord(String healthCardNum)
			throws PatientNotFoundException, CurrentPatientException {
		PatientRecord patientRec = this.getPatientRecord(healthCardNum);

		try {
			patientRec.createNewVisitRecord(getCurrentTime());
		}

		catch (CurrentPatientException e) {
			throw new CurrentPatientException();
		}
	}

	/**
	 * Returns every patient record on file.
	 * 
	 * @return all patient records.
	 */
	public ArrayList<PatientRecord> getPatientRecords() {
		return patientRecords;
	}
	
	/**
	 * Return an Array of Strings as an Array of int.
	 * 
	 * @param time
	 * @return
	 */
	private int[] timeStringToInt(String[] time) {
		// Get the length of the int Array
		int[] numberTime = new int[time.length];
		// Loops over String Array and converts the String to int
		for (int i = 0; i < time.length; i++) {
			
			numberTime[i] = Integer.valueOf(time[i]).intValue();

		}

		return numberTime;

	}
// TODO Added a create new patient in Patient Manager	
	/**
	 * Return a new PatientRecord with given strings name, date of birth
	 * and health card number and adds it to PatientManager.
	 * @param name 
	 * 			The patient's first and last name.
	 * @param dob
	 * 			The patient's date of birth in the form: "year-month-day".
	 * @param healthCard
	 * 			The patient's health card number.
	 */
	public void createNewPatient(String name, String dob, String healthCard) {

		// Split dob string by "-" and convert it to an int array
		int[] intDateOfBirth = timeStringToInt(dob.split("-"));
		// Create a new PatientRecord object with given info with an empty visit history and not a current patient
		PatientRecord newPatient = new PatientRecord(name, intDateOfBirth, healthCard, false, new ArrayList<PatientVisitRecord>());
		// Add to PatientManager's array
		patientRecords.add(newPatient);
	
	}

	/*
	 * IGNORE BELOW =====================================================
	 * 
	 * /**
	 * 
	 * @return a list of (current) patients who have not been visited by a
	 * doctor (in no particular order).
	 * 
	 * private ArrayList<PatientRecord> getPatientsNotVistedByDoctor() {
	 * 
	 * ArrayList<PatientRecord> patientsNotVistedByDoctor = new
	 * ArrayList<PatientRecord>();
	 * 
	 * // Check if the patient is a currently visiting? for (PatientRecord
	 * currentRecord : this.PatientRecords) {
	 * 
	 * if (!(currentRecord.getCurrentVisitRecord().isVisitedByDoctor())) {
	 * 
	 * patientsNotVistedByDoctor.add(currentRecord); } }
	 * 
	 * return patientsNotVistedByDoctor; }
	 * 
	 * /**
	 * 
	 * @return a list of (current) patients, not yet seen by a doctor, sorted by
	 * arrival time (earliest to latest).
	 * 
	 * @throws IdenticalTimeException
	 * 
	 * @throws IdenticalTimeException
	 * 
	 * public ArrayList<PatientRecord> getPatientsSortedByArrivalTime() throws
	 * IdenticalTimeException {
	 * 
	 * ArrayList<PatientRecord> currentList = getPatientsNotVistedByDoctor();
	 * ArrayList<PatientRecord> sortedList = new ArrayList<PatientRecord>(); int
	 * size = currentList.size(); // unchanging size
	 * 
	 * // going through the whole list once and picking the smallest arrival //
	 * time for (int i = 0; i < size; i++) {
	 * 
	 * PatientRecord currentSmallestArrivalTime = currentList.get(0); int
	 * removeIndex = 0;
	 * 
	 * for (int j = 0; j < currentList.size(); j++) {
	 * 
	 * if (!(currentSmallestArrivalTime.getCurrentVisitRecord()
	 * .isEarlierThan(currentList.get(j).getCurrentVisitRecord()))) {
	 * 
	 * currentSmallestArrivalTime = currentList.get(j); removeIndex = 0; }
	 * sortedList.add(currentSmallestArrivalTime);
	 * currentList.remove(removeIndex); } }
	 * 
	 * return sortedList; }
	 * 
	 * /**
	 * 
	 * @return a list of (current) patients, not yet seen by a doctor, sorted by
	 * urgency (most to least).
	 * 
	 * @throws IdenticalTimeException
	 * 
	 * public ArrayList<PatientRecord> getPatientsSortedByurgency() throws
	 * IdenticalTimeException {
	 * 
	 * ArrayList<PatientRecord> currentList = getPatientsNotVistedByDoctor();
	 * ArrayList<PatientRecord> sortedList = new ArrayList<PatientRecord>(); int
	 * size = currentList.size(); // unchanging size
	 * 
	 * // going through the whole list once and picking the smallest arrival //
	 * time for (int i = 0; i < size; i++) {
	 * 
	 * PatientRecord currentSmallestArrivalTime = currentList.get(0); int
	 * removeIndex = 0;
	 * 
	 * for (int j = 0; j < currentList.size(); j++) {
	 * 
	 * if (!(currentSmallestArrivalTime.getCurrentVisitRecord()
	 * .isEarlierThan(currentList.get(j).getCurrentVisitRecord()))) {
	 * 
	 * currentSmallestArrivalTime = currentList.get(j); removeIndex = 0; }
	 * sortedList.add(currentSmallestArrivalTime);
	 * currentList.remove(removeIndex); } }
	 * 
	 * return sortedList; }
	 */

}
