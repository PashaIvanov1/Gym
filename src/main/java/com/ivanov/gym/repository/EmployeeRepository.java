package com.ivanov.gym.repository;


import com.ivanov.gym.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE Employee e SET e.salary = e.salary + :amount")
    void increaseSalaries(@Param("amount") double amount);

}
