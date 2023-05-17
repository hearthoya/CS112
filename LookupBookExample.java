import java.util.Arrays;

public class LookupBookExample {
    public static void main(String[] args) {

        boolean runPhoneBook = false;
        boolean runHistoryBook = false;
        boolean runNumbers = false;
        boolean runStudentSubmission = true;

        if (runPhoneBook) {

            String[] sortedNames = {"Alice", "Bob", "Carol", "David", "Ethan", "Fran", "George"};
            String[] numbers = {"a123", "b238", "c323", "d441", "e123", "f993", "g101"};
            Node<String, String> phoneBook = new Node<String, String>(sortedNames, numbers);
            System.out.println(phoneBook.find("Bob"));
            System.out.println(phoneBook.find("Fran"));
            System.out.println(phoneBook.find("George"));
            System.out.println(phoneBook.find("Scott"));

            System.out.println(phoneBook);
        }

        if (runHistoryBook) {
            Integer[] years = {1999, 2001, 2005, 2011, 2015, 2017, 2020, 2023};
            String[] whatHappened = {
                    "People got scared about y2k",
                    "People forgot about y2k scare",
                    "People wrote 2004 on their checks for months",
                    "The akward what-do-we-call-this-decade had finally ended",
                    "People realized this decade has the same problem as the last",
                    "CS112 was taught by Scott",
                    "Politicians competed over catchy vision-based campaign slogans",
                    "CS112 was taught by Scott AGAIN"};
            Node<Integer, String> whatHappenedIn = new Node<Integer, String>(years, whatHappened);
            System.out.println(whatHappenedIn.find(1999));
            System.out.println(whatHappenedIn.find(2001));
            System.out.println(whatHappenedIn.find(2005));
            System.out.println(whatHappenedIn.find(2011));
            System.out.println(whatHappenedIn.find(2015));
            System.out.println(whatHappenedIn.find(2017));
            System.out.println(whatHappenedIn.find(2020));
            System.out.println(whatHappenedIn);
        }
        if (runNumbers) {
            Integer[] nums = new Integer[100];
            Integer[] otherNums = new Integer[100];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = i;
                otherNums[i] = -i;
            }
            Node<Integer, Integer> numb = new Node<Integer, Integer>(nums, otherNums);
            System.out.println(numb);
        }
        if (runStudentSubmission) {
            //students
            String[] sortedStudents = {"Ania", "Dylan", "Donovan", "Emmett", "George", "Harper", "Joshua", "Peter"};
            //their grades
            Integer[] Grades = {12, 24, 35, 55, 60, 74, 77, 97};
            //node list
            Node<String, Integer> StudentSubmission = new Node<String, Integer>(sortedStudents, Grades);
            //print
            System.out.println(StudentSubmission.find("Ania"));
            System.out.println(StudentSubmission.find("Dylan"));
            System.out.println(StudentSubmission.find("Donovan"));
            System.out.println(StudentSubmission.find("Emmett"));
            System.out.println(StudentSubmission.find("George"));
            System.out.println(StudentSubmission.find("Harper"));
            System.out.println(StudentSubmission.find("Joshua"));
            System.out.println(StudentSubmission.find("Peter"));
            System.out.println(runStudentSubmission);
        }
    }
}


class Node<K extends Comparable<K>, V> {
    Node<K, V> leftChild;
    Node<K, V> rightChild;
    K key;
    V value;
    boolean printTrace = true;

    public Node(K[] keys, V[] values) {

        if (printTrace) {
            System.out.print("Keys:\n\t");
            for (K k : keys) {
                System.out.print(k + " ");
            }
            System.out.print("\n");
            System.out.print("Values:\n\t");
            for (V v : values) {
                System.out.print("(" + v + ") ");
            }
            System.out.println("\n");
        }
        //recursively make tree.
        if (keys.length == 1) {
            this.leftChild = null;
            this.rightChild = null;
            this.value = values[0];
            this.key = keys[0];
        } else if (keys.length == 0) {
            this.value = null;
            this.key = null;
            this.leftChild = null;
            this.rightChild = null;
        } else {
            int splitPoint = keys.length / 2;
            if (printTrace)
                System.out.println("Splitting:" + splitPoint + " (" + keys[splitPoint] + ")");
            this.leftChild = new Node<K, V>(Arrays.copyOfRange(keys, 0, splitPoint),
                    Arrays.copyOfRange(values, 0, splitPoint));
            this.rightChild = new Node<K, V>(Arrays.copyOfRange(keys, splitPoint + 1, keys.length),
                    Arrays.copyOfRange(values, splitPoint + 1, keys.length));
            this.value = values[splitPoint];
            this.key = keys[splitPoint];
        }
    }

    public V find(K key) {
        if (printTrace) {
            System.out.println("My key:" + this.key + " My target:" + key);

        }

        int comparison = this.key.compareTo(key);
        if (comparison == 0) {//they match!
            return this.value;
        } else if (comparison > 0) {
            if (this.leftChild == null) {
                return null;
            }
            return this.leftChild.find(key);
        } else {// (comparison < 0)
            if (this.rightChild == null) {
                return null;
            }
            return this.rightChild.find(key);
        }

    }

    private String asString(int depth) {
        String toR = "";
        String tabs = "";
        for (int i = 0; i < depth; i++) {
            tabs += "\t";
        }
        toR += tabs + "Node:\n";
        toR += tabs + "Key: " + this.key;
        toR += "\n" + tabs + "Value: " + this.value;
        if (this.leftChild != null)
            toR += "\n" + tabs + "Left:\n" + this.leftChild.asString(depth + 1);
        if (this.rightChild != null)
            toR += "\n" + tabs + "Right:\n" + this.rightChild.asString(depth + 1);

        return toR;
    }

    public String toString() {
        return this.asString(0);
    }

}

