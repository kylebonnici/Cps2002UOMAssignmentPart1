package um.edu.mt.cps2002.assignment.part1; /**
 * Created by Mark on 01/04/15.
 */

import java.util.*;

public class AccountDatabase {

    private ArrayList<Account> database;

    public AccountDatabase(){
        ArrayList<Account> database = new ArrayList<Account>();
    }

    public Account getAccount(int AccountNumber){
        Account acc = new Account(0);

        return acc;
    }
    public int getSize(){
        return 0;
    }

    public boolean createNewAccount( int accountNumber){
        return true;
    }

}
