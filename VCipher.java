/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
public class VCipher {
	private char [] alphabet;   //the letters of the alphabet
	private final int SIZE = 26;
        // more instance variables
	private char [][] vcipher;  //two dimensional array for encryption and decryption  
	private int keywordLength; 
	private int letterFromFileCounter; 
	
	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword) {
		//creating alphabet array
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++) {
			alphabet[i] = (char)('A' + i);
		}
		//creating vcipher array 
		vcipher = new char [keyword.length()] [SIZE]; 
		//int cipherContentCount = 0; 
		for (int i=0; i< (keyword.toCharArray().length) ; i++) {
			int k=0;
			for(int j=0; j<SIZE;j++) {
				vcipher[i][j] = (char) (keyword.toCharArray()[i]+k); 
				k++;
				if((keyword.toCharArray()[i]+k) == 91) {
					k -= 26; 
				}
		//		cipherContentCount++;
			}
		}
		//initiliasing keyword length to keywordLength
		this.keywordLength = keyword.length(); 
		//to show cipher on console 
		StringBuilder vcipherOutput = new StringBuilder();
		for (int i=0; i<(keyword.toCharArray().length);i++) {
			for(int j=0;j<SIZE;j++) {
				vcipherOutput.append(vcipher[i][j]); 
			}
			vcipherOutput.append("\n");
		}
		System.out.println(vcipherOutput);
	}
	
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public char encode(char ch) {
		for(int i=0; i<SIZE; i++) {
			if(ch == alphabet[i]) {
				char returnchar = vcipher[letterFromFileCounter][i]; 
				letterFromFileCounter++;
				if(letterFromFileCounter>=keywordLength) {
					letterFromFileCounter=0;}
				return returnchar;
			}
		}
		return ' ';
	}
	
	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch) {
		for(int i=0; i<SIZE; i++) {
			if(letterFromFileCounter == keywordLength) {
				letterFromFileCounter = 0; 
			}
			if(ch == vcipher[letterFromFileCounter][i]) {
				char returnchar =  alphabet[i];
				letterFromFileCounter++;
				if(letterFromFileCounter>=keywordLength) {
					letterFromFileCounter=0;}
				return returnchar; 
			}	
				}
	    return ' ';  
	}
}
