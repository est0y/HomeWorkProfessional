package ru.est0y.ATM_app.banknotes;

import java.util.ArrayList;
import java.util.List;

public class BanknoteImpl implements Banknote {
    private final int denomination;
    private final long id;
    public static class Factory{
        public List<Banknote>Banknotes(int denomination,int count){
            var list=new ArrayList<Banknote>();
            for (int i=0;i<count;i++){
                list.add(new BanknoteImpl(denomination,i));
            }
            return list;
        }
    }

    public BanknoteImpl(int denomination, long id) {
        this.denomination = denomination;
        this.id = id;
    }
@Override
    public int getDenomination() {
        return this.denomination;
    }

    @Override
    public long getId() {
        return getId();
    }

}
