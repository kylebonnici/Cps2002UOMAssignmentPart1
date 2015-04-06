package um.edu.mt.cps2002.assignment.part1;

/**
 * Created by Mark on 01/04/15.
 */
public class TransactionManager extends Transaction{

    int numTransactionsProcessed;

    public boolean processTransaction(int src, int dst, long amount){
        sourceAccountNumber = src;
        destinationAccountNumber = dst;
        this.amount = amount;

        return this.process();
    }
}
