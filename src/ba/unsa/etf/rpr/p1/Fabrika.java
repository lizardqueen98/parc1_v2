package ba.unsa.etf.rpr.p1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Fabrika {
    Set<Masina> masine; //zbog duplikata
    Set<Materijal> materijali;
    Fabrika() {
        masine = new TreeSet<Masina>();
        materijali = new TreeSet<>(Materijal);
    }
 //   Map<Masina, String> najviseProizvoda();
}
