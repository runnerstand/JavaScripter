package com.bookstore.services;

import com.bookstore.models.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private List<Customer> customers;

    public CustomerService() {
        this.customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer findCustomerById(String id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Customer getOrCreateCustomer(String id) {
        Customer customer = findCustomerById(id);
        if (customer == null) {
            customer = new Customer(id, "Customer " + id); // Default name
            addCustomer(customer);
        }
        return customer;
    }
}
