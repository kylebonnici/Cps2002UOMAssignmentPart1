import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by kylebonnici on 31/03/15.
 */


public class TestApp {
    AccountDatabase acc;

    @Before
    public void reGenerateCalcInstance(){
        acc = new AccountDatabase();
    }

    @Test
    public void testCreatNewAccount(){
        String ans = acc.sayHello();
        assertEquals("Hello",ans);
    }

    @Test
    public void testSubtract(){
        String ans = c.sayBye();
        assertEquals("Bye",ans);
    }

}