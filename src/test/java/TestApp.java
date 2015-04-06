import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by kylebonnici on 31/03/15.
 */


public class TestApp {
    TestClass c;

    @Before
    public void reGenerateCalcInstance(){
        c = new TestClass();
    }

    @Test
    public void testAdd(){
        String ans = c.sayHello();
        assertEquals("Hello",ans);
    }

    @Test
    public void testSubtract(){
        String ans = c.sayBye();
        assertEquals("Bye",ans);
    }

}