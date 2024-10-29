package az.company.simplecrmtask.service.customer;

import az.company.simplecrmtask.models.request.CustomerRequest;
import az.company.simplecrmtask.models.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    List<CustomerResponse> getAllCostumers();

    CustomerResponse addCostumer(CustomerRequest customerRequest);

}
