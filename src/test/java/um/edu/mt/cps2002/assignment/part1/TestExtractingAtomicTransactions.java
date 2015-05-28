package um.edu.mt.cps2002.assignment.part1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by kylebonnici on 28/05/15.
 */
public class TestExtractingAtomicTransactions {

    TransactionManager tran ;
    private int qty = 10000;
    private long bal = 10000;

    @Before
    public void createNewTransactionManager(){
        tran = new TransactionManager();
        //Create qty account each even account id hase a balance of 1000;
        for (int loops = 1; loops <= qty; loops++) {
            tran.getAccountDatabase().createNewAccount(loops, bal);
        }
    }

    @Test
    public void testExtractingAtomicTransactionsFilterBySourceAccount1(){
        // succ = true
        int dstDeposit = 1;
        long depositAmount = 100;
        int[] dstMain = { 2 , 3 , 4 };
        long[] mainAmmount = { 200 , 150 , 100 };


        CompoundTransaction cmpTran = tran.createNewTypicalCompoundTransaction(Risk.HIGH, dstDeposit, depositAmount, dstMain, mainAmmount);

        ArrayList<Transaction> filteredTransections = cmpTran.getFilteredTransactions(6565);
        Assert.assertEquals(mainAmmount.length, filteredTransections.size());
    }

    @Test
    public void testExtractingAtomicTransactionsFilterBySourceAccount2(){
        // succ = true
        int dstDeposit = 1;
        long depositAmount = 100;
        int[] dstMain = { 2 , 3 , 4 };
        long[] mainAmmount = { 200 , 150 , 100 };


        CompoundTransaction cmpTran = tran.createNewTypicalCompoundTransaction(Risk.LOW, dstDeposit, depositAmount, dstMain, mainAmmount);

        ArrayList<Transaction> filteredTransections = cmpTran.getFilteredTransactions(6565);
        Assert.assertEquals(0, filteredTransections.size());
    }

    @Test
    public void testExtractingAtomicTransactionsInAscendingOrder1(){
        // succ = true
        int dstDeposit = 1;
        long depositAmount = 100;
        int[] dstMain = { 2 , 3 , 4 };
        long[] mainAmmount = { 200 , 150 , 100 };


        CompoundTransaction cmpTran = tran.createNewTypicalCompoundTransaction(Risk.LOW, dstDeposit, depositAmount, dstMain, mainAmmount);

        ArrayList<Transaction> filteredTransections = cmpTran.getTransactionsInAscending();

        Assert.assertEquals(7, filteredTransections.size());

        Assert.assertEquals((long)(100*0.05), filteredTransections.get(0).getAmount());
        Assert.assertEquals((long)(150*0.05), filteredTransections.get(1).getAmount());
        Assert.assertEquals((long)(200*0.05), filteredTransections.get(2).getAmount());

        Assert.assertEquals(depositAmount, filteredTransections.get(3).getAmount());

        Assert.assertEquals((long)(100), filteredTransections.get(4).getAmount());
        Assert.assertEquals((long)(150), filteredTransections.get(5).getAmount());
        Assert.assertEquals((long)(200), filteredTransections.get(6).getAmount());

    }

    @Test
    public void testExtractingAtomicTransactionsInDescendingOrder1(){
        // succ = true
        int dstDeposit = 1;
        long depositAmount = 100;
        int[] dstMain = { 2 , 3 , 4 };
        long[] mainAmmount = { 200 , 150 , 100 };


        CompoundTransaction cmpTran = tran.createNewTypicalCompoundTransaction(Risk.LOW, dstDeposit, depositAmount, dstMain, mainAmmount);

        ArrayList<Transaction> filteredTransections = cmpTran.getTransactionsInDescending();

        Assert.assertEquals(7, filteredTransections.size());

        Assert.assertEquals((long)(100*0.05), filteredTransections.get(6).getAmount());
        Assert.assertEquals((long)(150*0.05), filteredTransections.get(5).getAmount());
        Assert.assertEquals((long)(200*0.05), filteredTransections.get(4).getAmount());

        Assert.assertEquals(depositAmount, filteredTransections.get(3).getAmount());

        Assert.assertEquals((long)(100), filteredTransections.get(2).getAmount());
        Assert.assertEquals((long)(150), filteredTransections.get(1).getAmount());
        Assert.assertEquals((long)(200), filteredTransections.get(0).getAmount());

    }
}
