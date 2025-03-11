package com.example.demo1;

import com.example.demo1.models.Car;
import com.example.demo1.models.Order;
import com.example.demo1.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Date;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = entityManager.createQuery("SELECT o FROM Order o", Order.class).getResultList();
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/orders.xhtml").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("user_id"));
        Long carId = Long.parseLong(req.getParameter("car_id"));
        Date startDate = new Date(req.getParameter("start_date"));
        Date endDate = new Date(req.getParameter("end_date"));
        String status = req.getParameter("status");

        User user = entityManager.find(User.class, userId);
        Car car = entityManager.find(Car.class, carId);

        if (user != null && car != null) {
            Order order = new Order(user, car, startDate, endDate, status);
            entityManager.getTransaction().begin();
            entityManager.persist(order);
            entityManager.getTransaction().commit();
        }

        resp.sendRedirect("/orders");
    }
}

