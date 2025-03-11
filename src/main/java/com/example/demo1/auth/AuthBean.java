package com.example.demo1.auth;

import com.example.demo1.models.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("authBean")
@SessionScoped
public class AuthBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private User loggedInUser;

    @Inject
    private AuthenticationService authService;

    // Метод для входу користувача
    public String login() {
        loggedInUser = authService.authenticate(username, password);
        if (loggedInUser != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Вхід успішний!", null));
            return "index?faces-redirect=true"; // Перенаправлення після входу
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Невірний логін або пароль!", null));
            return null;
        }
    }

    // Метод для виходу користувача
    public String logout() {
        loggedInUser = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    // Перевірка, чи користувач авторизований
    public boolean isAuthenticated() {
        return loggedInUser != null;
    }

    // Гетери та сеттери
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public User getLoggedInUser() { return loggedInUser; }
    public void setLoggedInUser(User user) { this.loggedInUser = user; } // ⚡ Додано для можливого встановлення користувача
}
