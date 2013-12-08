package sample.customer.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import sample.customer.biz.domain.Customer;
import sample.customer.biz.service.CustomerService;
import sample.customer.biz.service.DataNotFoundException;

@Controller
@RequestMapping("/api/customer")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String register(@RequestBody Customer customer) {
        customerService.register(customer);
        return "OK";
    }

//    @RequestMapping(value = "/{customerId}", method = GET)
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public Customer findById(@PathVariable int customerId) 
//                                        throws DataNotFoundException {
//        return customerService.findById(customerId);
//    }
    
    @RequestMapping(value = "/{customerId}", method = GET)
    public ResponseEntity<Customer> findById(@PathVariable int customerId) 
                                        throws DataNotFoundException {
        Customer customer = customerService.findById(customerId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
            new MediaType("text", "xml", Charset.forName("UTF-8")));
        headers.set("My-Header", "MyHeaderValue");

        return new ResponseEntity<Customer>(
                customer, headers, HttpStatus.OK);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleException(DataNotFoundException e) {
        return "customer is not found";
    }
}
