package um.edu.mt.cps2002.assignment.part1; /**
 * Created by Mark on 01/04/15.
 */

import java.util.*;

public class AccountDatabase {

    private Map<Integer, Account> database;

    public AccountDatabase(){
        database = new HashMap<Integer, Account>();
    }

    public Account getAccount(int accountNumber){
        return database.get(new Integer(accountNumber));
    }

    public int getSize() {
        return database.size();
    }


    public boolean createNewAccount(int accountNumber, long accountBalance) {
         return  this.createNewAccount(accountNumber, "Account" + accountNumber, accountBalance);
    }

    public boolean createNewAccount( int accountNumber, String name, long accountBalance){
        if (getAccount(accountNumber) != null)
            return false;
        else{
            Account newAcc = new Account(accountNumber, accountBalance);
            newAcc.setAccountName(name);
            database.put(new Integer(accountNumber), newAcc);
            return true;
        }
    }

}
