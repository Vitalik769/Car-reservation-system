package com.example.demo1.services;

import com.example.demo1.models.Car;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Stateless
public class CarService implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public List<Car> getAllCars() {
        return em.createQuery("SELECT c FROM Car c", Car.class).getResultList();
    }

    public void addCar(Car car) {
        try {
            System.out.println("Додавання авто в базу: " + car.getBrand() + " " + car.getModel());
            em.persist(car);
            System.out.println("Авто успішно збережено в базу!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Помилка під час додавання авто: " + e.getMessage());
        }
    }

    public void deleteCar(Long id) {
        em.createQuery("DELETE FROM Order o WHERE o.car.id = :carId")
                .setParameter("carId", id)
                .executeUpdate();

        Car car = em.find(Car.class, id);
        if (car != null) {
            em.remove(car);
            em.flush();
            System.out.println("✅ Авто з ID " + id + " успішно видалено.");
        }
    }

    public Car getCarById(Long id) {
        return em.find(Car.class, id);
    }

    // Метод оновлення авто
    public void updateCar(Car car) {
        Car existingCar = em.find(Car.class, car.getId()); // Шукаємо авто за ID
        if (existingCar != null) {
            existingCar.setBrand(car.getBrand());
            existingCar.setModel(car.getModel());
            existingCar.setYear(car.getYear());
            existingCar.setPricePerDay(car.getPricePerDay());
            existingCar.setStatus(car.getStatus());
            em.merge(existingCar); // Оновлюємо існуючий запис
            System.out.println("✅ Авто з ID " + car.getId() + " оновлено!");
        } else {
            System.out.println("❌ Авто з ID " + car.getId() + " не знайдено!");
        }
    }
}


