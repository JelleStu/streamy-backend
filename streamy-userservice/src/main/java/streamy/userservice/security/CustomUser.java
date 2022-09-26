package streamy.userservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class CustomUser extends User {
    private  UUID userID;

    public CustomUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities, UUID userID) {
        super(username, password, authorities);
        this.userID = userID;
    }

    public void setUserID(UUID uuid){
        this.userID = uuid;
    }

    public UUID getUserID(){
        return this.userID;
    }

    public String getUserIDString(){
        return this.userID.toString();
    }
}
