package um.edu.mt.cps2002.assignment.part1;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAccountDatabase {

    AccountDatabase accDb;

    @Test
    public void testCreatNewAccount(){
        accDb = new AccountDatabase();
        accDb.createNewAccount(0);

        Assert.assertEquals(0, accDb.getAccount(0).accountNumber );
    }
}
