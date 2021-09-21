import java.util.*;
import java.io.*;

public class Decoder {

    private BufferedReader in;
    private int tableEntry = 256;
    private HashMap <Integer, String> dictionary;
    private StringBuilder output;


    /**
     * This is the constructor :p
     * @param filename
     * @throws IOException
     */
    public Decoder (String filename) throws IOException{
    	
    	
        //for file reading
        in = new BufferedReader (new FileReader(new File (filename)));
        
        dictionary = new HashMap <Integer, String>();
        output = new StringBuilder ();
        
        output.append("");
        
        //inputting all of the dictionary values of each individual character.
        for (int i = 0; i < 256; i++) {
        	
            dictionary.put(i, "" + (char) i);
            
        }

    }

    
    /**
     * decodes the encoded file that contains encoded integer values for characters separated by ", " (a comma followed by a space).
     * @throws IOException
     */
    public void decode () throws IOException {
    	
    	
    	//Input of encoded numbers.
    	String cur = in.readLine();
    	
    	//puts each encoded number as a String into an array of Strings called nums.
    	//look I made this easy for you. Turn this array nums into an ArrayList to make it run a lot faster.
    	String [] nums = cur.split(", ", 0);
    	
    	int old = Integer.parseInt(nums[0]);
    	String s = dictionary.get(old);
    	String c = "" + s.charAt(0);
    	
    	output.append(s);
    	
    	//loops through and does the actual algorithm. Followed the GeeksForGeeks pseudocode.
    	for (int i = 1; i < nums.length; i++) {
    		
    		int next = Integer.parseInt(nums[i]);
    		
    		if (!dictionary.containsKey(next)) {
    			s = dictionary.get(old);
    			s = s+c;
    		}
    		
    		else
    			s = dictionary.get(next);
    		
    		output.append(s);
    		c = "" + s.charAt(0);
    		dictionary.put(tableEntry, dictionary.get(old) + c);
    		tableEntry++;
    		old = next;
    		
    	}
    }

    //outputs a file
    public void fileOut () throws Exception, IOException {
    	FileWriter outputFile;
		BufferedWriter writer = null;
		if (output.equals("")) {
			throw new Exception ("ayo you didn't decode anything cuh");
		}
		try{
			outputFile = new FileWriter("DecodedOutput.txt");
			writer = new BufferedWriter(outputFile);
			writer.write(output.toString());
			writer.close();
		}
		catch(Exception e)
		{
			
		}
    }
    
    public static void main (String [] args) throws Exception, IOException {
    	Decoder dec = new Decoder ("EncodedOutput.txt");
    	dec.decode();
    	dec.fileOut();
    	
    }


}
