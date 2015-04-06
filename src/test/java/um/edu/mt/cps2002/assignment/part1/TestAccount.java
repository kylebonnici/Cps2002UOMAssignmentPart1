package um.edu.mt.cps2002.assignment.part1;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAccount {

    Account acc;


    @Test
    public void testCreatNewAccount(){
        acc = new Account(0);
        Assert.assertEquals(0, acc.getAccountNumber());
    }

    @Test
    public void testAccountName(){
        acc = new Account(0);
        acc.setAccountName("Account0");
        Assert.assertEquals("Account0", acc.getAccountName());
    }

    @Test
    public void testAccountBalance(){
        acc = new Account(0);

        long oldAccountBalance = acc.getAccountBalance();

        boolean succ = acc.adjustBalance(2000);

        if (succ) {
            Assert.assertEquals(2000, acc.getAccountBalance());
        }else {
            Assert.assertEquals(oldAccountBalance, acc.getAccountBalance());
        }
    }
}
