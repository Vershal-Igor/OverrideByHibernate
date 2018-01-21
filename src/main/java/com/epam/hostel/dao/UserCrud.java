package com.epam.hostel.dao;


import com.epam.hostel.model.user.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserCrud extends CrudRepository<User,Long> {
    User findByName(String name);
    Integer countAllByBanned();
}
