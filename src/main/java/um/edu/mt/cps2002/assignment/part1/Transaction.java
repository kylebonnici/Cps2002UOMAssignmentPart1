package um.edu.mt.cps2002.assignment.part1;

/**
 * Created by Mark on 01/04/15.
 */
public class Transaction extends AccountDatabase {

    public int sourceAccountNumber;
    public int destinationAccountNumber;
    public long amount;

    public boolean process(){
        Account accSrc = this.getAccount(sourceAccountNumber);
        Account accDst = this.getAccount(destinationAccountNumber);

        //bla bla bla


        //if all all ajst balnce
        //return true

        //else return false

        return true;
    }
}
