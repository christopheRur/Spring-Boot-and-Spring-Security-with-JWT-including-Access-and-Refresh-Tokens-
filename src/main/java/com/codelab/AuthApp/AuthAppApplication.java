package com.codelab.AuthApp;

import com.codelab.AuthApp.model.AppUser;
import com.codelab.AuthApp.model.Role;
import com.codelab.AuthApp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@ComponentScan(basePackages = {"com.codelab.AuthApp.*"})
@EntityScan("com.codelab.AuthApp.model")
@EnableJpaRepositories("com.codelab.AuthApp.repository")
public class AuthAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(AuthAppApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){

		return new BCryptPasswordEncoder();

	}

//	@Bean
//	CommandLineRunner run(UserService userService){
//
//		return args ->{
//			userService.saveRole(new Role(null,"ROLE_USER"));
//			userService.saveRole(new Role(null,"ROLE_ADMIN"));
//			userService.saveRole(new Role(null,"ROLE_MANAGER"));
//			userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));
//
//			userService.saveUser(new AppUser(null,"Wang", "Quiy","p123d",new ArrayList<>()));
//			userService.saveUser(new AppUser(null,"Yang", "Quid","p123dd",new ArrayList<>()));
//			userService.saveUser(new AppUser(null,"Wohan", "Quir","p123eg",new ArrayList<>()));
//
//			userService.addRoleToUser("Wang", "ROLE_USER");
//			userService.addRoleToUser("Yang", "ROLE_ADMIN");
//			userService.addRoleToUser("Wang", "ROLE_MANAGER");
//			userService.addRoleToUser("Wohan", "ROLE_SUPER_ADMIN");
//		};
//	}

}
