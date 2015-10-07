package com.example.triage;

import patient.PatientManager;
import patient.triageExceptions.NotCurrentPatientException;
import patient.triageExceptions.PatientNotFoundException;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.content.Context;
import android.content.Intent;

public class SearchActivity extends Activity {

	private PatientManager manager;
	private boolean anyFilesLoaded;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		intent = getIntent();
		manager = (PatientManager) intent.getSerializableExtra("manager");
		anyFilesLoaded = intent.getBooleanExtra("anyFilesLoaded", false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
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
	 * Sends PatientManager "manager" and boolean "anyFilesLoaded" to
	 * Check In Activity.
	 * @param view
	 */
	public void goToCheckIn(View view) {
		Intent intent = new Intent(this, Check_in.class);
		intent.putExtra("manager", manager);
		intent.putExtra("anyFilesLoaded", anyFilesLoaded);
		startActivity(intent);
	}
	
	/**
	 * Sends PatientManager "manager", boolean "anyFilesLoaded" and String "healthcard" to
	 * Pateint Record Activity.
	 * 
	 * Throws NotCurrentPatientException if given patient is not checked in.
	 * 
	 * Throws PatientNotFoundException if patient is not in hospital records
	 * @param view
	 */
	public void searchForPatient(View view) {

		EditText healthcardEditText = (EditText) findViewById(R.id.cardSearch);
		String healthcard = healthcardEditText.getText().toString();
		try {
			manager.getPatientRecord(healthcard);
			
			if (manager.getPatientRecord(healthcard).getPatientVisitRecords().size() == 0){
				throw new NotCurrentPatientException();
			}	
			
			intent = new Intent(this, PateintRecord.class);
			intent.putExtra("manager", manager);
			intent.putExtra("anyFilesLoaded", anyFilesLoaded);
			intent.putExtra("healthcard", healthcard);
			startActivity(intent);

		} catch (NotCurrentPatientException e){
			Context context = getApplicationContext();
			CharSequence text;
			int duration = Toast.LENGTH_SHORT;
			text = "Patient has never been checked in.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
		} catch (PatientNotFoundException e) {

			Context context = getApplicationContext();
			CharSequence text;
			int duration = Toast.LENGTH_SHORT;
			text = "There is no patient with that healthcard";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		} 
		

	}

}
