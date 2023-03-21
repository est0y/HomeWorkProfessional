package factories;

import ru.est0y.ATM_app.banknotes.BanknoteImpl;
import ru.est0y.ATM_app.money_storage.SingleDenominationCell;
import ru.est0y.ATM_app.money_storage.api.MoneyStorage;
import ru.est0y.ATM_app.wad_of_money.api.WadOfMoneyFactory;

public class SingleDenominationCellFactory {
    public static SingleDenominationCell createSingleDenominationCell(WadOfMoneyFactory factory, int denomination, int count){
        var cell=new SingleDenominationCell(denomination,factory);
        addBanknotes(cell,denomination,count);
        return cell;
    }
    private static void addBanknotes(MoneyStorage moneyStorage, int denomination, int count){
        var factory=new BanknoteImpl.Factory();
        factory.Banknotes(denomination,count).forEach(moneyStorage::addBanknote);
    }
}
