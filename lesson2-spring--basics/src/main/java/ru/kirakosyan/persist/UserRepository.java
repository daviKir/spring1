package ru.kirakosyan.persist;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository {
    public List<User> findAll();

    public User findById(long id);

    public void insert(User user);

    public void update(User user);

    public void delete(long id);

    public long getCount();
}
