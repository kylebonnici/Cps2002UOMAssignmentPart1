/**
 * Created by Mark on 01/04/15.
 */
public class Account {

    public int accountNumber;
    public String accountName ;
    public long accountBalance;

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

    public void setAccountBalance(long accountBalance) {
        this.accountBalance = accountBalance;
    }

    public boolean adjustBalance(long ammount) {
        return true;
    }
}
