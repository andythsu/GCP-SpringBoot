package Services.Token;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenSession {
    private static Map<String, AuthToken> session = new HashMap<>();
    public AuthToken getSession(String key){
        if (hasSession(key)) return session.get(key);
        return null;
    }
    public void setSession(String key, AuthToken token){
        session.put(key, token);
    }
    public boolean hasSession(String key){
        return session.containsKey(key);
    }
}
