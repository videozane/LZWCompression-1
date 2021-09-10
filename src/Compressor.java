import java.util.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
@SuppressWarnings("unused")
public class Compressor {
	
	
	private HashMap<String, Integer> encodeTable;
	private String inputFileName;
	private ArrayList<String>encodedList;
	private ArrayList<String>uncodedList;//temporary to debug
	
	
	public Compressor (String inputFileName) {
		encodeTable = new HashMap<String, Integer> ();
		this.inputFileName = inputFileName;
		encodedList = new ArrayList<String>();
		uncodedList = new ArrayList<String>();
		
		//code below puts in normal chars into the table
		for(int code = 0; code < 256; code++) {
			char newChar = (char)code; //changes the index of ASCII to the core to a character
			String toBeAdded = "" + newChar; //changes the character to a string
			encodeTable.put(toBeAdded, code); //puts string into HashMap with corresponding spot in table
		}
		
	}
	
	
	//converts number to binary and then adds it into encodedList
	public void inputCode (int num) {
		String binaryVersion = Integer.toBinaryString(num);
		StringBuilder bob = new StringBuilder();
		bob.append(binaryVersion);
		while (bob.length()< 12) {
			bob.insert(0, "0");
		}
		encodedList.add(bob.toString());
	}
	//always returns true
	public boolean compress () throws IOException {
		int counter = 256; // table starts off with 256 entries (0-255)
		
		BufferedReader reader = new BufferedReader (new FileReader (new File(inputFileName)));
		String c = Character.toString((char) reader.read()); //current char being looked at
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
		int toggleNum = 0;
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
	
	public void createFile() {
        Path path = Paths.get("doc.txt");
        byte[] bytes = "ABCD".getBytes(StandardCharsets.UTF_8);
 
        try {
            Files.write(path, bytes);    // Java 7+ only
            System.out.println("Successfully written data to the file");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


	
	public static void main (String [] args) throws IOException {
		Compressor smash = new Compressor ("CompressionTest0");
		smash.compress();
	}
}


