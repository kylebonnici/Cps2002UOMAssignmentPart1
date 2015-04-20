package um.edu.mt.cps2002.assignment.part1;

/**
 * Created by Mark on 01/04/15.
 */
public class Account {

    public int accountNumber;
    public String accountName ;
    public long accountBalance;
    private long lastTimeUsed;

    public Account (int accountNumber, long accountBalance){
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        setLastTimeUsed(System.currentTimeMillis() - 15000);
    }

    public long getLastTimeUsed() {
        return lastTimeUsed;
    }

    public void setLastTimeUsed(long lastTimeUsed) {
        this.lastTimeUsed = lastTimeUsed;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getAccountBalance() {
        return accountBalance;
    }

    public boolean adjustBalance(long amount) {
        if (accountBalance + amount >= 0){
            this.accountBalance += amount;
            return true;
        }else {
            return false;
        }
    }
}
