package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;



@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private final EntityManager entityManager;
    private final RoleDao roleDao;

    @Autowired
    public UserDaoImpl(EntityManager entityManager, RoleDao roleDao) {
        this.entityManager = entityManager;
        this.roleDao = roleDao;
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void save(User user) {
        var roles = user.getRoles();
        var roleList = roleDao.getRoles();
        var list = new ArrayList<Role>();
        for (Role role : roleList) {
            for (Role userRole : roles) {
                if (role.getName().equals(userRole.getName())) {
                    list.add(role);
                }
            }
        }
        user.setRoles(list);
        entityManager.persist(user);
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public User findByUsername(String username) {
        return entityManager
                .createQuery(
                        "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username=:username", User.class
                )
                .setParameter("username", username)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.find(User.class, id).getRoles().clear();
        entityManager
                .createQuery("DELETE FROM User WHERE id =:userId")
                .setParameter("userId", id)
                .executeUpdate();
    }
}














