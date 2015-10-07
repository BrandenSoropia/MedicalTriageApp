package com.example.triage;

import java.util.ArrayList;

import patient.PatientManager;
import patient.PatientRecord;
import patient.PatientVisitRecord;
import patient.Vitals;
import patient.triageExceptions.PatientNotFoundException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PastVisitVitalShow extends Activity {

	private PatientManager manager;
	private boolean anyFilesLoaded = false;
	private String healthcard;
	private Intent intent;
	private PatientVisitRecord wantedVisit;
	private TextView nameTextBox;
	private TextView dateOfBirthTextBox;
	private TextView healthcardTextBox;
	private TextView temperatureTextBox;
	private TextView systolicTextBox;
	private TextView diastolicTextBox;
	private TextView heartrateTextBox;
	private TextView timeTakenTextBox;
	private PatientRecord record;
	private Vitals currentVitals;
	private ArrayList<Vitals> newerVitals = new ArrayList<Vitals>();
	
	/**
	 * Populates the TextView in the PastVisitVitalShow Activity with
	 * patient's temperature, systolic, diastolic, heart rate and time
	 * when update taken.
	 * @param currentVitals
	 */
	private void setTextView(Vitals currentVitals) {
		
		temperatureTextBox = (TextView) findViewById(R.id._temp);
		CharSequence temperatureText;
		try {
			temperatureText = Double.toString(currentVitals.getTemp());

		} catch (NullPointerException e) {
			temperatureText = "null";
		}

		temperatureTextBox.setText(temperatureText);

		systolicTextBox = (TextView) findViewById(R.id._systolic);
		CharSequence systolicText;
		try {
			systolicText = Double.toString(currentVitals.getBloodPressure()[0]);

		} catch (NullPointerException e) {
			systolicText = "null";
		}

		systolicTextBox.setText(systolicText);

		temperatureTextBox.setText(temperatureText);

		diastolicTextBox = (TextView) findViewById(R.id._diastolic);
		CharSequence diastolicText;
		try {
			diastolicText = Double
					.toString(currentVitals.getBloodPressure()[1]);

		} catch (NullPointerException e) {
			diastolicText = "null";
		}

		diastolicTextBox.setText(diastolicText);

		heartrateTextBox = (TextView) findViewById(R.id.heart_rate);
		CharSequence heartrateText;
		try {
			heartrateText = Double.toString(currentVitals.getHeartRate());

		} catch (NullPointerException e) {
			heartrateText = "null";
		}

		heartrateTextBox.setText(heartrateText);

		heartrateTextBox.setText(heartrateText);

		timeTakenTextBox = (TextView) findViewById(R.id.time_taken);
		
		CharSequence timeTakenText;
		try {
			int[] timeTakenInts = currentVitals.getTimeTaken();

			String[] timeTakenStrings = new String[6];

			for (int i = 0; i < 6; i++) {
				boolean singleDigit = false;
				for (int j = 0; j < 10; j++) {

					if (timeTakenInts[i] == j) {

						singleDigit = true;

					}

				}

				if (!singleDigit) {

					timeTakenStrings[i] = Integer.valueOf(timeTakenInts[i])
							.toString();

				} else {
					timeTakenStrings[i] = "0"
							+ Integer.valueOf(timeTakenInts[i]).toString();
				}

			}

			timeTakenText = timeTakenStrings[0] + "-" + timeTakenStrings[1]
					+ "-" + timeTakenStrings[2] + "-" + timeTakenStrings[3]
					+ ":" + timeTakenStrings[4] + ":" + timeTakenStrings[5];

		} catch (NullPointerException e) {
			timeTakenText = "null";
		}

		timeTakenTextBox.setText(timeTakenText);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_past_visit_vital_show);

		try {

			intent = getIntent();
			manager = (PatientManager) intent.getSerializableExtra("manager");
			anyFilesLoaded = intent.getBooleanExtra("anyFilesLoaded", false);
			wantedVisit = (PatientVisitRecord) intent
					.getSerializableExtra("wantedVisit");
			healthcard = intent.getExtras().getString("healthcard");
			// Searches for PatientRecord

			record = manager.getPatientRecord(healthcard);
			record.getPatientVisitRecords().get(
					record.getPatientVisitRecords().size() - 1);

			currentVitals = wantedVisit.getCurrentVisitVitals();
			nameTextBox = (TextView) findViewById(R.id.name_older);
			CharSequence nameText = record.getName();
			nameTextBox.setText(nameText);

			dateOfBirthTextBox = (TextView) findViewById(R.id.dateOfBirth_older);
			int[] dateOfBirthInts = record.getDateOfBirth();
			CharSequence dateOfBirthText = dateOfBirthInts[0] + "-"
					+ dateOfBirthInts[1] + "-" + dateOfBirthInts[2];

			dateOfBirthTextBox.setText(dateOfBirthText);

			healthcardTextBox = (TextView) findViewById(R.id.healthcard_older);
			CharSequence healthCardText = healthcard;
			healthcardTextBox.setText(healthCardText);
			setTextView(currentVitals);

		} catch (PatientNotFoundException e) {

		}

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
	 * Sends PatientManager "manager", boolean "anyFilesLoaded", and String "healthcard"
	 * to PastVisitSelection Activity.
	 * @param view
	 */
	public void goToOlderVisits(View view) {
		intent = new Intent(this, PastVisitSelection.class);

		intent.putExtra("manager", manager);
		intent.putExtra("anyFilesLoaded", anyFilesLoaded);
		intent.putExtra("healthcard", healthcard);
		startActivity(intent);

	}
	
	/**
	 * Checks for an older PatientVisitRecord.
	 * @param view
	 */
	public void lookForOlderVitals(View view) {
		Vitals olderVitals;

		try {
			olderVitals = currentVitals.getOldVitals();
		} catch (NullPointerException e) {
			olderVitals = null;
		}

		if (olderVitals == null) {

			Context context = getApplicationContext();
			CharSequence text;
			int duration = Toast.LENGTH_SHORT;
			text = "There are no older vitals.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

		} else {
			newerVitals.add(currentVitals);
			currentVitals = olderVitals;
			setTextView(currentVitals);
		}
	}
	
	/**
	 * Looks for a newer PatientVisitRecord.
	 * @param view
	 */
	public void lookForNewerVitals(View view) {

		if (newerVitals.isEmpty()) {

			Context context = getApplicationContext();
			CharSequence text;
			int duration = Toast.LENGTH_SHORT;
			text = "There are no newer vitals.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

		} else {

			int sizeNewerVitals = newerVitals.size();
			currentVitals = newerVitals.get(sizeNewerVitals - 1);
			newerVitals.remove(sizeNewerVitals - 1);
			setTextView(currentVitals);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.past_visit_vital_show, menu);
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
