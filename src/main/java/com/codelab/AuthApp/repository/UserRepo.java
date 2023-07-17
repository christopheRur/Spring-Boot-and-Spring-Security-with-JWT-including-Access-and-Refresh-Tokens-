package com.codelab.AuthApp.repository;

import com.codelab.AuthApp.model.AppUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Qualifier("user")
@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);

}
