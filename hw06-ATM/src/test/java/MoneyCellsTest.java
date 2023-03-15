import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.est0y.ATM_app.money_storage.MoneyCells;
import ru.est0y.ATM_app.money_storage.api.MoneyStorage;
import ru.est0y.ATM_app.wad_of_money.Wad;
import ru.est0y.ATM_app.wad_of_money.api.WadOfMoneyFactory;

import java.util.TreeMap;

import static factories.SingleDenominationCellFactory.createSingleDenominationCell;

class MoneyCellsTest {

    @Test
    void takeMaxPossibleMoneyStorageByAmount() {
        TreeMap<Integer, MoneyStorage> map=new TreeMap<>();
        WadOfMoneyFactory factory=new Wad.Factory();
        map.put(100,createSingleDenominationCell(factory,100,3));
        map.put(200,createSingleDenominationCell(factory,200,3));
        var cells=new MoneyCells(map);
        var wad=cells.takeMaxPossibleMoneyByAmount(700);
        Assertions.assertEquals(700,wad.getSum());
        Assertions.assertEquals(200,cells.getSum());
    }
    @Test
    void takeMaxPossibleMoneyStorageByAmountWithEmptyCells() {
        TreeMap<Integer, MoneyStorage> map=new TreeMap<>();
        WadOfMoneyFactory factory=new Wad.Factory();
        map.put(100,createSingleDenominationCell(factory,100,3));
        map.put(200,createSingleDenominationCell(factory,200,0));
        map.put(500,createSingleDenominationCell(factory,500,2));
        map.put(1000,createSingleDenominationCell(factory,1000,0));
        var cells=new MoneyCells(map);
        var wad=cells.takeMaxPossibleMoneyByAmount(1200);
        Assertions.assertEquals(1200,wad.getSum());
        Assertions.assertEquals(100,cells.getSum());
    }
}