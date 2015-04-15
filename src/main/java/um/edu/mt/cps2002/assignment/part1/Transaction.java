package um.edu.mt.cps2002.assignment.part1;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 01/04/15.
 */
public class Transaction {

    public int sourceAccountNumber;
    public int destinationAccountNumber;
    public long amount;
    private Map<Integer,Long> lastTransectionTime;
    private AccountDatabase db;

    public Transaction(){
        lastTransectionTime = new HashMap<Integer,Long>();
        db = new AccountDatabase();
    }

    public boolean process(){
        Account accSrc = this.getAccountDatabase().getAccount(sourceAccountNumber);
        Account accDst = this.getAccountDatabase().getAccount(destinationAccountNumber);

        if (accSrc != null && accDst != null) {

            Long srcTime = lastTransectionTime.get(new Integer(accSrc.getAccountNumber()));
            Long dstTime = lastTransectionTime.get(new Integer(accDst.getAccountNumber()));

            if (srcTime != null && srcTime.longValue() + 15000 > System.currentTimeMillis() || dstTime != null && dstTime.longValue() + 15000 > System.currentTimeMillis()) {
                return false;
            }else {
                if (accSrc.adjustBalance(-amount)) {
                    if (accDst.adjustBalance(amount)) {

                        lastTransectionTime.put(new Integer(accSrc.getAccountNumber()), new Long(System.currentTimeMillis()));
                        lastTransectionTime.put(new Integer(accDst.getAccountNumber()), new Long(System.currentTimeMillis()));

                        return true;
                    } else {
                        accSrc.adjustBalance(amount);
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }else{
            return false;
        }
    }

    public AccountDatabase getAccountDatabase(){
        return db;
    }
}
