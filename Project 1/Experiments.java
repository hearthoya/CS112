import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class Experiments {

    public static void description() {
        System.out.println("\n\tFor this experiment, I am examining the randomness of the character mappings created by the Substitution cipher. By running \nthousands of trials (“experiments”) where I create instances of Substitution cipher and then measure the percentage of times \nthe jth character maps to the kth spot in my probability table, I am aiming to see an equal probability of any one character \nbeing randomly mapped to a given location in the table. To properly examine the randomness of our cipher I am using random \nkeys for each generation and creating thousands of generations. By doing this, I ensure the most randomness possible. \nFurthermore, in the actual code of Experiments I am creating Substitution ciphers based on the amount of generations, randomizing \nthe arrays of those generations, looking through those arrays to see where character are mapped and counting the times that \ncharacters maps to the j, kth place and converting that value into a percentage. \n\n\tAfter doing this with various generation amounts ranging from 150 to 1500000 I can conclude that the mapping of characters using \nsubstitution cipher is significantly random. The probability of any one character being mapped to the j, kth place is consistently \naround 0.37 - 0.42 with even less variation at higher generation values. Ideally, I would continue this research using different \nalgorithms, different characters/datasets, and even larger trial amounts to continue examining the randomness of various ciphers \nand Substitution cipher itself.");
    }

    public static void main(String[] args) {
        //create matrix for proportions
        double[][] proportionTable = new double[256][256];
        //create var to store the amount of generations
        int Gens = 150000;
        //create the generations
        for (int i = 0; i < Gens; i++) {
            //create new cipher with random key
            SubstitutionCipher Gen = new SubstitutionCipher((int) (Math.random() * 9999999));
            //populate the list you j made
            Gen.tableFill();
            //loop through table and see where chars are
            for (int j = 0; j < Gen.ranTable.length; j++) {
                proportionTable[j][Gen.ranTable[j]] += 1;
            }
        }
        //turn all vals into percentages
        for (int rows = 0; rows < 256; rows++) {
            for (int cols = 0; cols < 256; cols++) {
                //%
                proportionTable[rows][cols] /= Gens;
                proportionTable[rows][cols] *= 100;
            }
        }
        //print desc in terminal
        description();
        //create Table.txt file
        saveDoubleToFile(proportionTable, "Table.txt");
    }

    public static void saveDoubleToFile(double[][] proportionTable, String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            for (int rows = 0; rows < 255; rows++) {
                for (int cols = 0; cols < 255; cols++) {
                    //ty Soyon
                    writer.write("|" + String.format("%.4g", proportionTable[rows][cols]) + "|");
                }
                writer.write("\n ");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Badness in saveDoubleToFile");
            System.err.println(e);
        }
    }
}
