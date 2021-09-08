import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
@SuppressWarnings("unused")
public class Compressor {
	private HashMap<Character, String> assKey;
	private String inputFileName;
	public Compressor (String inputFileName) {
		this.inputFileName = inputFileName;
		for(char code = 0; code < 256; code++) {
			char newChar = (char)code; //changes the number to a character
			String toBeAdded = "" + newChar; //changes the character to a string
			assKey.put(code, toBeAdded); //puts into HashMap
		}
		
	}
	
	public void Compress () throws IOException {
		BufferedReader reader = new BufferedReader (new FileReader (new File(inputFileName)));
		while (reader.ready()) { //loops while there is something left to be read
			
		}
	}
	//asdf;lkajsd;fljasdf

	
	public static void main (String [] args) {
//		Compressor smash = new Compressor ();
//		System.out.println(smash.getTable().get(81));
	}
}


