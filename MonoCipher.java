/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;
	
	/** The cipher array. */
	private char [] cipher;

	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword)
	{
		//create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
		// create first part of cipher from keyword
		// create remainder of cipher from the remaining characters of the alphabet
		// print cipher array for testing and tutors
		cipher = new char [SIZE];
		int cipherContentCount = 0; 
		for (int i=0; i< (keyword.toCharArray().length) ; i++) {
			cipher[i] = keyword.toCharArray()[i]; 
			cipherContentCount++; 
		}
		
		 //adding remaining characters of alphabet
		int k = 0;
		for (int i=0; i<SIZE; i++) {
			boolean letterInKeyword = false; 
			for (int j=0;j<=cipherContentCount;j++) {
				if ((char)('A'+(25-i)) == cipher[j]) {
					letterInKeyword = true; 
				}
			} 
			if (!letterInKeyword) {
			cipher[cipherContentCount+k] = (char) ('A'+(25-i));
			k++;
			}
		}		
		System.out.println(cipher);
	}
	
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{
		for(int i=0; i<SIZE; i++) {
			if(ch == alphabet[i]) {
						return cipher[i]; 
				}
			}
		
	    return ' ';
	}

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{
		for(int i=0; i<SIZE; i++) {
			if(ch == cipher[i]) {
						return alphabet[i]; 
			}
		}
	    return ' '; 
	}
}
