package com.jojo.gezginservice.controller;

import com.jojo.gezginservice.model.User;
import com.jojo.gezginservice.response.UserResponse;
import com.jojo.gezginservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        logger.log(Level.INFO, "[UserController] -- user retrieved successfully. ");
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email) {
        logger.log(Level.INFO, "[UserController] -- user of type {0} retrieved. ", email);
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<User>> getByUserId(@PathVariable("id") Integer userId) throws Exception {
        return ResponseEntity.ok(userService.getById(userId));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        userService.delete(id);
    }

}


