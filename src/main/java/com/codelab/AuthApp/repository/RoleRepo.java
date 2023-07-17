package com.codelab.AuthApp.repository;

import com.codelab.AuthApp.model.Role;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Qualifier("role")
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

   Role findByName(String name);
}
