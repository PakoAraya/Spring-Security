package com.segurito.spring.controllers;

import com.segurito.spring.dtos.UserLoginDTO;
import com.segurito.spring.services.UserLoginServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * RestController to manage endpoints related to users.
 * This controller implements a RESTful API for handling CRUD operations for users.
 */
@RestController
@RequestMapping("/api/users")
public class UserLoginRestController {

  @Autowired
  private UserLoginServiceImpl userLoginServiceImpl;

  /**
   * Endpoint to get all users.
   *
   * @return ResponseEntity with the list of users and the corresponding HTTP status.
   */
  @GetMapping
  public ResponseEntity<List<UserLoginDTO>> getAllUsers() {
    try {
      List<UserLoginDTO> users = userLoginServiceImpl.findAllUserLogins();
      return ResponseEntity.status(HttpStatus.OK).body(users);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  /**
   * Endpoint to get a user by their ID.
   *
   * @param id User ID.
   * @return ResponseEntity with user data or an error message if not found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Object> getUserById(@PathVariable Long id) {
    try {
      UserLoginDTO user = userLoginServiceImpl.findUserLoginById(id)
              .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
      return ResponseEntity.status(HttpStatus.OK).body(user);
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
              Map.of(
                      "error", "User not found",
                      "code", HttpStatus.NOT_FOUND,
                      "details", e.getMessage()
              )
      );
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
              Map.of(
                      "error", "Error retrieving user",
                      "code", HttpStatus.INTERNAL_SERVER_ERROR,
                      "details", e.getMessage()
              )
      );
    }
  }

  /**
   * Endpoint to add a new user.
   *
   * @param userLoginDTO User data to add.
   * @return ResponseEntity with the added user or an error message.
   */
  @PostMapping
  public ResponseEntity<Object> addUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
    try {
      UserLoginDTO savedUser = userLoginServiceImpl.addUser(userLoginDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
              Map.of(
                      "error", "Invalid data",
                      "code", HttpStatus.BAD_REQUEST,
                      "details", e.getMessage()
              )
      );
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
              Map.of(
                      "error", "Error saving user",
                      "code", HttpStatus.INTERNAL_SERVER_ERROR,
                      "details", e.getMessage()
              )
      );
    }
  }

  /**
   * Endpoint to access the dashboard (accessible only by users with the ADMIN role).
   *
   * @return ResponseEntity with the list of users visible in the dashboard.
   */
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/dashboard")
  public ResponseEntity<Object> dashboard() {
    try {
      List<UserLoginDTO> users = userLoginServiceImpl.findAllUserLogins();
      return ResponseEntity.status(HttpStatus.OK).body(users);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
              Map.of(
                      "error", "Error accessing the dashboard",
                      "code", HttpStatus.INTERNAL_SERVER_ERROR,
                      "details", e.getMessage()
              )
      );
    }
  }

  /**
   * Endpoint for general issues (example, can be adapted as needed).
   *
   * @return Generic "problems" message.
   */
  @GetMapping("/problems")
  public ResponseEntity<Object> problems() {
    return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Problems section"));
  }
}

