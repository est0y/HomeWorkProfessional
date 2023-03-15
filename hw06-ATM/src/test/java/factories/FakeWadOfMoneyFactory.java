package factories;

import ru.est0y.ATM_app.banknotes.Banknote;
import ru.est0y.ATM_app.wad_of_money.api.WadOfMoney;

import java.util.*;

import static org.mockito.Mockito.*;

public class FakeWadOfMoneyFactory  {
    public static WadOfMoney makeFakeWadOfMoney(int sum, Map<Integer,Integer> denominations){
        WadOfMoney mock=mock(WadOfMoney.class);
       List<Banknote> banknotes= denominations.keySet().stream()
               .map(den-> FakeBanknoteFactory.makeFakeBanknotes(den,denominations.get(den)))
               .flatMap(Collection::stream).toList();
        when(mock.getBanknotes()).thenReturn(banknotes);
        when(mock.getSum()).thenReturn(sum);
        return mock;
    }
}
