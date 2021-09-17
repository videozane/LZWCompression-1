import java.util.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
@SuppressWarnings("unused")


/*
 * USES 12 BITS
 */
public class Compressor {
	
	
	private HashMap<String, Integer> encodeTable;
	private String inputFileName;
	private ArrayList<Integer>encodedList;
	private ArrayList<String>uncodedList;//temporary to debug
	
	
	public Compressor (String inputFileName) {
		encodeTable = new HashMap<String, Integer> ();
		this.inputFileName = inputFileName;
		encodedList = new ArrayList<Integer>();
		uncodedList = new ArrayList<String>();
		
		//code below puts in normal chars into the table
		for(int index = 0; index < 256; index++) {
			char newChar = (char)index; //changes the index of ASCII to a character
			String toBeAdded = "" + newChar; //changes the character to a string
			encodeTable.put(toBeAdded, index); //puts string into HashMap with corresponding spot in table
		}
		
	}
	
	
	/**
	 * converts number to binary (12 bits long) and then adds it into encodedList
	 * @param num is the number to be convertered to binary
	 */
	public void inputCode (int num) {
		// String binaryVersion = Integer.toBinaryString(num);
		// StringBuilder bob = new StringBuilder();
		// bob.append(binaryVersion);
		// while (bob.length()< 12) {
		// 	bob.insert(0, "0");
		// }
		encodedList.add(num);
	}
	/**
	 * 
	 * @return true always. I added this so that the method would end if there is a document that only has one char
	 * @throws IOException
	 */
	public boolean compress () throws IOException {
		int counter = 256; // table starts off with 256 entries (0-255)
		
		BufferedReader reader = new BufferedReader (new FileReader (new File(inputFileName)));
		String c = Character.toString((char) reader.read()); //current char being looked at
		if (!reader.ready()) {
			return true;
		}
		String n = Character.toString((char) reader.read()); //next char
		String combined = c + n;
		if (encodeTable.containsKey (combined)) {
			c = combined;
			
		}
		else {
			encodeTable.put(combined, counter);
			counter++;
			inputCode(encodeTable.get(c));
			uncodedList.add(c);
			c=n;
		}
		
		int toggleNum = 0; // keeps track if the loop below ends with a combined that's already in dictionary or one without
		//because that stuff needs to be added after the loop breaks
		
		while (reader.ready()) { //loops while there is something left to be read
			
			n = Character.toString((char) reader.read());
			combined = c+n;
			if (encodeTable.containsKey (combined)) {
				c = combined;
				toggleNum = 1;
				
			}
			else {
				encodeTable.put(combined, counter);
				counter++;
				uncodedList.add(c);
				inputCode(encodeTable.get(c));
				c=n;
				toggleNum = 0;
			}
		}
		if (toggleNum == 1) {
			uncodedList.add(combined);
			inputCode(encodeTable.get(combined));
		}
		else {
			uncodedList.add(n);
			inputCode(encodeTable.get(n));
		}
		return true;
	}
	/**
	 * creates a binary file using to BinaryOut class
	 */
	public void createFile() throws IOException {
		FileWriter outputFile;
		BufferedWriter writer = null;
		try{
			outputFile = new FileWriter("EncodedOutput.txt");
			writer = new BufferedWriter(outputFile);
			writer.write("yo");
			for (Integer num : encodedList){
				writer.write(num+", ");
			}
			writer.close();
		}
		catch(Exception e)
		{
			
		}
		//Previous verison of method is not compatible with integers. 
        // BinaryOut bitPrinter = new BinaryOut ("Compressed-file.bin");
        // for (int i = 0; i<encodedList.size(); i++) {
        // 	for (int j = 0; j < 12; j ++) {
        // 		if (encodedList.get(i).charAt(j)=='0') {
        // 			bitPrinter.writeBit(false);
        // 		}
        // 		else {
        // 			bitPrinter.writeBit(true);
        // 		}
        // 	}
        // }
        // bitPrinter.close();
    }


	
	public static void main (String [] args) throws IOException {
		Compressor smash = new Compressor ("lzw-file1.txt");
		smash.compress();
		smash.createFile();
	}
}


