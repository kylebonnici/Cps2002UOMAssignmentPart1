package um.edu.mt.cps2002.assignment.part1;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestAccountDatabase {

    AccountDatabase accDb;

    @Test
    public void testCreateNewAccount() {
        accDb = new AccountDatabase();

        for (int outerLoops = 0; outerLoops < 2; outerLoops++){
            for (int loops = 1; loops <= 1000; loops++) {
                boolean succ = accDb.createNewAccount(loops);

                Assert.assertEquals(outerLoops==0, succ);
            }
        }
    }

    @Test
    public void testGetSize(){
        accDb = new AccountDatabase();
        int qty = 100;

        for (int loops = 0; loops < qty; loops++) {
            accDb.createNewAccount(loops);
            Assert.assertEquals(loops, accDb.getSize());
        }

        for (int loops = 0; loops < qty; loops++) {
            accDb.createNewAccount(loops);
            Assert.assertEquals(qty, accDb.getSize());
        }

    }

    @Test
    public void testGetAccount(){
        accDb = new AccountDatabase();

        for (int outerLoops = 0 ; outerLoops < 2 ; outerLoops ++ ) {
            for (int loops = 0; loops < 100; loops++) {

                boolean found = accDb.getAccount(loops) != null;

                if (found) found = accDb.getAccount(loops).getAccountNumber() == loops;

                Assert.assertEquals(outerLoops != 0 , found);

                if (!found) accDb.createNewAccount(loops);
            }
        }
    }
}















