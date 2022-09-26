package streamy.userservice.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import streamy.userservice.StreamyUserserviceApplication;
import streamy.userservice.messaging.MessageAction;
import streamy.userservice.messaging.MessageObject;
import streamy.userservice.messaging.Sender;
import streamy.userservice.model.AppUser;
import streamy.userservice.model.Role;
import streamy.userservice.model.data.AppUserDto;
import streamy.userservice.repository.IRoleRepo;
import streamy.userservice.repository.IUserRepo;
import streamy.userservice.security.CustomUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService implements IUserInterface, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private IUserRepo userRepository;
    @Autowired
    private IRoleRepo roleRepository;

    @Autowired
    Sender messageSender;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.getAppUserByEmail(email);
        if (user == null){
            log.info("User not found.");
            return null;
        }
        else{
            log.info("{} found.", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new CustomUser(user.getEmail(), user.getPassword(), authorities, user.getId());
    }

    @RabbitListener(queues = StreamyUserserviceApplication.queueName)
    public void listenForMessage(MessageObject messageObject){
        System.out.println("Expected a messageobject, got a " + messageObject.toString());
    }

   public boolean updateUser(AppUserDto userDto){
        AppUser appUser = new AppUser(userDto.getId(), userDto.getEmail(), userDto.getName(), userDto.getPassword(), userDto.getRoles());
       if (userExist(appUser)){
           MessageObject messageObject = new MessageObject();
           messageObject.setMessageAction(MessageAction.UPDATE);
           messageObject.setUserID(userDto.getId());
           messageObject.setIban(userDto.getIban());
           messageSender.SendMessage(messageObject);
           userRepository.save(appUser);
           log.info("User with email {} updated!", appUser.getEmail());
           return true;
       }
       else{
           log.info("User doesnt exist!");
           return false;
       }
    }
    public AppUser saveUser(AppUserDto user) {
        if (loadUserByUsername(user.getEmail()) != null){
            log.info("User with email {} already exist", user.getEmail());
            return null;
        }
        else {
            AppUser appUser = new AppUser();
            appUser.setId(UUID.randomUUID());
            appUser.setName(user.getName());
            appUser.setEmail(user.getEmail());
            appUser.setPassword(passwordEncoder.encode(user.getPassword()));
            Collection<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findRoleByName("ROLE_USER"));
            appUser.setRoles(roles);
            log.info("Saving new user with name {}", user.getName());
            MessageObject messageObject = new MessageObject();
            messageObject.setMessageAction(MessageAction.ADD);
            messageObject.setUserID(appUser.getId());
            messageObject.setIban(user.getIban());
            messageSender.SendMessage(messageObject);
            return userRepository.save(appUser);
        }
    }

    public Role saveRole(Role role) {
        log.info("Saving new role {}", role);
        return roleRepository.save(role);
    }

    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role: {} to user {}", roleName, email);
        AppUser user = userRepository.getAppUserByEmail(email);
        Role role = roleRepository.findRoleByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void deleteUser(AppUser user) {
        userRepository.delete(user);
        MessageObject messageObject = new MessageObject();
        messageObject.setMessageAction(MessageAction.DELETE);
        messageObject.setUserID(user.getId());
        messageSender.SendMessage(messageObject);
        log.info("User {} deleted!", user.getEmail());
    }

    public AppUser getUserByEmail(String email) {
        log.info("Fetching user: {}", email);
        return userRepository.getAppUserByEmail(email);
    }

    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public boolean userExist(AppUser user){
        return userRepository.findById(user.getId()).isPresent();
    }
}
