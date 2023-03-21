package unit;

import ru.est0y.ATM_app.banknotes.Banknote;
import ru.est0y.ATM_app.wad_of_money.api.WadOfMoneyFactory;
import ru.est0y.ATM_app.money_storage.SingleDenominationCell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static factories.FakeBanknoteFactory.makeFakeBanknotes;

class SingleDenominationCellTest {

    @Test
    void getMaxMoneyWadByAmount() {
        var factory = mock(WadOfMoneyFactory.class);
        var banknoteList = makeFakeBanknotes(100, 5);
        var cell = new SingleDenominationCell(100, factory, banknoteList);
        Assertions.assertEquals(500, cell.getSum());
        cell.takeMaxPossibleMoneyByAmount(205);
        Assertions.assertEquals(300, cell.getSum());
        cell.takeMaxPossibleMoneyByAmount(1000);
        Assertions.assertEquals(0, cell.getSum());
        ArgumentCaptor<List<Banknote>> argument = ArgumentCaptor.forClass(List.class);
        verify(factory, Mockito.times(2)).createInstance(argument.capture());
        var arguments = argument.getAllValues();
        assertEquals(2, arguments.get(0).size());
        assertEquals(3, arguments.get(1).size());
    }
}