package ru.practicum.ewm.mainservice.user.repository;

import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.mainservice.repository.Repository;
import ru.practicum.ewm.mainservice.user.entity.User;

import java.util.List;


public interface UserRepository extends Repository<User, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM users OFFSET ?1 LIMIT ?2")
    List<User> findAll(int from, int size);
}
