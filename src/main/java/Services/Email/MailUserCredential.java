package Services.Email;

import org.springframework.stereotype.Component;

/**
 * @author: Andy Su
 * @Date: 11/2/2018
 */

public class MailUserCredential {
    private final String userName;
    private final String password;

    public MailUserCredential(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
