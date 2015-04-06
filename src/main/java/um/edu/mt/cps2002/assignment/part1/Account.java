package um.edu.mt.cps2002.assignment.part1;

/**
 * Created by Mark on 01/04/15.
 */
public class Account {

    public int accountNumber;
    public String accountName ;
    public long accountBalance;

    public Account (int accountNumber){
        this.accountNumber = accountNumber;
        accountBalance = 0;
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

    public boolean adjustBalance(long ammount) {

        return true;
    }
}
