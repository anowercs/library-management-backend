package org.neurogine.librarymanagementbackend.repository;

import org.neurogine.librarymanagementbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);

    boolean existsByUserNameAndPassword(String userName, String password);

    List<User> findByUserNameContaining(String keyword);


}
