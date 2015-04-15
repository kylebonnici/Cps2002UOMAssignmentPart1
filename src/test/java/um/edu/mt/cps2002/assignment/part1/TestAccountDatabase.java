package um.edu.mt.cps2002.assignment.part1;

import java.util.Random;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestAccountDatabase {

    AccountDatabase accDb;

    @Before
    public void createNewDatabase(){
        accDb = new AccountDatabase();
    }

    @Test
    public void testCreateNewAccount() {
        int qty = 100;

        //(outerLoops == 0) create qty accounts since database is empty account creation should be succsesfull.
        //(outerLoops == 1) re create qry accounts with same is as before but since they already exist creation should NOT be succsessfull.
        for (int outerLoops = 0; outerLoops < 2; outerLoops++){
            for (int loops = 1; loops <= qty; loops++) {
                boolean succ = accDb.createNewAccount(loops);

                Assert.assertEquals(outerLoops==0, succ);
            }
        }
    }

    @Test
    public void testGetSize(){
        int qty = 100;

        //create qty accounts and check size after each new account
        for (int loops = 0; loops < qty; loops++) {
            accDb.createNewAccount(loops);
            Assert.assertEquals(loops + 1, accDb.getSize());
        }

        //re create accounts with the same acc number thus no new account should be creathed thus size shuld still be == to qty
        for (int loops = 0; loops < qty; loops++) {
            accDb.createNewAccount(loops);
            Assert.assertEquals(qty, accDb.getSize());
        }

    }

    @Test
    public void testGetAccount(){
        //(outerLoops == 0) trys to look for account with Id = loops since database is empty found should be false.
        // accoun is creathed (so that further test cen be don when outerLoops == 1
        //(outerLoops == 1) trys to look for account with Id = loops since database is now not empty acount should be found and accoun number should match loops
        for (int outerLoops = 0 ; outerLoops < 2 ; outerLoops ++ ) {
            for (int loops = 0; loops < 100; loops++) {

                boolean found = accDb.getAccount(loops) != null;

                if (found) found = accDb.getAccount(loops).getAccountNumber() == loops;

                Assert.assertEquals(outerLoops != 0 , found);

                if (outerLoops == 0) accDb.createNewAccount(loops);
            }
        }
    }
}















