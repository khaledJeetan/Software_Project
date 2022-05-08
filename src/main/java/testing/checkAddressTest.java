package testing;
import model.Address;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import projects.software.restaurantns.DbWrapper;
import static org.junit.Assert.assertEquals;

public class checkAddressTest {
    @Test
    public void evaluateTrue () {
        DbWrapper db = new DbWrapper();
        Address a = new Address();
        a.setId(70);
        a.setLocation("aa");
        a.setCity("Nablus");
        int result = db.checkAddress(a);
        assertEquals("Checking Address in DB: ",70,result);
        db = null;
    }
    @Test
    public void evaluateFalse() {
        DbWrapper db = new DbWrapper();
        Address a = new Address();
        a.setId(5);
        a.setLocation("rafediya");
        a.setCity("nablus");
        int result = db.checkAddress(a);
        assertEquals("Checking Address in DB: ",0,result);
        db = null;
    }
}
