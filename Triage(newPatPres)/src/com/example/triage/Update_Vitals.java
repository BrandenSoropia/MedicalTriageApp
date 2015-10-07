package com.example.triage;

import patient.PatientManager;
import patient.triageExceptions.NotCurrentPatientException;
import patient.triageExceptions.PatientNotFoundException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Update_Vitals extends Activity {

	private PatientManager manager;
	boolean anyFilesLoaded = false;
	String healthcard;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update__vitals);

		intent = getIntent();
		manager = (PatientManager) intent.getSerializableExtra("manager");
		anyFilesLoaded = intent.getBooleanExtra("anyFilesLoaded", false);
		healthcard = intent.getExtras().getString("healthcard");

	}
	
	/**
	 * Updates the vitals of a patient in the PatientManager and 
	 * sends PatientManager "manager", boolean "anyFilesLoaded" and 
	 * String "healthcard" to Pateint Record Activity.
	 * @param view
	 */
	public void updateVitals(View view) {
		try {
			
			if (!manager.getPatientRecord(healthcard).isCurrentPatient()){
				
				throw new NotCurrentPatientException();
			}
			
			EditText temperatuerEditText = (EditText) findViewById(R.id.TemperatureEditText);
			String temperatureString = temperatuerEditText.getText().toString();
			double temperature = Double.valueOf(temperatureString);

			EditText systolicEditText = (EditText) findViewById(R.id.SystolicEditText);
			String systolicString = systolicEditText.getText().toString();
			double systolic = Double.valueOf(systolicString);

			EditText diastolicEditText = (EditText) findViewById(R.id.DiastolicEditText);
			String diastolicString = diastolicEditText.getText().toString();
			double diastolic = Double.valueOf(diastolicString);

			double[] bloodPressure = { systolic, diastolic };

			TextView heartrateEditText = (TextView) findViewById(R.id.HeartRateEditText);
			String heartrateString = heartrateEditText.getText().toString();
			double heartrate = Double.valueOf(heartrateString);

			manager.getPatientRecord(healthcard)
					.getCurrentVisitRecord()
					.updateVitals(PatientManager.getCurrentTime(), temperature,
							heartrate, bloodPressure);
			
			intent = new Intent(this, PateintRecord.class);
			intent.putExtra("manager", manager);
			intent.putExtra("anyFilesLoaded", anyFilesLoaded);
			intent.putExtra("healthcard", healthcard);
			startActivity(intent);

		} catch (PatientNotFoundException e) {

			Context context = getApplicationContext();
			CharSequence text;
			int duration = Toast.LENGTH_SHORT;
			text = "There is no patient with that healthcard";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		} catch (NotCurrentPatientException e){
			
			Context context = getApplicationContext();
			CharSequence text;
			int duration = Toast.LENGTH_SHORT;
			text = "Not current patient";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
		}
	}
	
	/**
	 * Sends PatientManager "manager" and boolean "anyFilesLoaded" to
	 * Main Activity.
	 * @param view
	 */
	public void goToMenu(View view) {
		intent = new Intent(this, MainActivity.class);

		intent.putExtra("manager", manager);
		intent.putExtra("anyFilesLoaded", anyFilesLoaded);

		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update__vitals, menu);
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
}
