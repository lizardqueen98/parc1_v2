package ba.unsa.etf.rpr.p1;

import java.util.*;

public abstract class Masina {
    public static class WrongMachineState extends Exception {
        public WrongMachineState(String greska) {
            super(greska);
        }
    }
    private String nazivMasine;
    private int serijskiBroj;
    private List<Materijal> spisakMaterijalaKojeMozeProizvesti;
    private List<Materijal> proizvedeniMaterijali;
    private boolean aktivna;
    private int radnoVrijeme, maxRadnoVrijeme;
    public Masina() {
     //   nazivMasine = "";
     //   serijskiBroj = 0;
        spisakMaterijalaKojeMozeProizvesti = new ArrayList<Materijal>();
        proizvedeniMaterijali = new ArrayList<Materijal>();
     //   aktivna = false;
     //   radnoVrijeme = 0;
     //   maxRadnoVrijeme = 0;
    }

    int getSerijski() {
        return serijskiBroj;
    }
    void upali() throws WrongMachineState {
        if(aktivna) throw new WrongMachineState("Masina je vec upaljena!");
        aktivna = true;
        maxRadnoVrijeme = 8;
    }
    void ugasi() throws WrongMachineState {
        if(!aktivna) throw new WrongMachineState("Masina je vec ugasena!");
        aktivna = false;
    }

    void resetuj() throws WrongMachineState {
        if(!aktivna) throw new WrongMachineState("Masina je vec ugasena!");
        maxRadnoVrijeme = 8;
    }

    int cijena(String materijal) throws IllegalArgumentException {
        int cijena;
        for(int i = 0; i < spisakMaterijalaKojeMozeProizvesti.size(); i++) {
            if(spisakMaterijalaKojeMozeProizvesti.get(i).getNazivMaterijala().equals(materijal)) {
                return spisakMaterijalaKojeMozeProizvesti.get(i).getCijena();
            }
        }
        throw new IllegalArgumentException("Materijal se ne moze proizvest!");
    }

    int proizvedi(String materijal) throws IllegalArgumentException {
        int vrijeme = 0;
        for(int i = 0; i < spisakMaterijalaKojeMozeProizvesti.size(); i++) {
            if (spisakMaterijalaKojeMozeProizvesti.get(i).getNazivMaterijala().equals(materijal)) {
                proizvedeniMaterijali.add(spisakMaterijalaKojeMozeProizvesti.get(i));
                vrijeme = spisakMaterijalaKojeMozeProizvesti.get(i).getCijena();
                radnoVrijeme += vrijeme;
                break;
            }
            if(i == spisakMaterijalaKojeMozeProizvesti.size())
                throw new IllegalArgumentException("Materijal se ne moze proizvesti");
        }
        if(radnoVrijeme > maxRadnoVrijeme) throw new IllegalArgumentException("Nedovoljno vremena");
        return vrijeme;
    }

    int preostaloSati() {
        if(!aktivna) return 0;
        return maxRadnoVrijeme - radnoVrijeme;
    }

    void  registrujMaterijal(String naziv, int cijena) {
        spisakMaterijalaKojeMozeProizvesti.add(new Materijal(naziv, cijena));
    }

    Set<String> dajMaterijaleMoguceZaProizvesti() {
        SortedSet<String> spisak = new TreeSet<String>();
        for(int i = 0; i < spisakMaterijalaKojeMozeProizvesti.size(); i++)
            if(spisakMaterijalaKojeMozeProizvesti.get(i).getCijena() + radnoVrijeme < maxRadnoVrijeme)
                spisak.add(spisakMaterijalaKojeMozeProizvesti.get(i).getNazivMaterijala());
        return spisak;
    }
    Map<String, Integer> dajMogucnostProizvodnje() {
        int josKolikoPuta, temp = radnoVrijeme;
        Map<String, Integer> mapa = new HashMap<String, Integer>();
        for(int i = 0; i < spisakMaterijalaKojeMozeProizvesti.size(); i++) {
            josKolikoPuta = 0;
            while(temp < radnoVrijeme) {
                temp += spisakMaterijalaKojeMozeProizvesti.get(i).getCijena();
                josKolikoPuta++;
            }
            mapa.put(spisakMaterijalaKojeMozeProizvesti.get(i).getNazivMaterijala(), josKolikoPuta);
        }
        return mapa;
    }
}
