package ba.unsa.etf.rpr.p1;

import java.util.*;
import java.util.function.Function;

public class Fabrika {
    private Set<Masina> masine = new TreeSet<>();
    public Map<Masina, String> najviseProizvoda(){
        Map<Masina,String> najviseProizvodi = new TreeMap<>();
        for(Masina m : masine){
            for(HashMap.Entry<String,Integer> entry : m.getMaterijali().entrySet()){
                if(m.getSati()/entry.getValue()>0) najviseProizvodi.put(m,entry.getKey());
                break;
            }
        }
        return najviseProizvodi;
    }
    public Masina dodajDomacuMasinu(String naziv, int serijski){
        Masina nova = new DomacaMasina(naziv,serijski,0);
        masine.add(nova);
        return nova;
    }
    public Masina dodajKupljenuMasinu(String naziv, int serijski){
        Masina nova = new Masina(naziv,serijski,0);
        masine.add(nova);
        return nova;
    }
    public void dodajMaterijal(String nazivMasine, String nazivMaterijala, int cijena){
        for(Masina m : masine){
            if(m.getNaziv().equals(nazivMasine)){
                m.registrujMaterijal(nazivMaterijala,cijena);
            }
        }
    }
    public Map<Masina, Integer> cijenaZaMaterijal(String naziv){
        Map<Masina,Integer> cijene = new TreeMap<>();
        boolean sadrzi = false;
        for(Masina m : masine){
            if(m.getSati()>0){
                sadrzi=false;
                for(HashMap.Entry<String,Integer> entry : m.getMaterijali().entrySet()){
                    if(entry.getKey().equals(naziv)){
                        cijene.put(m,entry.getValue());
                        sadrzi=true;
                    }
                }
                if(!sadrzi){
                    cijene.put(m,-1);
                }
            }
        }
        return cijene;
    }
    public Set<Masina> dajMasine(Function<Masina,Boolean> filter){
        Set<Masina> upotrebljivost = new TreeSet<>();
        if(filter == null){
            for(Masina m : masine){
                upotrebljivost.add(m);
            }
        }
        else{
            for(Masina m : masine){
                if(filter.apply(m)) upotrebljivost.add(m);
            }
        }
        return upotrebljivost;
    }

    @Override
    public String toString(){
        String ispis="";
        int brojac=1;
        for(Masina m : masine){
            ispis+=brojac+". "+m.toString()+"\n";
            brojac++;
        }
        return ispis;
    }
}
