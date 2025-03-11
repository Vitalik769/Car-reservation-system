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

@WebServlet("/cars")
public class CarServlet extends HttpServlet {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            // Видалення авто
            Long carId = Long.parseLong(req.getParameter("id"));
            Car car = entityManager.find(Car.class, carId);
            if (car != null) {
                entityManager.remove(car);
            }
        } else {
            // Додавання авто
            String brand = req.getParameter("brand");
            String model = req.getParameter("model");
            int year = Integer.parseInt(req.getParameter("year"));
            double price = Double.parseDouble(req.getParameter("price"));
            String status = req.getParameter("status");

            Car car = new Car(brand, model, year, price, status);
            entityManager.persist(car);
        }

        resp.sendRedirect("/cars");
    }
}
