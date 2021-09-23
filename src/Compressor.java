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


public class Compressor {
	private HashMap<String, Integer> encodeTable; //dictionary in which to add associations
	private String inputFileName; //file to compress
	
	public Compressor (String inputFileName) throws IOException {
		encodeTable = new HashMap<String, Integer> ();
		this.inputFileName = inputFileName;
		for(int index = 0; index < 256; index++) {
			encodeTable.put(""+(char)index, index); //puts string into HashMap with corresponding spot in table
		}
	}
	
	/**
	 * Made createFile() method obsolete by performing process within compress(). Instead of going back through an ArrayList [O(n)], output to file there instead
	 * Cleaned up variable names and made code look nicer. Added some comments.
	 * @return
	 * @throws IOException
	 */
	public boolean compress () throws IOException {
		int dictSize = 256; // table starts off with 256 entries (0-255)
		int toggleNum = 0; // keeps track if the loop below ends with a combined that's already in dictionary or one without
		FileWriter outputFile=new FileWriter("EncodedOutput.txt");
		BufferedWriter writer = new BufferedWriter(outputFile);
		
		BufferedReader reader = new BufferedReader (new FileReader (new File(inputFileName)));
		String c = Character.toString((char) reader.read()); //current char being looked at
		if (!reader.ready()) {
			return true;
		}
		String n = Character.toString((char) reader.read()); //n is next char
		String combined = c + n;
		if (encodeTable.containsKey(combined)) { //if already exists within dictionary, iterate to next step.
			c = combined;
		}else {
			encodeTable.put(combined, dictSize); //if not, add the string to dictionary, add to table the integer associated with c, then iterate.
			dictSize++;
			writer.write(encodeTable.get(c)+", ");
			c=n;
		}
		
		while (reader.ready()) { //loops while there is something left to be read
			n = Character.toString((char) reader.read());
			combined = c+n;
			if (encodeTable.containsKey(combined)) { //if combined exists, iterate.
				c = combined;
				toggleNum = 1;	
			}else {
				encodeTable.put(combined, dictSize);
				dictSize++;
				writer.write(encodeTable.get(c)+", ");
				c=n;
				toggleNum = 0;
			}
		}
		if (toggleNum == 1) {
			writer.write(encodeTable.get(combined)+", ");
		}else {
			writer.write(encodeTable.get(n)+", ");	
		}
		writer.close();
		return true;
	}

	
	public static void main (String [] args) throws IOException {
		final double startTime = ((double)System.nanoTime())/1000000;
		Compressor smash = new Compressor ("lzw-file3.txt");
		smash.compress();
		Decoder dec = new Decoder("EncodedOutput.txt");
		final double endTime = ((double)System.nanoTime())/1000000;
    	System.out.println("Total execution time: " + (endTime - startTime) + "miliseconds");
	}
}


