package com.example.demo1.auth;

import com.example.demo1.database.DataService;
import com.example.demo1.models.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.io.Serializable;

@Stateless
public class AuthenticationService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DataService dataService;

    public User authenticate(String username, String password) {
        User user = dataService.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Якщо користувач знайдений і пароль правильний
        }
        return null;
    }
}
