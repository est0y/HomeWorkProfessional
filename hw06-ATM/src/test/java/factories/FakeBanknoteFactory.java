package factories;

import ru.est0y.ATM_app.banknotes.Banknote;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class FakeBanknoteFactory {
    public static List<Banknote>makeFakeBanknotes(int denomination, int count){
        var list=new ArrayList<Banknote>();
        for (int i=0;i<count;i++){
            var banknote= Mockito.mock(Banknote.class);
            Mockito.when(banknote.getDenomination()).thenReturn(denomination);
            Mockito.when(banknote.getId()).thenReturn((long) i);
            Mockito.when(banknote.toString()).thenReturn("Denomination:"+denomination+",id:"+ i);
            list.add(banknote);
        }
        return list;
    }
}
