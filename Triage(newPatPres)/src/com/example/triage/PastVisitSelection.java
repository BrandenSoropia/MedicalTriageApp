package com.example.triage;

import java.util.ArrayList;

import patient.PatientManager;
import patient.PatientRecord;
import patient.PatientVisitRecord;
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

public class PastVisitSelection extends Activity {

	private PatientManager manager;
	private boolean anyFilesLoaded = false;
	private PatientVisitRecord currentVisit;
	private ArrayList<PatientVisitRecord> olderVisitsFromCurrent;
	private ArrayList<PatientVisitRecord> newerVisitsFromCurrent;
	private TextView vistTimeTakenTextBox;
	private PatientRecord record;
	private String healthcard;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_past_visit_selection);

		try {

			intent = getIntent();
			manager = (PatientManager) intent.getSerializableExtra("manager");
			anyFilesLoaded = intent.getBooleanExtra("anyFilesLoaded", false);
			healthcard = intent.getExtras().getString("healthcard");
			record = manager.getPatientRecord(healthcard);

			record.getPatientVisitRecords().get(
					record.getPatientVisitRecords().size() - 1);
			currentVisit = record.getCurrentVisitRecord();
			newerVisitsFromCurrent = new ArrayList<PatientVisitRecord>();
			olderVisitsFromCurrent = record.getPatientVisitRecords();
			

			this.setVisitArrivalTime();

		} catch (PatientNotFoundException e) {

		}

	}
	
	/**
	 * Gets an older PatientVisitRecord than the current PatientVisitRecord.
	 * 
	 * @param view
	 */
	public void getOlderVisitRecord(View view){
		// Checks if there are any older PatientVisitRecords than current
		if (olderVisitsFromCurrent.size() <= 1){
			Context context = getApplicationContext();
			CharSequence text;
			int duration = Toast.LENGTH_SHORT;
			text = "There are no older visit records.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
		} 
		// Add currentVisit to newerVisits and remove currentVisit from olderVisits. currentVisit becomes tail of of olderVisit
		else{
			
			newerVisitsFromCurrent.add(currentVisit);
			olderVisitsFromCurrent.remove(currentVisit);
			currentVisit = olderVisitsFromCurrent.get(olderVisitsFromCurrent.size() - 1);
			
			this.setVisitArrivalTime();
		
		}
		
	}
	/**
	 * Gets a newer PatientVisitRecord than the current PatientVisitRecord.
	 * @param view
	 */
	public void getNewerVisitRecord(View view) {
		// Case where there are no newer records
		if (newerVisitsFromCurrent.size() == 0){
			Context context = getApplicationContext();
			CharSequence text;
			int duration = Toast.LENGTH_SHORT;
			text = "There are no newer visit records.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
		}
		
		// Adds the currentVisit to olderVisits and currentVisit becomes the head of newVisit
		else {
			
			olderVisitsFromCurrent.add(currentVisit);
			currentVisit = newerVisitsFromCurrent.get(0);
			newerVisitsFromCurrent.remove(currentVisit);
			
			
			this.setVisitArrivalTime();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.past_visit_selection, menu);
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
	 * Gets the system's time and sets it as the value in a TextView.
	 */
	private void setVisitArrivalTime () {
		
		vistTimeTakenTextBox = (TextView) findViewById(R.id.visitTime);
		CharSequence vistTimeTakenText;
		int[] timeTakenInts = currentVisit.getArrivalTime();

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

		vistTimeTakenText = timeTakenStrings[0] + "-" + timeTakenStrings[1]
				+ "-" + timeTakenStrings[2] + "-" + timeTakenStrings[3]
				+ ":" + timeTakenStrings[4] + ":" + timeTakenStrings[5];

		vistTimeTakenTextBox.setText(vistTimeTakenText);

		
	
	}
	
	/**
	 * Sends PatientManager "manager" and boolean "anyFilesLoaded" to MainActivity.
	 * @param view
	 */
	public void goToMainMenu(View view) {
		intent = new Intent(this, MainActivity.class);

		intent.putExtra("manager", manager);
		intent.putExtra("anyFilesLoaded", anyFilesLoaded);

		startActivity(intent);
	}
	
	/**
	 * Sends PatientManager "manager", String "healthcard" and desired 
	 * PatientVisitRecord to the PastVisitVitalShow Activity.
	 * @param view
	 */
	public void goToSelectedVisitRecord(View view) {
		Intent intent = new Intent(this, PastVisitVitalShow.class);
		intent.putExtra("manager", manager);
		intent.putExtra("healthcard", healthcard);
		PatientVisitRecord wantedVisit = currentVisit;
		intent.putExtra("wantedVisit", wantedVisit);
		startActivity(intent);
	}
}
