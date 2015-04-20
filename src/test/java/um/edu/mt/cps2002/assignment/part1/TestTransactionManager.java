package um.edu.mt.cps2002.assignment.part1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by kylebonnici on 06/04/15.
 */
public class TestTransactionManager {

    TransactionManager tran ;
    private int qty = 1000;

    private void wait15Sec(){
        try {
            Thread.sleep(15001);
        }catch(java.lang.InterruptedException e){

        }
    }

    @Before
    public void createNewTransactionManager(){
        tran = new TransactionManager();
        //Create qty account each even account id hase a balance of 1000;
        for (int loops = 1; loops <= qty; loops++) {
            long bal = 0;
            if (loops%2 == 0) bal =  1000;
            tran.getAccountDatabase().createNewAccount(loops, bal);
        }
    }

    @Test
    public void testNumberOfTansactionsProcessed(){
        //only half should be succsesfull thus no of transections should be qty/2
        for (int loops = 1 ; loops <= 1000; loops ++){
            tran.processTransaction(loops + 1, loops, 100);
        }

        Assert.assertEquals(qty/2, tran.getNumTransactionsProcessed());
    }

    @Test
    public void testTransferBetweenTwoAccounts1(){
        //Transfer money from acc 2 to acc 1 ballance in accounts should be 700 300 respectivly
        Assert.assertEquals(true, tran.processTransaction(2, 1, 300));
        Assert.assertEquals(300, tran.getAccountDatabase().getAccount(1).getAccountBalance());
        Assert.assertEquals(700, tran.getAccountDatabase().getAccount(2).getAccountBalance());
    }

    @Test
    public void testTransferBetweenTwoAccounts2(){
        Assert.assertEquals(false, tran.processTransaction(2, 1, 3000));
        Assert.assertEquals(0, tran.getAccountDatabase().getAccount(1).getAccountBalance());
        Assert.assertEquals(1000, tran.getAccountDatabase().getAccount(2).getAccountBalance());
    }

    @Test
    public void testTransferBetween1UnkownAccounts1(){
        Assert.assertEquals(false, tran.processTransaction(2, qty + 1, 300));
        Assert.assertEquals(1000, tran.getAccountDatabase().getAccount(2).getAccountBalance());
    }

    @Test
    public void testTransferBetween1UnkownAccounts2(){
        Assert.assertEquals(false, tran.processTransaction(qty + 1, 2, 300));
        Assert.assertEquals(1000, tran.getAccountDatabase().getAccount(2).getAccountBalance());
    }

    @Test
    public void testTransferBetween2UnkownAccounts(){
        Assert.assertEquals(false, tran.processTransaction(qty + 2, qty + 1, 300));
    }

    @Test
    public void testTransferBetweenSameAccounts(){
        Assert.assertEquals(true, tran.processTransaction(2, 2, 300));
        Assert.assertEquals(1000, tran.getAccountDatabase().getAccount(2).getAccountBalance());
    }

    @Test
    public void testTransferBetweenTwoAccountsInv1(){
        Assert.assertEquals(true, tran.processTransaction(1, 2, -300));
        Assert.assertEquals(300, tran.getAccountDatabase().getAccount(1).getAccountBalance());
        Assert.assertEquals(700, tran.getAccountDatabase().getAccount(2).getAccountBalance());
    }

    @Test
    public void testTransferBetweenTwoAccountsInv2(){
        Assert.assertEquals(false, tran.processTransaction(1, 2, -3000));
        Assert.assertEquals(0, tran.getAccountDatabase().getAccount(1).getAccountBalance());
        Assert.assertEquals(1000, tran.getAccountDatabase().getAccount(2).getAccountBalance());
    }

    @Test
    public void testTransferBetweenTwoAccountsInLessThen15Sec1(){
        Assert.assertEquals(true, tran.processTransaction(2, 1, 300));
        Assert.assertEquals(300, tran.getAccountDatabase().getAccount(1).getAccountBalance());
        Assert.assertEquals(700, tran.getAccountDatabase().getAccount(2).getAccountBalance());

        Assert.assertEquals(false, tran.processTransaction(1, 2, 300));
        Assert.assertEquals(300, tran.getAccountDatabase().getAccount(1).getAccountBalance());
        Assert.assertEquals(700, tran.getAccountDatabase().getAccount(2).getAccountBalance());
    }

    @Test
    public void testTransferBetweenTwoAccountsInLessThen15Sec2(){
        Assert.assertEquals(true, tran.processTransaction(2, 1, 300));
        Assert.assertEquals(300, tran.getAccountDatabase().getAccount(1).getAccountBalance());
        Assert.assertEquals(700, tran.getAccountDatabase().getAccount(2).getAccountBalance());

        Assert.assertEquals(false, tran.processTransaction(2, 3, 300));
        Assert.assertEquals(0, tran.getAccountDatabase().getAccount(3).getAccountBalance());
        Assert.assertEquals(700, tran.getAccountDatabase().getAccount(2).getAccountBalance());
    }

    @Test
    public void testTransferBetweenTwoAccountsInLessThen15Sec3(){
        Assert.assertEquals(true, tran.processTransaction(2, 1, 300));
        Assert.assertEquals(300, tran.getAccountDatabase().getAccount(1).getAccountBalance());
        Assert.assertEquals(700, tran.getAccountDatabase().getAccount(2).getAccountBalance());

        Assert.assertEquals(false, tran.processTransaction(1, 3, 300));
        Assert.assertEquals(0, tran.getAccountDatabase().getAccount(3).getAccountBalance());
        Assert.assertEquals(300, tran.getAccountDatabase().getAccount(1).getAccountBalance());
    }

    @Test
    public void testTransferBetweenTwoAccountsAfter15Sec1(){
        Assert.assertEquals(true, tran.processTransaction(2, 1, 300));
        Assert.assertEquals(300, tran.getAccountDatabase().getAccount(1).getAccountBalance());
        Assert.assertEquals(700, tran.getAccountDatabase().getAccount(2).getAccountBalance());

        wait15Sec();

        Assert.assertEquals(true, tran.processTransaction(1, 2, 300));
        Assert.assertEquals(0, tran.getAccountDatabase().getAccount(1).getAccountBalance());
        Assert.assertEquals(1000, tran.getAccountDatabase().getAccount(2).getAccountBalance());
    }

    @Test
    public void testTransferBetweenTwoAccountsAfter15Sec2(){
        Assert.assertEquals(true, tran.processTransaction(2, 1, 300));
        Assert.assertEquals(300, tran.getAccountDatabase().getAccount(1).getAccountBalance());
        Assert.assertEquals(700, tran.getAccountDatabase().getAccount(2).getAccountBalance());

        wait15Sec();

        Assert.assertEquals(true, tran.processTransaction(2, 3, 300));
        Assert.assertEquals(300, tran.getAccountDatabase().getAccount(3).getAccountBalance());
        Assert.assertEquals(400, tran.getAccountDatabase().getAccount(2).getAccountBalance());
    }

    @Test
    public void testTransferBetweenTwoAccountsAfter15Sec3(){
        Assert.assertEquals(true, tran.processTransaction(2, 1, 300));
        Assert.assertEquals(300, tran.getAccountDatabase().getAccount(1).getAccountBalance());
        Assert.assertEquals(700, tran.getAccountDatabase().getAccount(2).getAccountBalance());

        wait15Sec();

        Assert.assertEquals(true, tran.processTransaction(1, 3, 300));
        Assert.assertEquals(300, tran.getAccountDatabase().getAccount(3).getAccountBalance());
        Assert.assertEquals(0, tran.getAccountDatabase().getAccount(1).getAccountBalance());
    }
}
