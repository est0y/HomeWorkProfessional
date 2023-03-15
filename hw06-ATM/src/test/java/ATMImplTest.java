import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.est0y.ATM_app.ATM.ATM;
import ru.est0y.ATM_app.ATM.ATMImpl;
import ru.est0y.ATM_app.banknotes.BanknoteImpl;
import ru.est0y.ATM_app.money_storage.MoneyCells;
import ru.est0y.ATM_app.money_storage.api.MoneyStorage;
import ru.est0y.ATM_app.wad_of_money.Wad;
import ru.est0y.ATM_app.wad_of_money.api.WadOfMoneyFactory;

import java.util.TreeMap;

import static factories.SingleDenominationCellFactory.createSingleDenominationCell;

class ATMImplTest {
    @Test
    void getBanknotesByAmount() {
        TreeMap<Integer, MoneyStorage> map=new TreeMap<>();
        WadOfMoneyFactory factory=new Wad.Factory();
        map.put(100,createSingleDenominationCell(factory,100,3));
        map.put(200,createSingleDenominationCell(factory,200,3));
        map.put(1000,createSingleDenominationCell(factory,1000,3));
        ATM atm=new ATMImpl(new MoneyCells(map));
        Assertions.assertEquals(6,atm.getBanknotesByAmount(3500).size());
        Assertions.assertEquals(400,atm.getSum());
        Assertions.assertThrows(IllegalArgumentException.class,()->atm.getBanknotesByAmount(401));
    }
    @Test
   void getSum(){
       TreeMap<Integer, MoneyStorage> map=new TreeMap<>();
       WadOfMoneyFactory factory=new Wad.Factory();
       map.put(100,createSingleDenominationCell(factory,100,10));
       map.put(200,createSingleDenominationCell(factory,200,5));
       map.put(1000,createSingleDenominationCell(factory,1000,2));
       ATM atm=new ATMImpl(new MoneyCells(map));
       Assertions.assertEquals(4000,atm.getSum());
   }
   @Test
   void addBanknote(){
       TreeMap<Integer, MoneyStorage> map=new TreeMap<>();
       WadOfMoneyFactory factory=new Wad.Factory();
       map.put(100,createSingleDenominationCell(factory,100,1));
       map.put(200,createSingleDenominationCell(factory,200,1));
       ATM atm=new ATMImpl(new MoneyCells(map));
       atm.addBanknote(new BanknoteImpl(100,3));
       Assertions.assertEquals(400,atm.getSum());
       Assertions.assertThrows(IllegalArgumentException.class, () -> atm.addBanknote(new BanknoteImpl(500,1)));
   }
}