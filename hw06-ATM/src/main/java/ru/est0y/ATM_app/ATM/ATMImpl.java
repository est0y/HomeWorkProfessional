package ru.est0y.ATM_app.ATM;

import ru.est0y.ATM_app.banknotes.Banknote;
import ru.est0y.ATM_app.money_storage.api.MoneyStorage;
import ru.est0y.ATM_app.wad_of_money.api.WadOfMoney;

import java.util.*;

public class ATMImpl implements ATM {

    private final MoneyStorage moneyCells;

    public ATMImpl(MoneyStorage moneyCells) {
        this.moneyCells = moneyCells;
    }

    @Override
    public int getSum() {
        return moneyCells.getSum();
    }

    @Override
    public void addBanknote(Banknote banknote) {
        moneyCells.addBanknote(banknote);
    }

    @Override
    public void addBanknotes(List<Banknote> banknotes) {
        banknotes.forEach(this::addBanknote);
    }

    @Override
    public List<Banknote> getBanknotesByAmount(int amount) {
        WadOfMoney wadOfMoney = moneyCells.takeMaxPossibleMoneyByAmount(amount);
        if (wadOfMoney.getSum() != amount) {
            throw new IllegalArgumentException("Not enough money");
        }
        return wadOfMoney.getBanknotes();
    }

}
