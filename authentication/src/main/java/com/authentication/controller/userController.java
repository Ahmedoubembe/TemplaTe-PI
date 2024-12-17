package com.authentication.controller;
import com.authentication.dto.userResponse;
import com.authentication.model.User;
import com.authentication.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class userController {

    @Autowired
    private userService service;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping
    public ResponseEntity<List<userResponse>> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUserDetails) {
        return service.updateUser(id, newUserDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return service.deleteUser(id);
    }

}