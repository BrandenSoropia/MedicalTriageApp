package com.example.triage;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import patient.PatientManager;
import patient.PatientRecord;
import patient.PatientVisitRecord;
import patient.Prescription;
import patient.Symptoms;
import patient.Vitals;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private PatientManager manager;
	boolean anyFilesLoaded = false;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		intent = getIntent();
		manager = (PatientManager) intent.getSerializableExtra("manager");
		anyFilesLoaded = intent.getBooleanExtra("anyFilesLoaded", false);

			
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * Sends PatientManager "manager" and boolean "anyFilesLoaded" to the Search Activity.
	 * @param view
	 */
	public void goToSearch(View view) {
		intent = new Intent(MainActivity.this, SearchActivity.class);
		if (anyFilesLoaded) {
			intent.putExtra("manager", manager);
			intent.putExtra("anyFilesLoaded", anyFilesLoaded);

		} else {
			intent.putExtra("anyFilesLoaded", anyFilesLoaded);

		}

		startActivity(intent);
	}
	
	/**
	 * Sends PatientManager "manager" and boolean "anyFilesLoaded" to the Check In Activity.
	 * @param view
	 */
	public void goToCheckIn(View view) {
		intent = new Intent(MainActivity.this, Check_in.class);

		if (anyFilesLoaded) {
			intent.putExtra("manager", manager);
			intent.putExtra("anyFilesLoaded", anyFilesLoaded);

		} else {
			intent.putExtra("anyFilesLoaded", anyFilesLoaded);

		}

		startActivity(intent);
	}
	
	/**
	 * Reads the patient_records.txt and creates a unique text file for each patient using
	 * their health card number as the file name. Also builds the PatientManager
	 * by reading the patiet's saved files.
	 * @param view
	 * @throws IOException
	 */
	public void loadProfile(View view) throws IOException {
		// Get's this files directory and finds the text file.
		File file = new File(getFilesDir(), "patient_records.txt");
		Context context = getApplicationContext();
		CharSequence text;
		int duration = Toast.LENGTH_SHORT;

		try {

			Scanner scanner = new Scanner(new FileInputStream(file.getPath()));
			ArrayList<String> record = new ArrayList<String>();
			ArrayList<String> patientInfo = new ArrayList<String>();

			// Go through each line of file and split each line on the commas
			while (scanner.hasNextLine()) {

				String[] splitString = scanner.nextLine().split(",");
				// Store each health card number

				String healthCard = splitString[0];

				// Adds the health card as file names to the ArrayList record
				record.add(healthCard + ".txt");

				// Store the string's data separated by $$ for each item
				String data = splitString[0] + "$" + splitString[1] + "$"
						+ splitString[2] + "\n";
				// Adds the Patient Info as file names to the ArrayList
				// patientInfo
				patientInfo.add(data);

			}

			// Checks if file already exists with that name
			// Used to keep track of each line, therefore keeping file with
			// correct data.
			int i = 0;

			// Used to store all the PatientRecords made
			ArrayList<PatientRecord> listOfPatientRecords = new ArrayList<PatientRecord>();

			// Makes PatientRecord objects for each file

			for (String filename : record) {

				File tempFile = new File(getFilesDir(), filename);

				if (!tempFile.exists()) {
					tempFile.createNewFile();
					FileOutputStream tempOutputStream = openFileOutput(
							tempFile.getName(), MODE_PRIVATE);
					tempOutputStream.write(patientInfo.get(i).getBytes());
					tempOutputStream.close();
				}

				i = i + 1;

				// Set up of scanner

				scanner = new Scanner(new FileInputStream(tempFile.getPath()));
				// Add PatientRecord to array
				listOfPatientRecords.add(readScannerParse(scanner));
				scanner.close();
			}
			
			// Finally, build the PatientManager
			
			manager = new PatientManager(listOfPatientRecords);
			anyFilesLoaded = true;
			
			text = "Done loading.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

		} catch (FileNotFoundException e) {
			text = "Failed to load file.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

		}
		

	}
	/**
	 * Writes data saved in the FileBuffer to the respective patient's text file.
	 * 
	 * @param view
	 * @throws IOException
	 */
	public void saveFileBuffers(View view) throws IOException {
		
		if (anyFilesLoaded) {
			ArrayList<PatientRecord> records = manager.getPatientRecords();

			for (PatientRecord record : records) {

				// Writes to patient file if there exists a PatientVisitRecrod for this patient
				if (!record.getPatientVisitRecords().isEmpty()){
					String[] buffer = record.getCurrentVisitRecord()
							.getSaveFileBuffer();

					FileOutputStream outputStream = openFileOutput(buffer[0]
							+ ".txt", MODE_APPEND);
					try {
						outputStream.write(buffer[1].getBytes());
						record.getCurrentVisitRecord().flushBuffer();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {

						outputStream.close();

					}
					
					
					
					
				}
				

			}
		}
		Context context = getApplicationContext();
		CharSequence text;
		int duration = Toast.LENGTH_SHORT;
		text = "Done Saving.";
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

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

	/**
	 * Return a PatientRecord object when given a Scanner of a patient text file.
	 * 
	 * @param lines
	 * @return PatientRecord
	 */
	public PatientRecord readScannerParse(Scanner lines) {

		ArrayList<PatientVisitRecord> patientVisitRecords = new ArrayList<PatientVisitRecord>();

		// main variables

		String firstLine = lines.nextLine();
		String[] mainData = firstLine.split("\\$");
		String name = mainData[1];
		String healthCardNumber = mainData[0];
		int[] dateOfBirth = timeStringToInt(mainData[2].split("-"));
		boolean currentPatient = false;
		int[] arrivalTime = null;
		String[] saveFileBuffer = { healthCardNumber, new String() };
		Vitals newVitals = null;
		Symptoms newSymptoms = null;
		boolean visitedByDoctor = false;
		int[] doctorVisitTime = null;
		String nextLine;
		Prescription patientPrescription = null;

		// running the parser
		while (lines.hasNextLine()) {

			// storing the nextline

			nextLine = lines.nextLine();

			// if not
			// cuSystem.out.println(s.split("\\$")[0]);System.out.println(s.split("\\$")[0]);rrent
			// patient make them one and store time
			if (!currentPatient) {

				if (nextLine.startsWith("\\NewVisit ")) {
					arrivalTime = timeStringToInt(nextLine.substring(10).split(
							"-"));
					currentPatient = true;

				}
			}

			else {

				// if current patient and line is vitals update then rip
				// time>temp>bloodpressure>heartrate then update vitals for
				// visit
				if (nextLine.startsWith("\\UpdateVitals ")) {

					String[] vitalsData = nextLine.substring(14).split("\\$");

					int[] timeTaken = timeStringToInt(vitalsData[0].split("-"));
					double temp = Double.valueOf(vitalsData[1]).floatValue();

					String[] stringBloodPressure = vitalsData[2].split("-");
					double[] bloodPressure = {
							Double.valueOf(stringBloodPressure[0]).floatValue(),
							Double.valueOf(stringBloodPressure[1]).floatValue() };

					double heartRate = Double.valueOf(vitalsData[3])
							.floatValue();

					newVitals = new Vitals(timeTaken, temp, bloodPressure,
							heartRate, newVitals);

					// if current patient and line is symptoms update then
					// rip time>each of symptoms descriptions and update
					// symptoms for visit
				}

				else if (nextLine.startsWith("\\UpdateSymptoms ")) {

					String[] symptomsData = nextLine.substring(15).split("\\$");
					int[] timeTaken = timeStringToInt(symptomsData[0]
							.split("-"));
					ArrayList<String> currentSymptomsDescription = new ArrayList<String>();

					// changed from "," to "&&" in symptoms class harma's
					// copy does not have change.
					for (String str : symptomsData[0].split("&")) {
						currentSymptomsDescription.add(str);
					}

					newSymptoms = new Symptoms(timeTaken,
							currentSymptomsDescription, newSymptoms);

					// if the line is a visit by doctor and getting time it
					// happened.
				}

				else if (nextLine.startsWith("\\UpdateDoctorVisitInformation ")) {

					visitedByDoctor = true;
					doctorVisitTime = timeStringToInt(nextLine.substring(30)
							.split("-"));

					// closing a visiting by making visit record adding it
					// to patient record and resetting variables for next
					// visit
				}
				// \\UpdatePrescription time$medication$instruction
				else if (nextLine.startsWith("\\UpdatePrescription ")) {
					
					
					String[] prescriptionData = nextLine.substring(21).split("\\$");
					
					int[] prescriptionTime = timeStringToInt(prescriptionData[0].split("-"));
					String medication = prescriptionData[1];
					String instructions = prescriptionData[2];
					patientPrescription = new Prescription(medication, instructions, prescriptionTime); 
					
				} else if (nextLine.startsWith("\\EndVisit")) {
					// Makes a PatientVisitRecord then stores it in patient's visit history
					PatientVisitRecord currentVisit = new PatientVisitRecord(
					arrivalTime, dateOfBirth, newVitals, newSymptoms,
					visitedByDoctor, doctorVisitTime, saveFileBuffer, patientPrescription);
					patientVisitRecords.add(currentVisit);

					currentPatient = false;
					newVitals = null;
					newSymptoms = null;
					visitedByDoctor = false;
					doctorVisitTime = null;
					patientPrescription = null;

				}

			}

		}

		// if still current patient when the file ends then create last visit
		// record with information had and add it to patientRecord and return
		// patientRecord

		if (currentPatient) {
			PatientVisitRecord currentVisit = new PatientVisitRecord(
					arrivalTime, dateOfBirth, newVitals, newSymptoms,
					visitedByDoctor, doctorVisitTime, saveFileBuffer, patientPrescription);
			patientVisitRecords.add(currentVisit);
			return new PatientRecord(name, dateOfBirth, healthCardNumber,
					currentPatient, patientVisitRecords);

		} else {
			return new PatientRecord(name, dateOfBirth, healthCardNumber,
					currentPatient, patientVisitRecords);
		}

	}
	
	public void goToNewPatientActivity(View view) {
		Intent intent = new Intent(MainActivity.this, NewPatient.class);
		intent.putExtra("manager", manager);
		intent.putExtra("anyFilesLoaded", anyFilesLoaded);
		startActivity(intent);
	}
}