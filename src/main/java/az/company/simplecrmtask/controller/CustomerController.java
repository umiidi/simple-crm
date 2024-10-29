package az.company.simplecrmtask.controller;

import az.company.simplecrmtask.models.request.CustomerRequest;
import az.company.simplecrmtask.models.response.CustomerResponse;
import az.company.simplecrmtask.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> getCustomers() {
        return customerService.getAllCostumers();
    }

    @PostMapping
    public CustomerResponse addCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return customerService.addCostumer(customerRequest);
    }

}
