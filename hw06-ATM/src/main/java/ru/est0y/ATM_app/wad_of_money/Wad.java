package ru.est0y.ATM_app.wad_of_money;

import ru.est0y.ATM_app.banknotes.Banknote;
import ru.est0y.ATM_app.wad_of_money.api.WadOfMoney;
import ru.est0y.ATM_app.wad_of_money.api.WadOfMoneyFactory;

import java.util.ArrayList;
import java.util.List;

public class Wad implements WadOfMoney {
    private int sum;
    private final List<Banknote> banknotes;

    public static class Factory implements WadOfMoneyFactory {
        @Override
        public WadOfMoney createInstance(List<Banknote> banknotes) {
            return new Wad(banknotes);
        }
    }

    public Wad(List<Banknote> banknotes) {
        if (banknotes.size() == 0) this.sum = 0;
        else {
            this.sum = banknotes.size() * banknotes.get(0).getDenomination();
        }
        this.banknotes = banknotes;
    }

    public Wad() {
        this.sum = 0;
        this.banknotes = new ArrayList<>();
    }

    @Override
    public int getSum() {
        return sum;
    }

    @Override
    public WadOfMoney merge(WadOfMoney wadOfMoney) {
        sum = sum + wadOfMoney.getSum();
        banknotes.addAll(wadOfMoney.getBanknotes());
        return this;
    }

    @Override
    public List<Banknote> getBanknotes() {
        return banknotes;
    }

}
