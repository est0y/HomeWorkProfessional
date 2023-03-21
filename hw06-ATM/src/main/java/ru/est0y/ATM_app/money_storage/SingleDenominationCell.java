package ru.est0y.ATM_app.money_storage;

import ru.est0y.ATM_app.banknotes.Banknote;
import ru.est0y.ATM_app.money_storage.api.MoneyStorage;
import ru.est0y.ATM_app.wad_of_money.api.WadOfMoney;
import ru.est0y.ATM_app.wad_of_money.api.WadOfMoneyFactory;

import java.util.*;

public class SingleDenominationCell implements MoneyStorage {

    private final WadOfMoneyFactory factory;
    final ArrayDeque<Banknote> banknotes = new ArrayDeque<>();
    private final int denomination;
    public SingleDenominationCell(int denomination, WadOfMoneyFactory factory) {
        this.factory = factory;
        this.denomination = denomination;
    }
    public SingleDenominationCell(int denomination, WadOfMoneyFactory factory, List<Banknote> banknotes) {
        this.factory = factory;
        this.denomination = denomination;
        this.banknotes.addAll(banknotes);
    }

    @Override
    public int getSum() {
        return banknotes.size() * denomination;
    }

    @Override
    public void addBanknote(Banknote banknote) {
        banknotes.add(banknote);
    }

    @Override
    public WadOfMoney takeMaxPossibleMoneyByAmount(int amount) {
        List<Banknote> necessaryBanknotes = new ArrayList<>();
        while (amount >= denomination) {
            try {
                necessaryBanknotes.add(banknotes.pop());
            } catch (NoSuchElementException e) {
                break;
            }
            amount = amount - denomination;
        }
        return factory.createInstance(necessaryBanknotes);
    }
}
