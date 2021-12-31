import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.io.*;


public class CS210project {
    public static void main(String[]args) {

        String line;
        BufferedReader in;
        Map<String, String> myHashMap = new HashMap<String, String>(); // myHashMap contains LineKey, Hash
        List<String> keys = new ArrayList<String>();    // keys contains LineKey
        final long startTime = System.currentTimeMillis();
        char [] charlist = new char [2];
        int counteri = 0;

        try{
            in = new BufferedReader(new FileReader("words.txt"));
            line = in.readLine();

            while(line != null) {
                myHashMap.put(line, bytesToHex(shaList(line)));

                keys.add(line);
                line = in.readLine();
                
            }
        
             // IMPORTANT HERE VVVVV

            int similarLetterCounter;   // Temp lettercounter
            int largestSimilarLetterCounter = 0;    // Largest lettercounter
            String similarKeyA = "EMPTY A";    // result1 
            String similarKeyB = "EMPTY B";    // result2

            //Selects hash1
            outerloop:
            for(String i : keys) {
                System.out.println(counteri);
                counteri++;


                //Selects hash2
                for(String p : keys.subList(counteri, keys.size() )) {    // Super Cool Enhanced For Sublist 
                    
                    similarLetterCounter = 0;

                    for(int charcheck = 0; charcheck < 64; charcheck++) {
                        
                        //Scalable charcheck
                        if(63 - charcheck < largestSimilarLetterCounter - similarLetterCounter) {
                            break;
                        }
                        
                        
                        charlist[0] = myHashMap.get(i).charAt(charcheck);
                        charlist[1] = myHashMap.get(p).charAt(charcheck);
                        if(charlist[0] == charlist[1]) {
                            similarLetterCounter++;
                        }
                        
                    }

                    // Then check and check if winner
                    if(similarLetterCounter > largestSimilarLetterCounter) {
                        System.out.println("NEW LARGEST PAIR FOUND = " + similarLetterCounter);
                        largestSimilarLetterCounter = similarLetterCounter;
                        similarKeyA = i;
                        similarKeyB = p;

                        
                    }

                    
                }
                
            }

            if(largestSimilarLetterCounter == 0) {
                System.out.println("NO SIMILAR LETTERS FOUND");
            } else {
                    System.out.println("The Most Similar Pair Has " + largestSimilarLetterCounter + " digits");
                    System.out.println("Hash A " + similarKeyA + " is No: " + keys.indexOf(similarKeyA));
                    System.out.println("Hash B " + similarKeyB + " is No: " + keys.indexOf(similarKeyB));
                    System.out.println();
                    System.out.println(myHashMap.get(similarKeyA));
                    System.out.println(myHashMap.get(similarKeyB));
                    
            }
            System.out.println("poop");
            
        }
        
        catch(Exception e) {
            System.out.println(e);
        }
        //END OF MAIN
        //
    }
    
    public static byte[] shaList(String inputs) {
        
        try{
            MessageDigest digested = MessageDigest.getInstance("SHA-256");
            return digested.digest(inputs.getBytes(StandardCharsets.UTF_8));
        }
        catch(Exception e) {
            return null;
        }
    }
    
    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for(int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
