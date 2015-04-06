package um.edu.mt.cps2002.assignment.part1;

import org.junit.Assert;
import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.*;

public class TestAccount {

    Account acc;

    private int genRandInt(){
        Random rn = new Random();
        int n = 2147483647;
        int i = rn.nextInt() % n;
        return  1 + i;
    }

    private long genRandLong(int max,int min){
        Random rn = new Random();
        int n = max - min + 1;
        long i = rn.nextLong() % n;
        return  min + i;
    }


    @Test
    public void testCreatNewAccount(){
        int accNo =  this.genRandInt();

        acc = new Account(accNo);
        Assert.assertEquals(accNo, acc.getAccountNumber());
    }

    @Test
    public void testAccountName(){
        acc = new Account(genRandInt());
        int randNo = genRandInt();
        acc.setAccountName("Account" +randNo );
        Assert.assertEquals("Account" +randNo, acc.getAccountName());
    }

    @Test
    public void testAccountBalance(){
        acc = new Account(genRandInt());

        for (int loops = 0 ; loops < 10000; loops ++) {
            long oldAccountBalance = acc.getAccountBalance();

            long amount = this.genRandLong(-1000000, 100000);

            long newAccountBalance = oldAccountBalance + amount;

            boolean succ = acc.adjustBalance(amount);

            if (succ) {
                Assert.assertEquals(oldAccountBalance + amount, acc.getAccountBalance());
            } else {
                Assert.assertEquals(oldAccountBalance, acc.getAccountBalance());
            }
        }
    }
}
