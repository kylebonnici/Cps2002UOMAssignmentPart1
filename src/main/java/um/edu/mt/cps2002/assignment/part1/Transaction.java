package um.edu.mt.cps2002.assignment.part1;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 01/04/15.
 */
public class Transaction {

    private int sourceAccountNumber;
    private int destinationAccountNumber;
    private long amount;
    private Account srcAcc;
    private Account dscAcc;

    public Transaction(Account srcAcc, Account dscAcc, long amount){
        this.srcAcc = srcAcc;
        this.dscAcc = dscAcc;
        this.sourceAccountNumber = srcAcc.getAccountNumber();
        this.destinationAccountNumber = dscAcc.getAccountNumber();
        this.amount = amount;
    }

    public boolean process(){
        long srcTime = srcAcc.getLastTimeUsed();
        long dstTime = dscAcc.getLastTimeUsed();

        if (srcTime + 15000 > System.currentTimeMillis() || dstTime + 15000 > System.currentTimeMillis()) {
            return false;
        }else {
            if (srcAcc.adjustBalance(-amount)) {
                if (dscAcc.adjustBalance(amount)) {

                    srcAcc.setLastTimeUsed(System.currentTimeMillis());
                    dscAcc.setLastTimeUsed(System.currentTimeMillis());

                    return true;
                } else {
                    srcAcc.adjustBalance(amount);
                    return false;
                }
            } else {
                return false;
            }
        }

    }
}
