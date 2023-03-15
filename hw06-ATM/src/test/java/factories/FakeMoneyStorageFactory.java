package factories;

import ru.est0y.ATM_app.money_storage.api.MoneyStorage;
import ru.est0y.ATM_app.wad_of_money.api.WadOfMoney;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FakeMoneyStorageFactory {
    public static MoneyStorage makeFakeStorageMoney(List<WadOfMoney> list) {
        MoneyStorage mock = mock(MoneyStorage.class);
        // WadOfMoney wadOfMoney=makeFakeWadOfMoney(300, Map.of(100,3));
        int sum=list.get(0).getSum();
        //var whenGetBanknotes=when(mock.addBanknote()
        var when = when(mock.takeMaxPossibleMoneyByAmount(anyInt())).thenReturn(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            when = when.thenReturn(list.get(i));
            sum+=list.get(i).getSum();
        }
        when(mock.getSum()).thenReturn(sum);
        return mock;
    }
}
