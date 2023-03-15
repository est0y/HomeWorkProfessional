package ru.est0y.ATM_app.wad_of_money.api;

import ru.est0y.ATM_app.banknotes.Banknote;

import java.util.List;

public interface WadOfMoneyFactory {
    WadOfMoney createInstance(List<Banknote> banknotes);
}
