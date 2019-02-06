package ba.unsa.etf.rpr.p1;

import java.util.*;



public class Masina implements Comparable<Masina>{
    private String naziv;
    private int serijskiBroj;
    private int sati=0;
    private Map<String,Integer> materijali = new HashMap<>();

    public Masina(String naziv, int serijskiBroj, int sati) {
        if(naziv.length()<2 || !naziv.matches("[a-zA-Z]+") || serijskiBroj<=0 || serijskiBroj>=100000)
            throw new IllegalArgumentException("ne valja");
        this.naziv = naziv;
        this.serijskiBroj = serijskiBroj;
        this.sati = sati;
    }

    public int getSerijski(){
        return serijskiBroj;
    }
    public void upali() throws WrongMachineState{
        if(sati>0) throw new WrongMachineState("Upaljena masina");
        sati=8;
    }
    public void ugasi()throws WrongMachineState{
        if(sati==0) throw new WrongMachineState("Ugasena masina");
        sati=0;
    }
    public void resetuj() throws WrongMachineState{
        if(sati==0) throw new WrongMachineState("Ugasena masina");
        sati=8;
    }
    public int cijena(String materijal){
        for(HashMap.Entry<String,Integer> entry : materijali.entrySet()){
            if(entry.getKey().equals(materijal)) return entry.getValue();
        }
        throw new IllegalArgumentException("Materijal se ne moze proizvesti");
    }
    public int proizvedi(String materijal){
        for(HashMap.Entry<String,Integer> entry : materijali.entrySet()){
            if(entry.getKey().equals(materijal)) {
                if(entry.getValue()<=sati){
                    sati-=entry.getValue();
                    return entry.getValue();
                }
            }
        }
        throw new IllegalArgumentException("Materijal se ne moze proizvesti");
    }
    public int preostaloSati(){
        return sati;
    }
    public void registrujMaterijal(String naziv, int cijena){
        if(cijena<1 || cijena>5) throw new IllegalArgumentException("ne valja cijena");
        if(!materijali.containsKey(naziv))
        materijali.put(naziv,cijena);
    }
    public Set<String> dajMaterijaleMoguceZaProizvesti(){
        Set<String> mozeProizvesti = new TreeSet<>();
        for(HashMap.Entry<String,Integer> entry : materijali.entrySet()){
            if(entry.getValue()<=sati) mozeProizvesti.add(entry.getKey());
        }
        return mozeProizvesti;
    }
    public Map<String, Integer> dajMogucnostProizvodnje(){
        Map<String, Integer> mogucnostProizvodnje = new HashMap<>();
        for(HashMap.Entry<String,Integer> entry : materijali.entrySet()){
            if(sati/entry.getValue()>0) mogucnostProizvodnje.put(entry.getKey(),sati/entry.getValue());
        }
        return mogucnostProizvodnje;
    }
    @Override
    public int compareTo(Masina m){
        if(dajMaterijaleMoguceZaProizvesti().size()>m.dajMaterijaleMoguceZaProizvesti().size()) return 1;
        if(dajMaterijaleMoguceZaProizvesti().size()<m.dajMaterijaleMoguceZaProizvesti().size()) return -1;
        return naziv.compareTo(m.naziv);
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getSerijskiBroj() {
        return serijskiBroj;
    }

    public void setSerijskiBroj(int serijskiBroj) {
        this.serijskiBroj = serijskiBroj;
    }

    public int getSati() {
        return sati;
    }

    public void setSati(int sati) {
        this.sati = sati;
    }

    public Map<String, Integer> getMaterijali() {
        return materijali;
    }

    public void setMaterijali(Map<String, Integer> materijali) {
        this.materijali = materijali;
    }
    @Override
    public boolean equals(Object o){
        Masina m = (Masina) o;
        return (m.naziv.equals(naziv) && m.serijskiBroj==serijskiBroj && m.sati==sati);
    }
    @Override
    public String toString(){
        String ispis;
        if(sati>0){
            ispis = "Masina "+naziv+" je upaljena (preostalo "+preostaloSati()+" sati). ";
        }
        else{
            ispis = "Masina "+naziv+" je ugasena. ";
        }
        ispis+="Ona moze proizvesti materijale ";
        int brojac=1;
        for(HashMap.Entry<String,Integer> entry : materijali.entrySet()){
            if(brojac<materijali.size()) {
                ispis += entry.getKey() + " (" + entry.getValue() + "), ";
                brojac++;
            }
            else ispis += entry.getKey() + " (" + entry.getValue() + ").";
        }
        return ispis;
    }
}
