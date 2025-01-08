package com.segurito.spring.repositories;

import com.segurito.spring.models.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserLoginRepositoryJPA extends JpaRepository<UserLogin, Long> {
  /**
   * Search for a user by its name.
   *
   * @param name its the name of the user.
   * @return an Optional with the user if it exists, or empty if it is not found.
   */
  Optional<UserLogin> findByName(String name);

  /**
   * Search for a user by its email.
   *
   * @param email its the email of the user.
   * @return an Optional with the user if it exists, or empty if it is not found.
   */
  Optional<UserLogin> findUserLoginByEmail(String email);

  /**
   * Search for a user by its id.
   *
   * @param id its the id of the user.
   * @return an Optional with the user if it exists, or empty if it is not found.
   */
  Optional<UserLogin> findUserLoginById(Long id);

}
