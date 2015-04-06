package um.edu.mt.cps2002.assignment.part1;

/**
 * Created by Mark on 01/04/15.
 */
public class Transaction {

    public int sourceAccountNumber;
    public int destinationAccountNumber;
    public long amount;

    public boolean process(){
        return true;
    }
}
