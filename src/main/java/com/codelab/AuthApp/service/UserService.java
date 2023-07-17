package com.codelab.AuthApp.service;

import com.codelab.AuthApp.model.Role;
import com.codelab.AuthApp.model.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {

    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();

}
