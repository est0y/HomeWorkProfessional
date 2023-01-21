package homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MyCustomerTest {
    Customer ivan = new Customer(1, "Ivan", 10);
    Customer ivan2 = new Customer(1, "Ivan2", 22);

    @Test
    void testEquals() {
        Assertions.assertEquals(true, ivan.equals(ivan2));
    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(ivan.hashCode(), ivan2.hashCode());
    }
}