package com.epam.hostel.dao;


import com.epam.hostel.model.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserCrud extends CrudRepository<User,Long> {
    User findByName(String name);
    Integer countAllByBanned();
}
