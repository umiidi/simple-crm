package az.company.simplecrmtask.service.customer;

import az.company.simplecrmtask.models.entities.Customer;
import az.company.simplecrmtask.models.request.CustomerRequest;
import az.company.simplecrmtask.models.response.CustomerResponse;
import az.company.simplecrmtask.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.company.simplecrmtask.mapper.CustomerMapper.CUSTOMER_MAPPER;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerResponse> getAllCostumers() {
        return customerRepository.findAll()
                .stream()
                .map(CUSTOMER_MAPPER::mapToCustomerResponse)
                .toList();
    }

    @Override
    public CustomerResponse addCostumer(CustomerRequest customerRequest) {
        Customer customer = CUSTOMER_MAPPER.mapToCustomer(customerRequest);
        customerRepository.save(customer);
        return CUSTOMER_MAPPER.mapToCustomerResponse(customer);
    }

}
