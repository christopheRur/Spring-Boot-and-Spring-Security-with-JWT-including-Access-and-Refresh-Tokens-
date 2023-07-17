package com.codelab.AuthApp.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.codelab.AuthApp.model.AppUser;
import com.codelab.AuthApp.model.Role;
import com.codelab.AuthApp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers(){

        return ResponseEntity.ok(userService.getUsers());
    }

 @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){

        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/user/save").toUriString());

        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

 @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){

     URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/role/save").toUriString());

     return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

@PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){

    userService.addRoleToUser(form.getRoleName(),form.getUserName());

     return ResponseEntity.ok().build();
    }

@GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader =request.getHeader(AUTHORIZATION);
    if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

        try {

            String refreshToken = authorizationHeader.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            String username = decodedJWT.getSubject();
            AppUser user = userService.getUser(username);


            String accessToken = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles",user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                    .sign(algorithm);


            Map<String,String> tokens = new HashMap<>();

            tokens.put("accessToken",accessToken);
            tokens.put("refreshToken",refreshToken);

            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);

        }catch (Exception e){

            log.error("Login Exception: {} ",e.getMessage());

            response.setHeader("error ",e.getMessage());
            //response.sendError(FORBIDDEN.value());
            response.setStatus(FORBIDDEN.value());

            Map<String,String> error = new HashMap<>();

            error.put("error_msg ",e.getMessage());

            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);

        }

    }

    else{

        throw new RuntimeException("Refresh token is missing.");
    }



    }



}

@Data
class RoleToUserForm{
    private String userName;
    private String roleName;
}