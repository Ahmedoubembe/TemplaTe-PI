package com.authentication.service;
import com.authentication.dto.userResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.authentication.model.User;
import com.authentication.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class userService {

    @Autowired
    private userRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public userResponse convertToDto(User user) {
        userResponse response = new userResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }

    public ResponseEntity<String> createUser(@RequestBody User user) {
        if(repository.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>("User avec cet email deja exist.", HttpStatus.CREATED);
        }
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        repository.save(user);
        return new ResponseEntity<>("User créé avec succès.", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<userResponse>> getAllUsers() {
        List<User> users = repository.findAll();
        List<userResponse> userDtos = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = repository.findById(id).orElse(null);
        if (user != null) {
            userResponse userDto = convertToDto(user);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUserDetails) {
        User oldUserDetails = repository.findById(id).orElse(null);
        if (oldUserDetails != null) {
            oldUserDetails.setEmail(newUserDetails.getEmail());
            oldUserDetails.setPassword(newUserDetails.getPassword());
            User updatedUser = repository.save(oldUserDetails);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
