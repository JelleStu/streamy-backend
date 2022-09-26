package streamy.userservice.service;

import streamy.userservice.model.AppUser;
import streamy.userservice.model.Role;
import streamy.userservice.model.data.AppUserDto;

import java.util.List;

public interface IUserInterface {
    AppUser saveUser(AppUserDto user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    void deleteUser(AppUser user);

    AppUser getUserByEmail(String email);
    List<AppUser> getUsers();
}
