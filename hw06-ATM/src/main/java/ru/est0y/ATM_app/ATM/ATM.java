package ru.est0y.ATM_app.ATM;

import ru.est0y.ATM_app.banknotes.Banknote;

import java.util.List;

public interface ATM {
    int getSum();

    List<Banknote> getBanknotesByAmount(int amount);

    void addBanknote(Banknote banknote);

    void addBanknotes(List<Banknote> banknotes);
}
