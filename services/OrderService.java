package com.example.demo1.services;

import com.example.demo1.models.Order;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.io.Serializable;

@Stateless
public class OrderService implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    // Отримати всі замовлення
    public List<Order> getAllOrders() {
        return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }

    // Додати нове замовлення
    public void addOrder(Order order) {
        try {
            em.persist(order);
            System.out.println("✅ Замовлення успішно збережене!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Помилка при збереженні замовлення: " + e.getMessage());
        }
    }

    // Видалити замовлення
    public void deleteOrder(Long id) {
        Order order = em.find(Order.class, id);
        if (order != null) {
            em.remove(order);
            em.flush();
            System.out.println("✅ Замовлення успішно видалено: ID " + id);
        } else {
            System.out.println("❌ Помилка: Замовлення не знайдено.");
        }
    }

    // Отримати замовлення за ID
    public Order getOrderById(Long id) {
        return em.find(Order.class, id);
    }

    // ✅ Додати оновлення замовлення
    public void updateOrder(Order order) {
        Order existingOrder = em.find(Order.class, order.getId());
        if (existingOrder != null) {
            existingOrder.setStartDate(order.getStartDate());
            existingOrder.setEndDate(order.getEndDate());
            existingOrder.setStatus(order.getStatus());
            em.merge(existingOrder);
            System.out.println("✅ Замовлення оновлено: ID " + order.getId());
        } else {
            System.out.println("❌ Помилка: Замовлення з ID " + order.getId() + " не знайдено!");
        }
    }
}