package streamy.userservice.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import streamy.userservice.model.Role;
@Repository
public interface IRoleRepo extends MongoRepository<Role, Long> {
    @Query("{name: '?0'}")
    Role findRoleByName(String name);
}
