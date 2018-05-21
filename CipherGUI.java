import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField;
	private static JTextField messageField;
	private JLabel keyLabel, messageLabel;

	//application instance variables
	//including the 'core' part of the textfile filename
	//some way of indicating whether encoding or decoding is to be done
	private MonoCipher mcipher;
	private VCipher vcipher;
	
	//object for frequency report
	private LetterFrequencies frequencyReport;  
	
	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{
		this.setSize(400,150);
		this.setLocation(100,100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}
	
	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);
		
		//middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);
		
		//bottom panel is green and contains 2 buttons
		
		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}
	
	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{
		frequencyReport = new LetterFrequencies(); //initialising frequency report object
		if (e.getSource() == monoButton) {
			if (this.getKeyword()) {
				if (this.processFileName() == true) {
					mcipher = new MonoCipher(keyField.getText());
					if(this.processFile(false) == true) {
						//when processing of file was successful the frequency report is written
						String outputFileFrequencyReport = "messageF.txt"; //name of frequency report file 
						FileWriter writerFrequencyReport = null;
						try {
							try {
								writerFrequencyReport = new FileWriter(outputFileFrequencyReport);
								writerFrequencyReport.write(frequencyReport.getReport());
							}
							finally {
								if (writerFrequencyReport != null) {
									writerFrequencyReport.close(); 
								}
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						System.exit(0);
					}
				}
			} 
		} else if (e.getSource() == vigenereButton) {
					if (this.getKeyword()) {
						if (this.processFileName()==true) {
							vcipher = new VCipher(keyField.getText());
							if(this.processFile(true)== true) {
								//when processing of file was successful the frequency report is written
								String outputFileFrequencyReport = "messageF.txt";
								FileWriter writerFrequencyReport = null;
								try {
									try {
										writerFrequencyReport = new FileWriter(outputFileFrequencyReport);
										writerFrequencyReport.write(frequencyReport.getReport());
									}
									finally {
										if (writerFrequencyReport != null) {
											writerFrequencyReport.close(); 
										}
									}
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								System.exit(0);
							}
						}
					}
				}
			}
	
	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword()
	{
		String keyword = keyField.getText();
		if (keyword.equals("")) { //check if keyword field is empty
			JOptionPane.showMessageDialog(null, "Please enter a correct keyword!", "input error", JOptionPane.ERROR_MESSAGE);
			keyField.setText("");
			//System.out.println("empty string");
			return false;
		} else {
			for (int i=0; i< keyword.length(); i++) {
				if(!Character.isLetter(keyword.charAt(i)) || Character.isLowerCase(keyword.charAt(i))) { //check if keyword field input are letters and uppercase
					JOptionPane.showMessageDialog(null, "Please enter a correct keyword!", "input error", JOptionPane.ERROR_MESSAGE);
					keyField.setText("");
					//System.out.println("not correct format"); 
					return false; 
				} else {
					for(int j=(i+1); j<keyword.length(); j++) {
						if (keyword.charAt(i) == keyword.charAt(j)) { //check if keyword has repeating characters 
							JOptionPane.showMessageDialog(null, "Please enter a correct keyword!", "input error", JOptionPane.ERROR_MESSAGE);
							keyField.setText("");
							//System.out.println("double letter");
							return false; 
						} 
					}
				}
			}
		}
		return true; 
	}
	
	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced 
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName()
	{
		String fileName = messageField.getText(); 
		if (fileName.equals("") || !(fileName.charAt(fileName.length()-1) == 'C') && !(fileName.charAt(fileName.length()-1) == 'P') ) {
			JOptionPane.showMessageDialog(null, "Please enter correct file name", "input error", JOptionPane.ERROR_MESSAGE);
			messageField.setText("");
			return false; 
		}
	    return true; 
	}
	
	/** 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere)
	{
		String inputFileName = messageField.getText() + ".txt"; //get file name and adding .txt 
		FileReader reader = null;
		char character = 0;
		String outputFileNameEncryption = "messageC.txt"; //name of encrypted file
		String outputFileNameDecryption = "messageD.txt"; //name of decrypted file 
		boolean doneReadingFile = false; 
		FileWriter writerEncrypted = null;
		FileWriter writerDecrypted = null; 
		if (vigenere == false) { //monocipher button was pressed
			if (messageField.getText().charAt(messageField.getText().length()-1) == 'P') { //filename ending on P 
				try {		
					try {
						reader = new FileReader(inputFileName);
						writerEncrypted = new FileWriter(outputFileNameEncryption);
						while (!doneReadingFile) {
							int nextCharacter = reader.read(); 
							if (nextCharacter == -1) {
								doneReadingFile = true; 
								reader.close();
								writerEncrypted.close();
							} else {
								character = (char) nextCharacter;
								if (character >= 65 && character <= 90) { 
									char encodedchar = mcipher.encode(character);
									writerEncrypted.write(encodedchar); //encoded char written to messageC
									frequencyReport.addChar(encodedchar); //encoded char added to alphaCounts
								} else {
									writerEncrypted.write(character);
								}	
							} 
							
						}
					}
					finally {
						if (reader != null) {
							reader.close();
						} else if (writerEncrypted != null) {
							writerEncrypted.close();
						} 
					}
				}
				catch (FileNotFoundException e) {
					//System.out.println("no file found");
					JOptionPane.showMessageDialog(null, "no file with this name was found", "no file found", JOptionPane.ERROR_MESSAGE);
					messageField.setText("");
					return false; 
				} catch (IOException ex) {
					System.out.println("IO Excpetion thrown");
					return false; 
				}
				
			} else if (messageField.getText().charAt(messageField.getText().length()-1) == 'C')  { //filename ending on C
				try {		
					try { 
						reader = new FileReader(inputFileName);
						writerDecrypted = new FileWriter(outputFileNameDecryption);
						while (!doneReadingFile) {
							int nextCharacter = reader.read(); 
							if (nextCharacter == -1) {
								doneReadingFile = true; 
								reader.close();
								writerDecrypted.close();
							} else {
								character = (char) nextCharacter;
								if (character >= 65 && character <= 90) {
									char decodedchar = mcipher.decode(character);
									writerDecrypted.write(decodedchar); //decoded char written to messageD
									frequencyReport.addChar(decodedchar); //decoded char added to alphaCounts
								} else {
									writerDecrypted.write(character);
								}		
							} 
						}
					}
					finally {
						if (reader != null) {
							reader.close();
						} else if (writerDecrypted != null) {
							writerDecrypted.close();
						} 
					}
				}
				catch (FileNotFoundException e) {
					//System.out.println("no file found");
					JOptionPane.showMessageDialog(null, "no file with this name was found", "no file found", JOptionPane.ERROR_MESSAGE);
					messageField.setText("");
					return false; 
				} catch (IOException ex) {
					System.out.println("IO Excpetion thrown");
					return false; 
				}
			}
		} else { //vcipher button was pressed
			if (messageField.getText().charAt(messageField.getText().length()-1) == 'P') { //filename ending on P
				try {		
					try {
						reader = new FileReader(inputFileName);
						writerEncrypted = new FileWriter(outputFileNameEncryption);
						while (!doneReadingFile) {
							int nextCharacter = reader.read(); 
							if (nextCharacter == -1) {
								doneReadingFile = true; 
								reader.close();
								writerEncrypted.close();
							} else {
								character = (char) nextCharacter;
								if (character >= 65 && character <= 90) { 									
									char encodedchar = vcipher.encode(character);
									writerEncrypted.write(encodedchar); //encoded char written to messageC
									frequencyReport.addChar(encodedchar); //encoded char added to alphaCounts
								} else {
									writerEncrypted.write(character);
								}	
							} 
							
						}
					}
					finally {
						if (reader != null) {
							reader.close();
						} else if (writerEncrypted != null) {
							writerEncrypted.close();
						} 
					}
				}
				catch (FileNotFoundException e) {
					//System.out.println("no file found");
					JOptionPane.showMessageDialog(null, "no file with this name was found", "no file found", JOptionPane.ERROR_MESSAGE);
					messageField.setText("");
					return false; 
				} catch (IOException ex) {
					System.out.println("IO Excpetion thrown");
					return false; 
				}
				
			} else if (messageField.getText().charAt(messageField.getText().length()-1) == 'C')  { //filename ending on C
				try {		
					try {
						reader = new FileReader(inputFileName);
						writerDecrypted = new FileWriter(outputFileNameDecryption);
						while (!doneReadingFile) {
							int nextCharacter = reader.read(); 
							if (nextCharacter == -1) {
								doneReadingFile = true; 
								reader.close();
								writerDecrypted.close();
							} else {
								character = (char) nextCharacter;
								if (character >= 65 && character <= 90) {
									char decodedchar = vcipher.decode(character);
									writerDecrypted.write(decodedchar); //decoded char written to messageD
									frequencyReport.addChar(decodedchar); //decoded char added to alphaCounts
								} else {
									writerDecrypted.write(character);
								}		
							} 
						}
					}
					finally {
						if (reader != null) {
							reader.close();
						} else if (writerDecrypted != null) {
							writerDecrypted.close();
						} 
					}
				}
				catch (FileNotFoundException e) {
					//System.out.println("no file found");
					JOptionPane.showMessageDialog(null, "no file with this name was found", "no file found", JOptionPane.ERROR_MESSAGE);
					messageField.setText("");
					return false; 
				} catch (IOException ex) {
					System.out.println("IO Excpetion thrown");
					return false; 
				}
			}
		}
		
		return doneReadingFile;
	}
}
