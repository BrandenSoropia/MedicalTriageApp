// TODO Made a Prescription class with time, medication and instructions
package patient;

public class Prescription {
	
	private String medication;
	private String instructions;
	private int[] timeTaken;
	
	/**
	 * Constructs a Prescription object with given Strings 
	 * medication and instructions. 
	 * 
	 * @param medication
	 * @param instructions
	 */
	public Prescription(String medication, String instructions, int[] timeTaken) {
		this.medication = medication;
		this.instructions = instructions;
		this.timeTaken = timeTaken;
	}
	
	/**
	 * Return a string containing the name of medication.
	 * prescribed.
	 * 
	 * @return
	 */
	public String getMedication() {
		return this.medication;
	}
	
	/**
	 * Return a string containing the instructions
	 * for the given prescription.
	 * 
	 * @return
	 */
	public String getInstructions() {
		return this.instructions;
	}
	
	/**
	 * Return an array of int as the time the prescription
	 * was given.
	 * 
	 * @return
	 */
	public int[] getTimeTaken() {
		return this.timeTaken;
	}
}
