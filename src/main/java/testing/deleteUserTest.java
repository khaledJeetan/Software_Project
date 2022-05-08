package testing;
import model.User;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import projects.software.restaurantns.DbWrapper;
import static org.junit.Assert.assertEquals;
import projects.software.restaurantns.SingupController;
import static org.junit.Assert.assertEquals;

public class deleteUserTest {
    @Test
    public void evaluateTrue () {
        DbWrapper db = new DbWrapper();
        User user = new User();
        user.setName("name");
        boolean result = db.deleteUser(user);
        assertEquals("Checking user in DB: ",false,result);
        db = null;
    }
    @Test
    public void evaluateFalse() {
        DbWrapper db = new DbWrapper();
        User user = new User();
        user.setName("rahaf");
        boolean result = db.deleteUser(user);
        boolean expectedresult = true;
        assertEquals("Checking user in DB: ",true,result);
        db = null;
    }
}
