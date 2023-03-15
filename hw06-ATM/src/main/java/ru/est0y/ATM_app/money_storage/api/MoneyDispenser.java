package ru.est0y.ATM_app.money_storage.api;

import ru.est0y.ATM_app.wad_of_money.api.WadOfMoney;

public interface MoneyDispenser {
    WadOfMoney takeMaxPossibleMoneyByAmount(int amount);
}
