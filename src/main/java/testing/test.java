package testing;


import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import projects.software.restaurantns.SingupController;
import static org.junit.Assert.assertEquals;

public class test {
    @Test
    public void evaluateTrue () {
        SingupController signup = new SingupController();
        boolean result = signup.isUser("rahaf");
        boolean expectedresult = true;
        assertEquals("Checking user in DB: ",true,result);
        signup = null;
    }
    @Test
    public void evaluateFalse() {
        var signup = new SingupController();
        boolean result = signup.isUser("name");
        assertEquals(true,result);
    }
}
