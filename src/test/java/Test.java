import model.person.User;
import org.junit.jupiter.api.Assertions;
import view.CommandProcessor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Test {

    @org.junit.jupiter.api.Test
    public void testLoginProcess() {
        new User("zahra", "001001", "zar");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(("""
                user login --username zar --password 001001
                user login --username zahra --password 00100
                user login --username zahra --password 001001
                user logout
                exit
                """).getBytes());
        System.setIn(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        CommandProcessor.login();
        Assertions.assertEquals("""
                welcome to duel links!
                Username and password didn’t match!
                Username and password didn’t match!
                user logged in successfully!
                user logged out successfully!
                """, outputStream.toString());
    }

}
