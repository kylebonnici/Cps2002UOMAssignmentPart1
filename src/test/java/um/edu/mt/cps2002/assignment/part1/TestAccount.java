package um.edu.mt.cps2002.assignment.part1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.*;

public class TestAccount {

    Account acc;
    int accNo;

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

    @Before
    public void createNewInstance(){
        accNo =  this.genRandInt();
        acc = new Account(accNo, 0);
    }

    @Test
    public void testCreateNewAccount(){
        Assert.assertEquals(accNo, acc.getAccountNumber());
    }

    @Test
    public void testAccountName(){
        acc.setAccountName("Account" +accNo );
        Assert.assertEquals("Account" +accNo, acc.getAccountName());
    }

    @Test
    public void testNewAccountBalance1(){
        Assert.assertEquals(0, acc.getAccountBalance());
    }

    @Test
    public void testNewAccountBalance2(){
        boolean succ = acc.adjustBalance(200);

        Assert.assertEquals(true, succ);
        Assert.assertEquals(200, acc.getAccountBalance());
    }

    @Test
    public void testOverWithdraw1(){
        acc.adjustBalance(1000); // set acc to 1000

        boolean succ = acc.adjustBalance(-1001);

        Assert.assertEquals(false, succ);
        Assert.assertEquals(1000, acc.getAccountBalance());
    }

    @Test
    public void testOverWithdraw2(){
        acc.adjustBalance(1000); // set acc to 1000

        boolean succ = acc.adjustBalance(-2000);

        Assert.assertEquals(false, succ);
        Assert.assertEquals(1000, acc.getAccountBalance());
    }

    @Test
    public void testUnderWithdraw1(){
        acc.adjustBalance(1000); // set acc to 1000

        boolean succ = acc.adjustBalance(-1000);

        Assert.assertEquals(true, succ);
        Assert.assertEquals(0, acc.getAccountBalance());
    }

    @Test
    public void testUnderWithdraw2(){
        acc.adjustBalance(1000); // set acc to 1000

        boolean succ = acc.adjustBalance(-999);

        Assert.assertEquals(true, succ);
        Assert.assertEquals(1,acc.getAccountBalance());
    }
}
