/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;
	
	/** Count for each letter */
	private int [] alphaCounts;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};

	/** Character that occurs most frequently */
	private char maxCh;

	/** Total number of characters encrypted/decrypted */
	private int totChars;
	
	//highest frequency of a letter in text file
	int maxChAsInt;
	
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{
	    //alphabet 
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
		//creating alphaCounts array to calculate frequency for each letter in text file 
		alphaCounts = new int [SIZE]; 
		//initialising the total number of chars
		totChars = 0;
		//initialising the highest frequency of a letter
		maxChAsInt = 0; 
	}
		
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{
		//frequency count for each letter 
		{
			for(int i=0; i<SIZE; i++) {
				if (ch == alphabet[i]) {
					alphaCounts[i] += 1;  	
				} 
			}
			totChars++; //for each letter anaylised the total number of chars is increased by 1
		} 
	}
	
	/**
	 * Gets the maximum frequency
	 * @return the maximum frequency
	 */
	private double getMaxPC()
        {
		//most frequently occurred character - if multiple letters with the same frequency the last in the alphabet is returned
				for (int index=0; index<SIZE; index++) {
					if (alphaCounts[index] >= maxChAsInt) {
						maxChAsInt =  alphaCounts[index]; //highest frequency in alphaCounts array 
						maxCh = alphabet[index]; //most frequent letter as char
					}
				}
		double mostFrequentLetterInPercent = ((double) 100/totChars)*maxChAsInt;
	    return mostFrequentLetterInPercent;  
	}
	
	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	public String getReport()
	{
		StringBuilder report = new StringBuilder(); //creating a string builder to create report string
		report.append(String.format("LETTER ANALYSIS" + "\r\n" + "\r\n"));
		report.append(String.format("%15s", "Letter"));
		report.append(String.format("%15s", "Freq"));
		report.append(String.format("%15s", "Freq%"));
		report.append(String.format("%15s", "AvgFreq%"));
		report.append(String.format("%15s", "Diff" + "\r\n"));
		for(int i=0; i<SIZE; i++) {
		report.append(String.format("%15s", (char) alphabet[i])); //appending alphabet array 
		report.append(String.format("%15s", alphaCounts[i]));  //appending alphaCounts array 
		report.append(String.format("%15s", String.format("%.1f", calculateFrequencies(alphaCounts[i])))); //appending frequencies of letters in %
		report.append(String.format("%15s", Double.toString(avgCounts[i])));  //appending average frequencies 
		report.append(String.format("%15s", String.format("%.1f", calculateDifferencesInFrequencies(calculateFrequencies(alphaCounts[i]),avgCounts[i])) + "\r\n"));//appending differences in frequency 
		}
		double mostFrequentCh = this.getMaxPC();
		report.append("The most frequent letter is "+ maxCh +" at "+ String.format("%.1f", mostFrequentCh) + "%"); //appending most frequent letter as last sentences 
	    return report.toString();  // replace with your code
	}
	
	/**
	 * calculate frequencies in percent
	 * @param frequency
	 * @return frequency in percent
	 */
	private double calculateFrequencies (int frequency) {
		double frequencyPercent = ((double) 100/totChars)*frequency;
		return frequencyPercent; 
	}
	
	/**
	 * calculate difference between frequency in text file and average frequency 
	 * @param frequency
	 * @param avgFrequency
	 * @return difference of frequencies
	 */
	private double calculateDifferencesInFrequencies (double frequency, double avgFrequency) {
		double difference = frequency - avgFrequency; 
		return difference;
	}
}
