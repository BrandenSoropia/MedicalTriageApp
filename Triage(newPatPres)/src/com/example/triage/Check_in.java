package com.example.triage;

import patient.PatientManager;
import patient.triageExceptions.CurrentPatientException;
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

public class Check_in extends Activity {

	private PatientManager manager;
	private boolean anyFilesLoaded;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_in);

		intent = getIntent();
		manager = (PatientManager) intent.getSerializableExtra("manager");
		anyFilesLoaded = intent.getBooleanExtra("anyFilesLoaded", false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_in, menu);
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
	 * Sends PatientManager "manager" and the boolean "anyFilesLoaded" to the MainActivity.
	 * @param view
	 */
	public void goToMenu(View view) {
		intent = new Intent(Check_in.this, MainActivity.class);

		intent.putExtra("manager", manager);
		intent.putExtra("anyFilesLoaded", anyFilesLoaded);

		startActivity(intent);
	}
	/**
	 * Checks in a patient using their health card number.
	 * 
	 * Raises "PatientNotFoundException" if no patient is found with that health card number.
	 * 
	 * @param view
	 * @throws PatientNotFoundException
	 */
	public void checkInHealthCard(View view) throws PatientNotFoundException {
		
		// Gets data input in the TemperatureEditText EditText field
		EditText healthCardEditText = (EditText) findViewById(R.id.TemperatureEditText);

		String healthCard = healthCardEditText.getText().toString();
		
		Context context = getApplicationContext();
		CharSequence text;
		int duration = Toast.LENGTH_SHORT;

		try {
			manager.createNewVisitRecord(healthCard);

			text = "New visit record has been created for this health card.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		} catch (PatientNotFoundException e) {

			text = "There is no patient with that healthcard";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

		} catch (CurrentPatientException c) {

			text = "The patient with that healthcard is a current Patient already";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();


		}

	}
	
	/**
	 * Checks out patient and writes to their respective file. 
	 * @param view
	 */
	public void checkOutHealthCard(View view){
		
		EditText healthCardEditText = (EditText) findViewById(R.id.TemperatureEditText);
		
		String healthCard = healthCardEditText.getText().toString();
		Context context = getApplicationContext();
		CharSequence text;
		int duration = Toast.LENGTH_SHORT;


		try {
			if (!manager.getPatientRecord(healthCard).isCurrentPatient()){
				text = "The patient is already checked out.";
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
	
			} else {
				manager.getPatientRecord(healthCard).getCurrentVisitRecord().endPatientVisit(PatientManager.getCurrentTime());
				manager.getPatientRecord(healthCard).setPatientCheckedIn();
			text = "The patient has been checked out.";
			Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
			
			
		} catch (PatientNotFoundException e) {

		text = "There is no patient with that healthcard";
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

		
		
		
	}
}
}
