
public class MyDS extends OrderedCollection {

    public Iode end = null;

    public void append(int toAppend) {
        Iode toAdd = new Iode(toAppend);
        toAdd.prev = end;
        end = toAdd;
        piDetector();
    }

    public void piDetector() {
        String toReturn = "";
        //pi string
        String pi = "314159";
        //end node
        Iode n = end;
        //while loop to go through list
        while (n != null && toReturn.length() < 6) {
            toReturn = n.s + toReturn;
            n = n.prev;
            //pichecker
            if (toReturn.equals(pi)) {
                System.out.println("Who has pi on their face now, Pr0HaX0r?");
            }
        }

    }


    public int peek() {
        //same as pop but with no delete
        //return 0 if empty
        if (end == null) {
            return 0;
        }
        return end.s;

    }

    public int pop() {
        //return 0 if empty
        if (end == null) {
            return 0;
        }
        //deletes and shows last data piece
        int toReturn = end.s;

        end = end.prev;
        return toReturn;
    }

    public String toString() {
        // to string
        String toReturn = "";
        //end node
        Iode n = end;
        //while loop to go through list
        while (n != null) {
            toReturn = n.s + " " + toReturn;
            n = n.prev;
        }
        return toReturn;
    }

    public int length() {
        //returns length of the string
        int length = 0;
        //create end node
        Iode n = end;
        while (n != null) {
            length++;
            n = n.prev;
        }
        return length;
    }
}

class Iode {
    int s;//int num
    Iode prev;

    public Iode(int s) {
        this.s = s;
    }
}
