package com.joseneyra.brewery.web.controllers;

import com.joseneyra.brewery.domain.Customer;
import com.joseneyra.brewery.repositories.CustomerRepository;
import com.joseneyra.brewery.security.perms.customer.CustomerCreatePermission;
import com.joseneyra.brewery.security.perms.customer.CustomerReadPermission;
import com.joseneyra.brewery.security.perms.customer.CustomerUpdatePermission;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/customers")
@Controller
public class CustomerController {

    //ToDO: Add service
    private final CustomerRepository customerRepository;


    @CustomerReadPermission
    @RequestMapping("/find")
    public String findCustomers(Model model){
        model.addAttribute("customer", Customer.builder().build());
        return "customers/findCustomers";
    }

//    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER"})             // Replaced by PreAuthorize
//    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")      // Replaced by Authority method
    @CustomerReadPermission
    @GetMapping
    public String processFindFormReturnMany(Customer customer, BindingResult result, Model model){
        // find customers by name
        //ToDO: Add Service
        List<Customer> customers = customerRepository.findAllByCustomerNameLike("%" + customer.getCustomerName() + "%");
        if (customers.isEmpty()) {
            // no customers found
            result.rejectValue("customerName", "notFound", "not found");
            return "customers/findCustomers";
        } else if (customers.size() == 1) {
            // 1 customer found
            customer = customers.get(0);
            return "redirect:/customers/" + customer.getId();
        } else {
            // multiple customers found
            model.addAttribute("selections", customers);
            return "customers/customerList";
        }
    }

    @CustomerReadPermission
    @GetMapping("/{customerId}")
    public ModelAndView showCustomer(@PathVariable UUID customerId) {
        ModelAndView mav = new ModelAndView("customers/customerDetails");
        //ToDO: Add Service
        mav.addObject(customerRepository.findById(customerId).get());
        return mav;
    }

    @CustomerCreatePermission
    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("customer", Customer.builder().build());
        return "customers/createCustomer";
    }


//    @PreAuthorize("hasRole('ADMIN')")
    @CustomerCreatePermission
    @PostMapping("/new")
    public String processCreationForm(Customer customer) {
        //ToDO: Add Service
        Customer newCustomer = Customer.builder()
                .customerName(customer.getCustomerName())
                .build();

        Customer savedCustomer= customerRepository.save(newCustomer);
        return "redirect:/customers/" + savedCustomer.getId();
    }


    @CustomerUpdatePermission
    @GetMapping("/{customerId}/edit")
    public String initUpdateCustomerForm(@PathVariable UUID customerId, Model model) {
        if(customerRepository.findById(customerId).isPresent())
            model.addAttribute("customer", customerRepository.findById(customerId).get());
        return "customers/createOrUpdateCustomer";
    }


    @CustomerUpdatePermission
    @PostMapping("/{beerId}/edit")
    public String processUpdationForm(@Valid Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return "beers/createOrUpdateCustomer";
        } else {
            //ToDO: Add Service
            Customer savedCustomer =  customerRepository.save(customer);
            return "redirect:/customers/" + savedCustomer.getId();
        }
    }

}
