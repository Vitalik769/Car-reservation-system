package com.example.demo1;

import com.example.demo1.database.DataService;
import com.example.demo1.models.User;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import jakarta.persistence.NoResultException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @EJB
    private DataService dataService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = dataService.findUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            // Успішна автентифікація
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("index.xhtml"); // або до іншої захищеної сторінки
        } else {
            // Невдала автентифікація – повертаємо повідомлення про помилку
            request.setAttribute("errorMessage", "Невірний логін або пароль");
            request.getRequestDispatcher("login.xhtml").forward(request, response);
        }
    }
}
