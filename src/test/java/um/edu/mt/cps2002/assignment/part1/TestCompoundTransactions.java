package um.edu.mt.cps2002.assignment.part1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by kylebonnici on 12/05/15.
 */

public class TestCompoundTransactions {

    TransactionManager tran ;
    int startAmount = 1000;

    private void wait15Sec(){
        try {
            Thread.sleep(15001);
        }catch(java.lang.InterruptedException e){

        }
    }

    @Before
    public void createNewTransactionManager(){
        tran = new TransactionManager();
        tran.getAccountDatabase().createNewAccount(1234, startAmount);
        tran.getAccountDatabase().createNewAccount(4567, startAmount);
        tran.getAccountDatabase().createNewAccount(6781, startAmount);
        tran.getAccountDatabase().createNewAccount(1111, startAmount);
        tran.getAccountDatabase().createNewAccount(2321, startAmount);
        tran.getAccountDatabase().createNewAccount(2212, startAmount);
    }


    @Test
    public void testValidCompoundTransaction1(){
        int amount = 200;
        int startAmount = 1000;

        CompoundTransaction ctBuyProperty = new CompoundTransaction();
        CompoundTransaction ctPrepareLoan = new CompoundTransaction();
        CompoundTransaction ctPayFees = new CompoundTransaction();

        ctBuyProperty.addChildTransaction(ctPrepareLoan);
        ctBuyProperty.addChildTransaction(ctPayFees);

        Transaction trAllLoanFunds = new Transaction(tran.getAccountDatabase().getAccount(1234),tran.getAccountDatabase().getAccount(4567),amount);
        Transaction trTranToSeller = new Transaction(tran.getAccountDatabase().getAccount(4567),tran.getAccountDatabase().getAccount(6781),amount);

        ctPrepareLoan.addChildTransaction(trAllLoanFunds);
        ctPrepareLoan.addChildTransaction(trTranToSeller);

        Transaction trPayLegalFees = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2321),(long)(amount*0.18));
        Transaction trPayTaxes = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2212),(long)(amount*0.18));

        ctPayFees.addChildTransaction(trPayLegalFees);
        ctPayFees.addChildTransaction(trPayTaxes);

        Assert.assertEquals(true, tran.processTransaction(ctBuyProperty));

        Assert.assertEquals(startAmount - amount, tran.getAccountDatabase().getAccount(1234).getAccountBalance());
        Assert.assertEquals(startAmount + amount, tran.getAccountDatabase().getAccount(6781).getAccountBalance());
        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(4567).getAccountBalance());

        Assert.assertEquals(startAmount+(long)(amount*0.18), tran.getAccountDatabase().getAccount(2321).getAccountBalance());
        Assert.assertEquals(startAmount-(2*(long)(amount*0.18)), tran.getAccountDatabase().getAccount(1111).getAccountBalance());
        Assert.assertEquals(startAmount+(long)(amount*0.18), tran.getAccountDatabase().getAccount(2212).getAccountBalance());
    }

    @Test
    public void testValidCompoundTransaction2(){
        int amount = 200;

        //empty account 4567
        tran.getAccountDatabase().getAccount(4567).adjustBalance(-1000);

        CompoundTransaction ctBuyProperty = new CompoundTransaction();
        CompoundTransaction ctPrepareLoan = new CompoundTransaction();
        CompoundTransaction ctPayFees = new CompoundTransaction();

        ctBuyProperty.addChildTransaction(ctPrepareLoan);
        ctBuyProperty.addChildTransaction(ctPayFees);

        Transaction trAllLoanFunds = new Transaction(tran.getAccountDatabase().getAccount(1234),tran.getAccountDatabase().getAccount(4567),amount);
        Transaction trTranToSeller = new Transaction(tran.getAccountDatabase().getAccount(4567),tran.getAccountDatabase().getAccount(6781),amount);

        ctPrepareLoan.addChildTransaction(trAllLoanFunds);
        ctPrepareLoan.addChildTransaction(trTranToSeller);

        Transaction trPayLegalFees = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2321),(long)(amount*0.18));
        Transaction trPayTaxes = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2212),(long)(amount*0.18));

        ctPayFees.addChildTransaction(trPayLegalFees);
        ctPayFees.addChildTransaction(trPayTaxes);

        Assert.assertEquals(true, tran.processTransaction(ctBuyProperty));

        Assert.assertEquals(startAmount - amount, tran.getAccountDatabase().getAccount(1234).getAccountBalance());
        Assert.assertEquals(startAmount + amount, tran.getAccountDatabase().getAccount(6781).getAccountBalance());
        Assert.assertEquals(0, tran.getAccountDatabase().getAccount(4567).getAccountBalance());

        Assert.assertEquals(startAmount+(long)(amount*0.18), tran.getAccountDatabase().getAccount(2321).getAccountBalance());
        Assert.assertEquals(startAmount-(2*(long)(amount*0.18)), tran.getAccountDatabase().getAccount(1111).getAccountBalance());
        Assert.assertEquals(startAmount+(long)(amount*0.18), tran.getAccountDatabase().getAccount(2212).getAccountBalance());
    }


    @Test
    public void testInvalidCompoundTransaction1NoRollback(){
        int amount = 2000;

        CompoundTransaction ctBuyProperty = new CompoundTransaction();
        CompoundTransaction ctPrepareLoan = new CompoundTransaction();
        CompoundTransaction ctPayFees = new CompoundTransaction();

        ctBuyProperty.addChildTransaction(ctPrepareLoan);
        ctBuyProperty.addChildTransaction(ctPayFees);

        Transaction trAllLoanFunds = new Transaction(tran.getAccountDatabase().getAccount(1234),tran.getAccountDatabase().getAccount(4567),amount);
        Transaction trTranToSeller = new Transaction(tran.getAccountDatabase().getAccount(4567),tran.getAccountDatabase().getAccount(6781),amount);

        ctPrepareLoan.addChildTransaction(trAllLoanFunds);
        ctPrepareLoan.addChildTransaction(trTranToSeller);

        Transaction trPayLegalFees = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2321),(long)(amount*0.18));
        Transaction trPayTaxes = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2212),(long)(amount*0.18));

        ctPayFees.addChildTransaction(trPayLegalFees);
        ctPayFees.addChildTransaction(trPayTaxes);

        Assert.assertEquals(false, tran.processTransaction(ctBuyProperty));

        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(1234).getAccountBalance());
        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(6781).getAccountBalance());
        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(4567).getAccountBalance());

        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(2321).getAccountBalance());
        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(1111).getAccountBalance());
        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(2212).getAccountBalance());
    }

    @Test
    public void testInvalidCompoundTransaction1WithRollback(){
        int amount = 200;

        //empty account 1234
        tran.getAccountDatabase().getAccount(1234).adjustBalance(-1000);

        CompoundTransaction ctBuyProperty = new CompoundTransaction();
        CompoundTransaction ctPrepareLoan = new CompoundTransaction();
        CompoundTransaction ctPayFees = new CompoundTransaction();

        ctBuyProperty.addChildTransaction(ctPrepareLoan);
        ctBuyProperty.addChildTransaction(ctPayFees);

        Transaction trAllLoanFunds = new Transaction(tran.getAccountDatabase().getAccount(1234),tran.getAccountDatabase().getAccount(4567),amount);
        Transaction trTranToSeller = new Transaction(tran.getAccountDatabase().getAccount(4567),tran.getAccountDatabase().getAccount(6781),amount);

        ctPrepareLoan.addChildTransaction(trAllLoanFunds);
        ctPrepareLoan.addChildTransaction(trTranToSeller);

        Transaction trPayLegalFees = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2321),(long)(amount*0.18));
        Transaction trPayTaxes = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2212),(long)(amount*0.18));

        ctPayFees.addChildTransaction(trPayLegalFees);
        ctPayFees.addChildTransaction(trPayTaxes);

        Assert.assertEquals(false, tran.processTransaction(ctBuyProperty));

        Assert.assertEquals(0, tran.getAccountDatabase().getAccount(1234).getAccountBalance());
        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(6781).getAccountBalance());
        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(4567).getAccountBalance());

        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(2321).getAccountBalance());
        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(1111).getAccountBalance());
        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(2212).getAccountBalance());
    }


    @Test
    public void testValidCompoundTransactionTwiceTimeError1(){
        int amount = 200;
        int startAmount = 1000;

        CompoundTransaction ctBuyProperty = new CompoundTransaction();
        CompoundTransaction ctPrepareLoan = new CompoundTransaction();
        CompoundTransaction ctPayFees = new CompoundTransaction();

        ctBuyProperty.addChildTransaction(ctPrepareLoan);
        ctBuyProperty.addChildTransaction(ctPayFees);

        Transaction trAllLoanFunds = new Transaction(tran.getAccountDatabase().getAccount(1234),tran.getAccountDatabase().getAccount(4567),amount);
        Transaction trTranToSeller = new Transaction(tran.getAccountDatabase().getAccount(4567),tran.getAccountDatabase().getAccount(6781),amount);

        ctPrepareLoan.addChildTransaction(trAllLoanFunds);
        ctPrepareLoan.addChildTransaction(trTranToSeller);

        Transaction trPayLegalFees = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2321),(long)(amount*0.18));
        Transaction trPayTaxes = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2212),(long)(amount*0.18));

        ctPayFees.addChildTransaction(trPayLegalFees);
        ctPayFees.addChildTransaction(trPayTaxes);


        Assert.assertEquals(true, tran.processTransaction(ctBuyProperty));

        Assert.assertEquals(startAmount - amount, tran.getAccountDatabase().getAccount(1234).getAccountBalance());
        Assert.assertEquals(startAmount + amount, tran.getAccountDatabase().getAccount(6781).getAccountBalance());
        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(4567).getAccountBalance());

        Assert.assertEquals(startAmount+(long)(amount*0.18), tran.getAccountDatabase().getAccount(2321).getAccountBalance());
        Assert.assertEquals(startAmount - (2 * (long) (amount * 0.18)), tran.getAccountDatabase().getAccount(1111).getAccountBalance());
        Assert.assertEquals(startAmount + (long) (amount * 0.18), tran.getAccountDatabase().getAccount(2212).getAccountBalance());


        ctBuyProperty = new CompoundTransaction();
        ctPrepareLoan = new CompoundTransaction();
        ctPayFees = new CompoundTransaction();

        ctBuyProperty.addChildTransaction(ctPrepareLoan);
        ctBuyProperty.addChildTransaction(ctPayFees);

        trAllLoanFunds = new Transaction(tran.getAccountDatabase().getAccount(1234),tran.getAccountDatabase().getAccount(4567),amount);
        trTranToSeller = new Transaction(tran.getAccountDatabase().getAccount(4567),tran.getAccountDatabase().getAccount(6781),amount);

        ctPrepareLoan.addChildTransaction(trAllLoanFunds);
        ctPrepareLoan.addChildTransaction(trTranToSeller);

        trPayLegalFees = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2321),(long)(amount*0.18));
        trPayTaxes = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2212),(long)(amount*0.18));

        ctPayFees.addChildTransaction(trPayLegalFees);
        ctPayFees.addChildTransaction(trPayTaxes);

        Assert.assertEquals(false, tran.processTransaction(ctBuyProperty));

        Assert.assertEquals(startAmount - amount, tran.getAccountDatabase().getAccount(1234).getAccountBalance());
        Assert.assertEquals(startAmount + amount, tran.getAccountDatabase().getAccount(6781).getAccountBalance());
        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(4567).getAccountBalance());

        Assert.assertEquals(startAmount+(long)(amount*0.18), tran.getAccountDatabase().getAccount(2321).getAccountBalance());
        Assert.assertEquals(startAmount - (2 * (long) (amount * 0.18)), tran.getAccountDatabase().getAccount(1111).getAccountBalance());
        Assert.assertEquals(startAmount + (long) (amount * 0.18), tran.getAccountDatabase().getAccount(2212).getAccountBalance());


    }

    @Test
    public void testValidCompoundTransactionTwiceWait15Sec1(){
        int amount = 200;
        int startAmount = 1000;

        CompoundTransaction ctBuyProperty = new CompoundTransaction();
        CompoundTransaction ctPrepareLoan = new CompoundTransaction();
        CompoundTransaction ctPayFees = new CompoundTransaction();

        ctBuyProperty.addChildTransaction(ctPrepareLoan);
        ctBuyProperty.addChildTransaction(ctPayFees);

        Transaction trAllLoanFunds = new Transaction(tran.getAccountDatabase().getAccount(1234),tran.getAccountDatabase().getAccount(4567),amount);
        Transaction trTranToSeller = new Transaction(tran.getAccountDatabase().getAccount(4567),tran.getAccountDatabase().getAccount(6781),amount);

        ctPrepareLoan.addChildTransaction(trAllLoanFunds);
        ctPrepareLoan.addChildTransaction(trTranToSeller);

        Transaction trPayLegalFees = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2321),(long)(amount*0.18));
        Transaction trPayTaxes = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2212),(long)(amount*0.18));

        ctPayFees.addChildTransaction(trPayLegalFees);
        ctPayFees.addChildTransaction(trPayTaxes);


        Assert.assertEquals(true, tran.processTransaction(ctBuyProperty));

        Assert.assertEquals(startAmount - amount, tran.getAccountDatabase().getAccount(1234).getAccountBalance());
        Assert.assertEquals(startAmount + amount, tran.getAccountDatabase().getAccount(6781).getAccountBalance());
        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(4567).getAccountBalance());

        Assert.assertEquals(startAmount+(long)(amount*0.18), tran.getAccountDatabase().getAccount(2321).getAccountBalance());
        Assert.assertEquals(startAmount - (2 * (long) (amount * 0.18)), tran.getAccountDatabase().getAccount(1111).getAccountBalance());
        Assert.assertEquals(startAmount + (long) (amount * 0.18), tran.getAccountDatabase().getAccount(2212).getAccountBalance());


        wait15Sec();

        ctBuyProperty = new CompoundTransaction();
        ctPrepareLoan = new CompoundTransaction();
        ctPayFees = new CompoundTransaction();

        ctBuyProperty.addChildTransaction(ctPrepareLoan);
        ctBuyProperty.addChildTransaction(ctPayFees);

        trAllLoanFunds = new Transaction(tran.getAccountDatabase().getAccount(1234),tran.getAccountDatabase().getAccount(4567),amount);
        trTranToSeller = new Transaction(tran.getAccountDatabase().getAccount(4567),tran.getAccountDatabase().getAccount(6781),amount);

        ctPrepareLoan.addChildTransaction(trAllLoanFunds);
        ctPrepareLoan.addChildTransaction(trTranToSeller);

        trPayLegalFees = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2321),(long)(amount*0.18));
        trPayTaxes = new Transaction(tran.getAccountDatabase().getAccount(1111),tran.getAccountDatabase().getAccount(2212),(long)(amount*0.18));

        ctPayFees.addChildTransaction(trPayLegalFees);
        ctPayFees.addChildTransaction(trPayTaxes);

        Assert.assertEquals(true, tran.processTransaction(ctBuyProperty));

        Assert.assertEquals(startAmount - (2*amount), tran.getAccountDatabase().getAccount(1234).getAccountBalance());
        Assert.assertEquals(startAmount + (2*amount), tran.getAccountDatabase().getAccount(6781).getAccountBalance());
        Assert.assertEquals(startAmount, tran.getAccountDatabase().getAccount(4567).getAccountBalance());

        Assert.assertEquals(startAmount+(long)((2*amount)*0.18), tran.getAccountDatabase().getAccount(2321).getAccountBalance());
        Assert.assertEquals(startAmount - (2 * (long) ((2*amount) * 0.18)), tran.getAccountDatabase().getAccount(1111).getAccountBalance());
        Assert.assertEquals(startAmount + (long) ((2*amount) * 0.18), tran.getAccountDatabase().getAccount(2212).getAccountBalance());


    }


}

