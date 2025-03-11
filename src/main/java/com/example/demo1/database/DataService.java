package com.example.demo1.database;

import com.example.demo1.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class DataService {

    @PersistenceContext
    private EntityManager entityManager;

    // User CRUD operations
    public void createUser(User user) {
        entityManager.persist(user);
    }

    public User readUser(Long id) {
        return entityManager.find(User.class, id);
    }

    public void updateUser(User user) {
        entityManager.merge(user);
    }

    public void deleteUser(Long id) {
        User user = readUser(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
    public User findUserByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> findAllUsers() {
        return entityManager.createNamedQuery("User.findAll", User.class).getResultList();
    }
}
