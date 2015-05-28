package um.edu.mt.cps2002.assignment.part1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 01/04/15.
 */
public class TransactionManager{

    private Map<Integer,Long> lastTransectionTime;
    private AccountDatabase db;
    private ArrayList<AbstractTransaction> tranList;

    public int getNumTransactionsProcessed() {
        return numTransactionsProcessed;
    }

    public TransactionManager(){
        lastTransectionTime = new HashMap<Integer,Long>();
        db = new AccountDatabase();
        tranList = new ArrayList<AbstractTransaction>();
    }

    private int numTransactionsProcessed;

    public boolean processTransaction(int src, int dst, long amount){
        Account srcAcc = getAccountDatabase().getAccount(src);
        Account dstAcc = getAccountDatabase().getAccount(dst);

        boolean succ = false;

        if (dstAcc != null && srcAcc !=  null) {
            Transaction tran = new Transaction(srcAcc, dstAcc, amount);


            succ = tran.process();

            if (succ) {
                tran.updateTime();
                tranList.add(tran);
                numTransactionsProcessed++;
            }
        }

        return succ;
    }

    public boolean processTransaction(CompoundTransaction compoundTransaction){
        boolean succ = false;

        succ = compoundTransaction.process();

        if (succ) {
            compoundTransaction.updateTime();
            tranList.add(compoundTransaction);
            numTransactionsProcessed++;
        }

        return succ;
    }

    public AccountDatabase getAccountDatabase(){
        return db;
    }

    public CompoundTransaction createNewTypicalCompoundTransaction(Risk risk ,int dstPayDeposit, long depositAmount, int[] dstMainTransaction, long[] mainAmount){
        CompoundTransaction root = new CompoundTransaction();

        return root;
    }
    
}
