package streamy.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Document("users")
public class AppUser {
    @Id
    private UUID id;
    @Indexed(unique = true)
    private String email;
    private String name;
    private String password;
    @ManyToMany(fetch= FetchType.EAGER)
    private Collection<Role> roles = new ArrayDeque<>();
}
