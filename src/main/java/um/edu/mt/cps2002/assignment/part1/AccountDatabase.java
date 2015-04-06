package um.edu.mt.cps2002.assignment.part1; /**
 * Created by Mark on 01/04/15.
 */

import java.util.*;

public class AccountDatabase {

    private Map<Integer, Account> database;

    public AccountDatabase(){
        Map<Integer, Account> database = new HashMap<Integer, Account>();
    }

    public Account getAccount(int AccountNumber){
        return database.get(AccountNumber);
    }

    public int getSize() {
        return database.size();
    }


    public boolean createNewAccount( int accountNumber) {
         return  this.createNewAccount(accountNumber, "Account" + accountNumber);
    }

    public boolean createNewAccount( int accountNumber, String name){
        if (getAccount(accountNumber) == null)
            return false;
        else{
            Account newAcc = new Account(accountNumber);
            newAcc.setAccountName(name);
            return true;
        }
    }

}
