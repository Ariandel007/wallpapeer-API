package com.wallpapeer.controllers;

import com.wallpapeer.models.User;
import com.wallpapeer.payload.request.UserChangePass;
import com.wallpapeer.payload.response.MessageResponse;
import com.wallpapeer.payload.response.UpdateUser;
import com.wallpapeer.repository.RoleRepository;
import com.wallpapeer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder encoder;

  @PutMapping("/user/{username}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> updatePassword(@PathVariable String username, @RequestBody UserChangePass userChangePass, Principal principal) {
    String currentUsername = principal.getName();
    if(!username.equals(currentUsername)) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: This is not the user!"));
    }

    if(!username.equals(userChangePass.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: This is not the user!"));
    }

    Optional<User> userApp = userRepository.findByUsername(userChangePass.getUsername());

    if(!userApp.isPresent()) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: This is not the user!"));
    }

    User userToEdit = userApp.get();
    userToEdit.setPassword(encoder.encode(userChangePass.getPassword()));

    userRepository.save(userToEdit);

    return ResponseEntity.ok().body(new UpdateUser(userToEdit.getUsername()));
  }


}
