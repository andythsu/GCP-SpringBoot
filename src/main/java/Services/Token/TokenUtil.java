package Services.Token;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {
    private static int TOKEN_LENGTH = 24;

    @Autowired
    private TokenSession tokenSession;

    public AuthToken acqureToken(){
        String token = RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH);
        AuthToken authToken = new AuthToken(token);
        tokenSession.setSession(token, authToken);
        return authToken;
    }
    public boolean isTokenExpired(AuthToken token){
        return token.getExpiredAtTime().after(new Date());
    }
}
