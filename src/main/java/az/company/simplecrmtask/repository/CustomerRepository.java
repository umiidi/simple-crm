package az.company.simplecrmtask.repository;

import az.company.simplecrmtask.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
