package homework;


import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(o -> o.getScores()));

    public Map.Entry<Customer, String> getSmallest() {
        return entryClone(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return entryClone(map.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> entryClone(Map.Entry<Customer, String> entry) {
        if (entry == null) {
            return null;
        }
        return Map.entry(entry.getKey().clone(), entry.getValue());
    }
}
