package homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    Customer c1 = new Customer(1, "Ivan", 100);
    Customer c2 = new Customer(2, "Ivan2", 22);
    Customer c3 = new Customer(3, "Ivan3", 56);

    @Test
    void getNext() {
        CustomerService customerService = new CustomerService();
        customerService.add(c1, "1");
        customerService.add(c2, "2");
        customerService.add(c3, "3");
        Customer customer = customerService.getNext(new Customer(5, "", 59)).getKey();
        Assertions.assertEquals(c1.equals(customer), true);
    }

}