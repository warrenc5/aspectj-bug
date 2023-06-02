package test;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author wozza
 */
public class MyTest {

    @Test
    public void greetMyVictim() {
        MyVictim victim = new MyVictim();
        assertEquals("hello", victim.hello());
    }
}