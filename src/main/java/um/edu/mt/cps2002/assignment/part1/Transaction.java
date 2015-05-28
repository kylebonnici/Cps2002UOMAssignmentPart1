package um.edu.mt.cps2002.assignment.part1;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 01/04/15.
 */
public class Transaction extends AbstractTransaction {

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
        if (check()) {
            srcAcc.adjustBalance(-amount);
            dscAcc.adjustBalance(amount);
            return true;
        }

        return false;
    }

    public boolean check(){
        if (!checkFailOnTime() && !checkFailOnSrc() && !checkFailOnDst()) {
            return true;
        }

        return false;

    }

    public boolean checkFailOnTime(){ //true ok false fail
        long srcTime = srcAcc.getLastTimeUsed();
        long dstTime = dscAcc.getLastTimeUsed();

        return (srcTime + 15000 > System.currentTimeMillis() || dstTime + 15000 > System.currentTimeMillis());
    }

    private boolean checkFailOnSrc(){
        return !srcAcc.checkAdjustBalance(-amount);
    }

    private boolean checkFailOnDst(){
        return !dscAcc.checkAdjustBalance(amount);
    }

    public void updateTime(){
        srcAcc.setLastTimeUsed(System.currentTimeMillis());
        dscAcc.setLastTimeUsed(System.currentTimeMillis());
    }

    public void rollback() {
        srcAcc.adjustBalance(amount);
        dscAcc.adjustBalance(-amount);
    }

    public int getSourceAccountNumber(){
        return sourceAccountNumber;
    }

    public int getDestinationAccountNumber(){
        return destinationAccountNumber;
    }

    public long getAmount(){
        return amount;
    }
}
