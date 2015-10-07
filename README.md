# MedicalTriageApp
Result of CSC207: Software Design final project. 

This is an Android application to collect patient information and perform triage for patients
who arrive at our hospital emergency room (ER). When a patient arrives at the ER, a nurse
records their personal data (name, birthdate, and health card number), symptoms, and vital
signs. Prioritize patients into categories of urgency based on the hospital
policy (seen below). the nurse may check and record the symptoms and vitals
periodically to re-categorize. The program reports whether the patient is improving or
worsening over time. When a patient has been seen by a doctor, the nurse makes a record of
that.

Additional Features:
1. Nurses and physcians can launch the triage application and log in using a username and
password, which loads saved data, if it exists. In our, unrealistic, implementation, all
username passwards are stored in the file passwords.txt that we give you.
2. Nurses can create new patient records and record individual patient data (name, birth date,
and health card number)
3. Nurses can record the date and time when a patient has been seen by a doctor.
4. Nurses can access a list of patients (name, birth date and health card number) who have not
yet been seen by a doctor categorized and ordered by decreasing urgency according to
hospital policy.
5. Using the health card number, physicians can look up a patient's record, which contains all
data recorded about that patient.
6. Physicians can record prescription information (name of the medication and instructions) for a
given patient. (Notice that this information becomes part of the patient's record.

Hospital Policy:

Assign 1 point for each of the following:
 age < 2 years
 temperature >= 39.0 degrees Celsius
 blood pressure (measured in systolic/diastolic):
 systolic >= 140 or diastolic >= 90
 heart rate >= 100 or <= 50

Based on points, patients are categorized as follows:
 Urgent (3-4 points)
 Less Urgent (2 points)
 Non Urgent (1 point)
