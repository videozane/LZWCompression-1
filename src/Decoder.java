import java.util.*;
import java.io.*;

public class Decoder {
    
/**
 * wtf is this 
 * 
 * PSEUDOCODE
1    Initialize table with single character strings
2    OLD = first input code
3    output translation of OLD
4    WHILE not end of input stream
5        NEW = next input code
6        IF NEW is not in the string table
7               S = translation of OLD
8               S = S + C
9       ELSE
10              S = translation of NEW
11       output S
12       C = first character of S
13       OLD + C to the string table
14       OLD = NEW
15   END WHILE
 */


    //IM SORRY CHRIS!!!!!

    private BufferedReader in;
    private int tableEntry = 256;
    private HashMap <Integer, String> dictionary;


    public Decoder (String filename) throws IOException{
        //for file reading
        in = new BufferedReader (new FileReader(new File (filename)));
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, "" + (char) i);
        }

    }

    //decodes
    public void Decode () throws IOException {

        while (true) {

            String cur = "" + in.read();
            
        }
    }

    //outputs a file
    public void fileOut () {

    }


}
