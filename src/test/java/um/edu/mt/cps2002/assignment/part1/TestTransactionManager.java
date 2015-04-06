package um.edu.mt.cps2002.assignment.part1;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by kylebonnici on 06/04/15.
 */
public class TestTransactionManager {

    TransactionManager tran ;

    @Test
    public void testProcessTransaction() {
        tran = new TransactionManager();

        int qty = 4;
        for (int loops = 1; loops <= qty; loops++) {
            tran.createNewAccount(loops);
            if (loops%2 == 0) tran.getAccount(loops).adjustBalance(1000);
        }


        Assert.assertEquals(false, tran.processTransaction(1, 2, 500)); //fail
        Assert.assertEquals(0, tran.getAccount(1).getAccountBalance());
        Assert.assertEquals(1000, tran.getAccount(2).getAccountBalance());

        Assert.assertEquals(true, tran.processTransaction(2, 1, 500)); // succ 2--> 500 1-->500
        Assert.assertEquals(500, tran.getAccount(1).getAccountBalance());
        Assert.assertEquals(500, tran.getAccount(2).getAccountBalance());

        long time = System.currentTimeMillis();

        while (System.currentTimeMillis() - time <= 14950){
            Assert.assertEquals(false, tran.processTransaction(1, 2, 500)); //fail time
            Assert.assertEquals(500, tran.getAccount(1).getAccountBalance());
            Assert.assertEquals(500, tran.getAccount(2).getAccountBalance());
        }

        while (System.currentTimeMillis() - time <= 15050){

        }

        Assert.assertEquals(true, tran.processTransaction(1, 2, 500)); //succ 2 -->1000 1-->0
        Assert.assertEquals(0, tran.getAccount(1).getAccountBalance());
        Assert.assertEquals(1000, tran.getAccount(2).getAccountBalance());

        time = System.currentTimeMillis();

        Assert.assertEquals(true, tran.processTransaction(4, 3, 500)); //succ 2 -->1000 1-->0
        Assert.assertEquals(500, tran.getAccount(4).getAccountBalance());
        Assert.assertEquals(500, tran.getAccount(3).getAccountBalance());

        while (System.currentTimeMillis() - time <= 14950){
            Assert.assertEquals(false, tran.processTransaction(2, 1, 1001)); //fail time
            Assert.assertEquals(0, tran.getAccount(1).getAccountBalance());
            Assert.assertEquals(1000, tran.getAccount(2).getAccountBalance());
        }

        while (System.currentTimeMillis() - time <= 15050){

        }

        Assert.assertEquals(false, tran.processTransaction(2, 1, 1001)); //fail
        Assert.assertEquals(0, tran.getAccount(1).getAccountBalance());
        Assert.assertEquals(1000, tran.getAccount(2).getAccountBalance());

        Assert.assertEquals(true, tran.processTransaction(2, 1, 100)); //succ 2 --> 900 1-->100
        Assert.assertEquals(100, tran.getAccount(1).getAccountBalance());
        Assert.assertEquals(900, tran.getAccount(2).getAccountBalance());

        time = System.currentTimeMillis();

        while (System.currentTimeMillis() - time <= 14950){
            Assert.assertEquals(false, tran.processTransaction(2, 1, -101)); //fail time
            Assert.assertEquals(100, tran.getAccount(1).getAccountBalance());
            Assert.assertEquals(900, tran.getAccount(2).getAccountBalance());
        }

        while (System.currentTimeMillis() - time <= 15050){

        }

        Assert.assertEquals(false, tran.processTransaction(2, 1, -101)); //fail
        Assert.assertEquals(100, tran.getAccount(1).getAccountBalance());
        Assert.assertEquals(900, tran.getAccount(2).getAccountBalance());

        Assert.assertEquals(true, tran.processTransaction(2, 1, -100)); //succ 2 -->1000 1-->0
        Assert.assertEquals(0, tran.getAccount(1).getAccountBalance());
        Assert.assertEquals(1000, tran.getAccount(2).getAccountBalance());

        time = System.currentTimeMillis();

        while (System.currentTimeMillis() - time <= 14950){
            Assert.assertEquals(false, tran.processTransaction(1, 2, -100)); //fail time
            Assert.assertEquals(0, tran.getAccount(1).getAccountBalance());
            Assert.assertEquals(1000, tran.getAccount(2).getAccountBalance());
        }

        while (System.currentTimeMillis() - time <= 15050){

        }

        Assert.assertEquals(true, tran.processTransaction(1, 2, -100)); //succ 2 -->900 1-->100
        Assert.assertEquals(100, tran.getAccount(1).getAccountBalance());
        Assert.assertEquals(900, tran.getAccount(2).getAccountBalance());

        time = System.currentTimeMillis();

        while (System.currentTimeMillis() - time <= 14950){
            Assert.assertEquals(false, tran.processTransaction(2, 3, 200)); //fail time
            Assert.assertEquals(900, tran.getAccount(2).getAccountBalance());
        }

        while (System.currentTimeMillis() - time <= 15050){

        }

        Assert.assertEquals(false, tran.processTransaction(2, 5, 200)); //fail
        Assert.assertEquals(900, tran.getAccount(2).getAccountBalance());

        Assert.assertEquals(false, tran.processTransaction(1, 5, -200)); //fail
        Assert.assertEquals(900, tran.getAccount(2).getAccountBalance());

    }
}
