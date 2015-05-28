package um.edu.mt.cps2002.assignment.part1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by kylebonnici on 28/05/15.
 */
public class TestTransectionManagerTypical {

    TransactionManager tran ;
    private int qty = 10000;
    private long bal = 10000;

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
            tran.getAccountDatabase().createNewAccount(loops, bal);
        }
    }

    private void textTypicalCompoundTransaction(Risk risk,int dstDeposit,long depositAmount, int[] dstMain, long[] mainAmmount ){
        CompoundTransaction cmpTran = tran.createNewTypicalCompoundTransaction(risk, dstDeposit, depositAmount, dstMain, mainAmmount);

        boolean succ = true;
        long commTot = 0;
        double comm = (risk == Risk.HIGH? 0.1: 0.05);

        succ = depositAmount <= bal; // if balance is > tan inisial balce it will fail
        for (int loops = 0; loops < dstMain.length; loops ++){
            succ = succ && (mainAmmount[loops] <= bal);
            commTot += mainAmmount[loops]*comm;
        }

        succ = succ && (commTot <= bal);

        Assert.assertEquals(succ, tran.processTransaction(cmpTran));

        if (succ){
            Assert.assertEquals(bal + depositAmount, tran.getAccountDatabase().getAccount(dstDeposit).getAccountBalance());
            Assert.assertEquals(bal - depositAmount, tran.getAccountDatabase().getAccount(risk == Risk.HIGH ? 3123 : 8665).getAccountBalance());

            long acc1 = 0;
            long acc2 = 0;
            for (int loops = 0; loops < dstMain.length; loops ++){
                long calComm =(long) (mainAmmount[loops]*comm);
                acc1 += mainAmmount[loops];
                acc2 += calComm;

                Assert.assertEquals(bal + mainAmmount[loops], tran.getAccountDatabase().getAccount(dstMain[loops]).getAccountBalance());
            }

            Assert.assertEquals(bal - acc1, tran.getAccountDatabase().getAccount(risk == Risk.HIGH ? 3143 : 3133).getAccountBalance());

            Assert.assertEquals((bal + acc2), tran.getAccountDatabase().getAccount(risk == Risk.HIGH ? 4444 : 4445).getAccountBalance());
            Assert.assertEquals((bal - acc2), tran.getAccountDatabase().getAccount(risk == Risk.HIGH ? 6565 : 6588).getAccountBalance());
        }else {
            Assert.assertEquals(bal, tran.getAccountDatabase().getAccount(dstDeposit).getAccountBalance());
            Assert.assertEquals(bal, tran.getAccountDatabase().getAccount(risk == Risk.HIGH ? 3123 : 8665).getAccountBalance());

            for (int loops = 0; loops < dstMain.length; loops ++){
                Assert.assertEquals(bal, tran.getAccountDatabase().getAccount(dstMain[loops]).getAccountBalance());
                Assert.assertEquals(bal, tran.getAccountDatabase().getAccount(risk == Risk.HIGH ? 3143 : 3133).getAccountBalance());

                Assert.assertEquals(bal, tran.getAccountDatabase().getAccount(risk == Risk.HIGH ? 4444 : 4445).getAccountBalance());
                Assert.assertEquals(bal, tran.getAccountDatabase().getAccount(risk == Risk.HIGH ? 6565 : 6588).getAccountBalance());
            }
        }
    }

    @Test
    public void testTypicalCompoundTransactionRiskHigh1(){
        // succ = true
        int dstDeposit = 1;
        long depositAmount = 100;
        int[] dstMain = { 2 , 3 , 4 };
        long[] mainAmmount = { 200 , 150 , 100 };

        textTypicalCompoundTransaction(Risk.HIGH, dstDeposit, depositAmount, dstMain, mainAmmount);
    }

    @Test
    public void testTypicalCompoundTransactionRiskHigh2(){
        // succ = true
        int dstDeposit = 1;
        long depositAmount = bal;
        int[] dstMain = { 2 , 3 , 4 };
        long[] mainAmmount = { 200 , 150 , 100 };

        textTypicalCompoundTransaction(Risk.HIGH, dstDeposit, depositAmount, dstMain, mainAmmount);
    }

    @Test
    public void testTypicalCompoundTransactionRiskHigh3(){
        // succ = false
        int dstDeposit = 1;
        long depositAmount = bal + 1;
        int[] dstMain = { 2 , 3 , 4 };
        long[] mainAmmount = { 200 , 150 , 100 };

        textTypicalCompoundTransaction(Risk.HIGH, dstDeposit, depositAmount, dstMain, mainAmmount);
    }

    @Test
    public void testTypicalCompoundTransactionRiskHigh4(){
        // succ = false
        int dstDeposit = 1;
        long depositAmount = 100;
        int[] dstMain = { 2 , 3 , 4 };
        long[] mainAmmount = { 5000 , 2000 , 3000 };

        textTypicalCompoundTransaction(Risk.HIGH, dstDeposit, depositAmount, dstMain, mainAmmount);
    }

    @Test
    public void testTypicalCompoundTransactionRiskLow1(){
        int dstDeposit = 1;
        long depositAmount = 100;
        int[] dstMain = { 2 , 3 , 4 };
        long[] mainAmmount = { 200 , 150 , 100 };

        textTypicalCompoundTransaction(Risk.LOW, dstDeposit, depositAmount, dstMain, mainAmmount);
    }

    @Test
    public void testTypicalCompoundTransactionRiskLow2(){
        // succ = true
        int dstDeposit = 1;
        long depositAmount = bal;
        int[] dstMain = { 2 , 3 , 4 };
        long[] mainAmmount = { 200 , 150 , 100 };

        textTypicalCompoundTransaction(Risk.LOW, dstDeposit, depositAmount, dstMain, mainAmmount);
    }

    @Test
    public void testTypicalCompoundTransactionRiskLow3(){
        // succ = false
        int dstDeposit = 1;
        long depositAmount = bal + 1;
        int[] dstMain = { 2 , 3 , 4 };
        long[] mainAmmount = { 200 , 150 , 100 };

        textTypicalCompoundTransaction(Risk.LOW, dstDeposit, depositAmount, dstMain, mainAmmount);
    }

    @Test
    public void testTypicalCompoundTransactionRiskLow4(){
        // succ = false
        int dstDeposit = 1;
        long depositAmount = 100;
        int[] dstMain = { 2 , 3 , 4 };
        long[] mainAmmount = { 5000 , 2000 , 3000 };

        textTypicalCompoundTransaction(Risk.LOW, dstDeposit, depositAmount, dstMain, mainAmmount);
    }

}
