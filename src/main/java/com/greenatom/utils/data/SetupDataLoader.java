package com.greenatom.utils.data;

import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.entity.Role;
import com.greenatom.domain.enums.JobPosition;
import com.greenatom.repository.EmployeeRepository;
import com.greenatom.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup){
            return;
        }
        createRoleNotFound("ROLE_ADMIN");
        createRoleNotFound("ROLE_SUPERVISOR");
        createRoleNotFound("ROLE_MANAGER");
        createRoleNotFound("ROLE_SPECIALIST");
        createAdminNotFound();
    }

    @Transactional
    public void createRoleNotFound(String name){
        Optional<Role> role = roleRepository.findByName(name);
        if (role.isEmpty()){
            Role roleToSave = new Role();
            roleToSave.setName(name);
            roleRepository.save(roleToSave);
        }
    }

    @Transactional
    public void createAdminNotFound(){
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN").get();
        List<Optional<Employee>> admins = employeeRepository.findAllByRole(roleAdmin);
        if(admins.isEmpty()){
            Employee employeeToSave = new Employee();
            employeeToSave.setRole(roleAdmin);
            employeeToSave.setName("Admin");
            employeeToSave.setSurname("Admin");
            employeeToSave.setEmail("-");
            employeeToSave.setSalary(0);
            employeeToSave.setPatronymic("Admin");
            employeeToSave.setJobPosition(JobPosition.ADMIN);
            employeeToSave.setPhoneNumber("0");
            employeeToSave.setUsername("Admin_A_A_" + (employeeRepository.count() + 1));
            employeeToSave.setPassword(encoder.encode("admin"));
            employeeRepository.save(employeeToSave);
        }
    }
}
