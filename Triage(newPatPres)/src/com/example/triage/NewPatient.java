package com.example.triage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import patient.PatientManager;
import patient.triageExceptions.PatientNotFoundException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewPatient extends Activity {
	
	private PatientManager manager;
	boolean anyFilesLoaded = false;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_patient);
		
		intent = getIntent();
		manager = (PatientManager) intent.getSerializableExtra("manager");
		anyFilesLoaded = intent.getBooleanExtra("anyFilesLoaded", false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_patient, menu);
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
	 * Sends PatientManager "manager" and boolean "anyFilesLoaded" to Main Activity.
	 * @param view
	 */
	public void goToMenu(View view) {
		intent = new Intent(this, MainActivity.class);

		intent.putExtra("manager", manager);
		intent.putExtra("anyFilesLoaded", anyFilesLoaded);

		startActivity(intent);
	}
	
	/**
	 * Create a new PatientRecord and text file for a new patient. 
	 * Also write to patient_record.txt with new patient info.
	 * @param view
	 * @throws IOException 
	 */
	public void createNewPatient(View view) throws IOException {
		
		
		try {
			// Get the patient's health card from textfield
			EditText patientHealthCardInput = (EditText) findViewById(R.id.new_patient_health_card);
			String newPatientHealthCard = patientHealthCardInput.getText().toString();
			
			// Check if the patient already exists.
			manager.getPatientRecord(newPatientHealthCard);
			// Print a message to user saying the patient already exists.
			Context context = getApplicationContext();
			CharSequence text;
			int duration = Toast.LENGTH_SHORT;
			text = "Patient already exists.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
		} 
		// If the patient doesn't exist, proceed to create new patient and it's .txt file.
		catch (PatientNotFoundException e){
			
			// Get the patient's health card from textfield
			EditText patientHealthCardInput = (EditText) findViewById(R.id.new_patient_health_card);
			String newPatientHealthCard = patientHealthCardInput.getText().toString();
			
			// Get the patient's name from textfield
			EditText patientNameInput = (EditText) findViewById(R.id.new_patient_name);
			String newPatientName = patientNameInput.getText().toString();
			
			// Get the patient's date of birth from textfield
			EditText patientDOBInput = (EditText) findViewById(R.id.new_patient_dob);
			String newPatientDOB = patientDOBInput.getText().toString();
			
			//Create new patient and add it to the PatientManager
			manager.createNewPatient(newPatientName, newPatientDOB, newPatientHealthCard);
			
			// Creates a string will patient's basic info to be written in patient_records.txt
			String toPatientList = "\n" + newPatientHealthCard + "," + newPatientName + "," + newPatientDOB ;
			
			// Add new patient to patient_records.txt
			File file = new File(getFilesDir(), "patient_records.txt");
			FileOutputStream patientRecordOutputStream = openFileOutput(file.getName(), MODE_APPEND);
			patientRecordOutputStream.write(toPatientList.getBytes());
			patientRecordOutputStream.close();
			
			// Creates a string will patient's basic info to be written in patient's personal .txt file
			String toPatientFile = newPatientHealthCard + "," + newPatientName + "," + newPatientDOB + "\n" ;

			// Make a .txt using health card number
			File tempFile = new File(getFilesDir(), newPatientHealthCard + ".txt");
			tempFile.createNewFile();
			FileOutputStream tempOutputStream = openFileOutput(tempFile.getName(), MODE_PRIVATE);
			// Write patient's basic info as the first line of the .txt file.
			tempOutputStream.write(toPatientFile.getBytes());
			tempOutputStream.close();
			
			Context context = getApplicationContext();
			CharSequence text;
			int duration = Toast.LENGTH_SHORT;
			text = "Thanks! Patient has been added.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		
		
	}
}
