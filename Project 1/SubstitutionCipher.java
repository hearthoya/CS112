import java.util.LinkedList;
import java.util.Random;
import java.util.List;

//Links
//https://docs.oracle.com/javase/8/docs/api/java/util/List.html
//https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
//https://www.programiz.com/java-programming/library/math/floor

public class SubstitutionCipher extends Cipher {

    public SubstitutionCipher(long key) {
        super(key);
    }

    public char[] orgTable = new char[256];
    public char[] ranTable = new char[256];

    public void tableFill() {
        //create random object
        Random rand = new Random(getKey());
        //fill org row
        for (int h = 0; h < 256; h++) {
            orgTable[h] = (char) h;
            ranTable[h] = (char) h;
        }
        //fill rand row, using fisher yates
        for (int i = 255; i > 0; i--) {
            //create random char
            char rar = (char) rand.nextInt(i + 1);
            //and temp char
            char temp = ranTable[i];
            //swapping
            ranTable[i] = ranTable[rar];
            ranTable[rar] = temp;
        }
    }


    public List<Character> encrypt(List<Character> cleartext) {

        List<Character> ciphertext = new LinkedList<Character>();

        tableFill();

        //encrypt
        for (char clearChar : cleartext) {
            char cipherChar = ranTable[clearChar];
            //add char to the ciphertext list
            ciphertext.add(cipherChar);
        }
        return ciphertext;
    }

    //decrypt isn't accessing sub table at all
    public List<Character> decrypt(List<Character> ciphertext) {

        List<Character> cleartext = new LinkedList<Character>();

        tableFill();
        //go through every char in cipher text
        for (char cipherChar : ciphertext) {
            //go through all characters in ran table
            for (int j = 0; j < ranTable.length; j++) {
                //if character in ran table is equal to cipherChar
                if (ranTable[j] == cipherChar) {
                    //set clear char equal to
                    char clearChar = (char) j;
                    cleartext.add(clearChar);
                    break;
                }
            }
        }
        return cleartext;
    }

}