package az.company.simplecrmtask.mapper;

import az.company.simplecrmtask.models.entities.Customer;
import az.company.simplecrmtask.models.request.CustomerRequest;
import az.company.simplecrmtask.models.response.CustomerResponse;

public enum CustomerMapper {

    CUSTOMER_MAPPER;

    public Customer mapToCustomer(CustomerRequest request) {
        return Customer.builder()

                .name(request.getName())
                .companyName(request.getCompanyName())
                .phone(request.getPhone())
                .build();
    }

    public CustomerResponse mapToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .companyName(customer.getCompanyName())
                .phone(customer.getPhone())
                .build();
    }

}
