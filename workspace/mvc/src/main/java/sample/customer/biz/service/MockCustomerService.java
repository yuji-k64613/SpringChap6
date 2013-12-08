package sample.customer.biz.service;

import sample.customer.biz.domain.Customer;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("customerService")
public class MockCustomerService implements CustomerService {
    // サンプル用のMock実装であるため、
    // synchronized等の同期処理は一切おこなわない。
    private Map<Integer, Customer> customerMap
                            = new LinkedHashMap<Integer, Customer>();
    
    private int nextId = 1;

    private boolean isExists(int id) {
        return customerMap.containsKey(id);
    }
    
    public List<Customer> findAll() {
        return new ArrayList<Customer>(customerMap.values());
    }

    public Customer findById(int id) throws DataNotFoundException {
        if (!isExists(id)) {
            throw new DataNotFoundException();
        }
        return customerMap.get(id);
    }

    public Customer register(Customer customer) {
        customer.setId(nextId++);
        customerMap.put(customer.getId(), customer);

        return customer;
    }

    public void update(Customer customer) throws DataNotFoundException {
        if (!isExists(customer.getId())) {
            throw new DataNotFoundException();
        }
        customerMap.put(customer.getId(), customer);
    }

    public void delete(int id) throws DataNotFoundException {
        if (!isExists(id)) {
            throw new DataNotFoundException();
        }
        customerMap.remove(id);
    }

    public boolean isFreeEmailCustomer(Customer customer) {
        // この実装では、
        // Customer#isFreeEmailを呼び出してその結果を返すだけ
        return customer.isFreeEmail();
    }

    @PostConstruct
    public void initCustomer() {
        nextId = 1;

        register(new Customer("太郎", "東京都新宿区", "taro@aa.bb.cc"));
        register(new Customer("次郎", "東京都豊島区", "jiro@aa.bb.cc"));
        register(new Customer("三郎", "東京都板橋区", "sabu@aa.bb.cc"));
    }
}
