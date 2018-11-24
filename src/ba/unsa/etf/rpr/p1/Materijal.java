package ba.unsa.etf.rpr.p1;

public class Materijal {
    private String nazivMaterijala;
    private int cijena;
    Materijal(String naziv, int vrijeme) {
        nazivMaterijala = naziv;
        cijena = vrijeme;
    }
    public int getCijena() {
        return cijena;
    }

    public String getNazivMaterijala() {
        return nazivMaterijala;
    }
}
