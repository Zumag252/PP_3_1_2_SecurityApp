package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    @Autowired
    public UserDaoImpl(EntityManager entityManager, PasswordEncoder passwordEncoder, UserRepository repository) {
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Override
    public Set<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultStream().collect(Collectors.toSet());
    }

    @Override
    public void createUser(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        entityManager.persist(user);
    }

    @Override
    public User userById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteUser(long id) {
        User user = userById(id);
        if (user == null) {
            throw new NullPointerException("Пользователь с таким id не найден");
        }
        entityManager.remove(user);
    }

    @Override
    public User updateUser(long id, User updatedUser) {
        updatedUser = entityManager.merge(updatedUser);
        return updatedUser;
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}














