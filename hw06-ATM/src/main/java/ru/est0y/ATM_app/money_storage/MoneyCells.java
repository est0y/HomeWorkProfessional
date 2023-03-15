package ru.est0y.ATM_app.money_storage;

import ru.est0y.ATM_app.banknotes.Banknote;
import ru.est0y.ATM_app.money_storage.api.MoneyStorage;
import ru.est0y.ATM_app.wad_of_money.api.WadOfMoney;

import java.util.*;

public class MoneyCells implements MoneyStorage {
    private final TreeMap<Integer, MoneyStorage> cells;

    public MoneyCells(TreeMap<Integer, MoneyStorage> cells) {
        this.cells = cells;
    }

    @Override
    public int getSum() {
        return cells.values().stream().mapToInt(MoneyStorage::getSum).sum();
    }

    @Override
    public void addBanknote(Banknote banknote) {
        MoneyStorage singleDenominationCell = cells.get(banknote.getDenomination());
        if (singleDenominationCell == null) {
            throw new IllegalArgumentException("This denomination:"+banknote.getDenomination()+" is not accepted");
        }
        singleDenominationCell.addBanknote(banknote);
    }


    public WadOfMoney takeMaxPossibleMoneyByAmount(int amount) {
        WadOfMoney result = null;
        Map.Entry<Integer, MoneyStorage> entry1 = cells.floorEntry(amount);
            for(var entry=entry1;amount > 0; entry = cells.lowerEntry(entry.getKey())) {
            if (entry==null){
                break;
            }
            var wad = entry.getValue().takeMaxPossibleMoneyByAmount(amount);
            if (wad.getSum() == 0) {
                continue;
            }
            result = result == null ? wad : result.merge(wad);
            amount = amount - wad.getSum();
        }
        return result;
    }
}
