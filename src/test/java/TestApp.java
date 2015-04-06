import org.junit.Before;
import org.junit.Test;

import javax.security.auth.login.AccountNotFoundException;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by kylebonnici on 31/03/15.
 */


public class TestApp {
    AccountDatabase accDb;
    Account acc;


    @Test
    public void testCreatNewAccount(){
        acc = new Account(0);
        assertEquals(0, acc.getAccountNumber());
    }

    @Test
    public void testAccountName(){
        acc = new Account(0);
        acc.setAccountName("Account0");
        assertEquals("Account0", acc.getAccountName());
    }

    @Test
    public void testAccountBalance(){
        acc = new Account(0);

        long oldAccountBalance = acc.getAccountBalance();

        boolean succ = acc.adjustBalance(2000);

        if (succ) {
            assertEquals(2000, acc.getAccountBalance());
        }else {
            assertEquals(oldAccountBalance, acc.getAccountBalance());
        }
    }

}