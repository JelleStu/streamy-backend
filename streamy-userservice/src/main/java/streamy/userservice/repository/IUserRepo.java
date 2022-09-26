package streamy.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import streamy.userservice.model.AppUser;

import java.util.UUID;

@Repository
public interface IUserRepo extends MongoRepository<AppUser, UUID> {
    @Query("{email:'?0'}")
    AppUser getAppUserByEmail(String email);


}
