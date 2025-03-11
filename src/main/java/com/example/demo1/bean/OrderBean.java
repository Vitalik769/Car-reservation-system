package com.example.demo1.bean;

import com.example.demo1.models.Car;
import com.example.demo1.models.Order;
import com.example.demo1.models.User;
import com.example.demo1.auth.AuthBean;
import com.example.demo1.services.CarService;
import com.example.demo1.services.OrderService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named("orderBean")
@SessionScoped
public class OrderBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Order newOrder = new Order();
    private Order selectedOrder;
    private List<Order> orders;
    private Long selectedCar;
    private Date startDate;
    private Date endDate;

    @Inject
    private OrderService orderService;

    @Inject
    private CarService carService;

    @Inject
    private AuthBean authBean;

    @PostConstruct
    public void init() {
        orders = orderService.getAllOrders();
    }

    public void createOrder() {
        if (selectedCar == null || startDate == null || endDate == null) return;

        Car car = carService.getCarById(selectedCar);
        User user = authBean.getLoggedInUser();
        if (car == null || user == null) return;

        newOrder = new Order(user, car, startDate, endDate, "pending");
        orderService.addOrder(newOrder);
        orders = orderService.getAllOrders();
        PrimeFaces.current().ajax().update("ordersTable", "addOrderForm");
    }

    public void openEditDialog(Order order) {
        this.selectedOrder = order;
    }

    public void updateOrder() {
        orderService.updateOrder(selectedOrder);
        orders = orderService.getAllOrders();
        PrimeFaces.current().ajax().update("ordersTable", "editOrderDialog");
    }

    public void deleteOrder(Long id) {
        orderService.deleteOrder(id);
        orders = orderService.getAllOrders();
        PrimeFaces.current().ajax().update("ordersTable");
    }

    // ГЕТЕРИ ТА СЕТЕРИ
    public Long getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Long selectedCar) {
        this.selectedCar = selectedCar;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Order getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Order selectedOrder) {
        this.selectedOrder = selectedOrder;
    }
}