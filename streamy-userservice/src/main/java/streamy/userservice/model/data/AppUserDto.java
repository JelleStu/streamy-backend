package streamy.userservice.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import streamy.userservice.model.Role;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    private UUID id;
    private String email;
    private String name;
    private String password;
    private Collection<Role> roles = new ArrayDeque<>();
    private String iban;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
