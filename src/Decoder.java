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
    	
    	
    	
    	
    	int dictionaryIndexOfCurrent = Integer.parseInt(nums[0]);//always stores the 
    	
    	/**
    	 * this is a very strange variable\ because in some cases it acts as strOfNext and in some cases it acts as strOfCurrent
    	 * However, it is always initialized at strOfCurrent even though it's not named that...confusing.
    	 * My guess was this was the person at GeeksforGeek's idea. 
    	 */
    	String strOfNext = dictionary.get(dictionaryIndexOfCurrent);
    	
    	String firstCharOfNext = "" + strOfNext.charAt(0);
    	
    	output.append(strOfNext);
    	
    	//loops through and does the actual algorithm. Followed the GeeksForGeeks pseudocode.
    	for (int i = 1; i < nums.length; i++) {
    		
    		int dictionaryIndexOfNext = Integer.parseInt(nums[i]);
    		
    		if (!dictionary.containsKey(dictionaryIndexOfNext)) {
    			strOfNext+=firstCharOfNext; //next isn't updated so its actually old + firstCharOfOld
    		}
    		
    		else
    			strOfNext = dictionary.get(dictionaryIndexOfNext);
    		
    		output.append(strOfNext);
    		firstCharOfNext = "" + strOfNext.charAt(0);
    		dictionary.put(tableEntry, dictionary.get(dictionaryIndexOfCurrent) + firstCharOfNext);
    		tableEntry++;
    		dictionaryIndexOfCurrent = dictionaryIndexOfNext;
    		
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
    	final double startTime = ((double)System.nanoTime())/1000000;
    	
    	Decoder dec = new Decoder ("EncodedOutput.txt");
    	dec.decode();
    	dec.fileOut();
    	final double endTime = ((double)System.nanoTime())/1000000;
    	System.out.println("Total execution time: " + (endTime - startTime) + "miliseconds");
    	
    }


}
