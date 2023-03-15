package ru.est0y.ATM_app.money_storage.api;

import ru.est0y.ATM_app.banknotes.Banknote;

public interface MoneyReceiver {
    void addBanknote(Banknote banknote);
}
