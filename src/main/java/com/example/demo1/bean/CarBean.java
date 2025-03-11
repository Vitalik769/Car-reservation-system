package com.example.demo1.bean;

import com.example.demo1.models.Car;
import com.example.demo1.services.CarService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class CarBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Car newCar = new Car();
    private Car selectedCar = new Car();
    private List<Car> cars;

    @Inject
    private CarService carService;

    @PostConstruct
    public void init() {
        cars = carService.getAllCars();
    }

    public void addCar() {
        if (newCar.getBrand() == null || newCar.getModel() == null || newCar.getYear() == 0 || newCar.getPricePerDay() == 0) {
            System.out.println("❌ Помилка: не всі поля заповнені!");
            return;
        }

        carService.addCar(newCar);
        cars = carService.getAllCars();
        newCar = new Car();

        PrimeFaces.current().ajax().update("carsTable", "addCarForm");
        System.out.println("✅ Авто успішно додано!");
    }

    public void deleteCar(Long carId) {
        if (carId == null) {
            System.out.println("❌ Помилка: ID авто NULL! Видалення неможливе.");
            return;
        }

        carService.deleteCar(carId);
        cars = carService.getAllCars();

        PrimeFaces.current().ajax().update("carsTable");
        System.out.println("✅ Видалено авто з ID: " + carId);
    }

    // Метод для відкриття діалогу
    public void openEditDialog(Car car) {
        this.selectedCar = new Car(car.getId(), car.getBrand(), car.getModel(), car.getYear(), car.getPricePerDay(), car.getStatus());
    }

    // Метод для оновлення авто
    public void updateCar() {
        if (selectedCar == null || selectedCar.getId() == null) {
            System.out.println("❌ Помилка: немає вибраного авто або ID NULL!");
            return;
        }

        carService.updateCar(selectedCar);
        cars = carService.getAllCars();

        PrimeFaces.current().ajax().update("carsTable", "editCarDialog");
        System.out.println("✅ Авто оновлено: " + selectedCar.getId());
    }


    public List<Car> getAvailableCars() {
        return cars.stream().filter(car -> "available".equalsIgnoreCase(car.getStatus())).collect(Collectors.toList());
    }

    // Гетери та сетери
    public Car getNewCar() { return newCar; }
    public void setNewCar(Car newCar) { this.newCar = newCar; }
    public List<Car> getCars() { return cars; }
    public Car getSelectedCar() { return selectedCar; }
    public void setSelectedCar(Car selectedCar) { this.selectedCar = selectedCar; }
}



