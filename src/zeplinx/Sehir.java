package zeplinx;

import java.util.*;

public class Sehir {

    private int plakaNumarasi;
    private String isim;
    private Koordinat koordinat;
    private int rakim;
    private double uzaklik = Double.MAX_VALUE;

    private List<Sehir> enkisaYol = new LinkedList<>();
    private Set<Sehir> komsular =  new HashSet<>();

    private Map<Sehir, Integer> gidebildigiSehirler = new HashMap<>();

    //Mesafe hipotenus uzakligidir.
    public void gercekKomsuEkle(Sehir komsu, Integer mesafe) {
        gidebildigiSehirler.put(komsu, mesafe);
    }
    
    public void gidebilecegiSehirleriTemizle(){
        gidebildigiSehirler.clear();
    }

    public void komsuEkle(Sehir komsu) {
        komsular.add(komsu);
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public List<Sehir> getEnkisaYol() {
        return enkisaYol;
    }

    public void setEnkisaYol(List<Sehir> enkisaYol) {
        this.enkisaYol = enkisaYol;
    }

    public Map<Sehir, Integer> getGidebildigiSehirler() {
        return gidebildigiSehirler;
    }

    public void setGidebildigiSehirler(Map<Sehir, Integer> gidebildigiSehirler) {
        this.gidebildigiSehirler = gidebildigiSehirler;
    }

    public int getPlakaNumarasi() {
        return plakaNumarasi;
    }

    public void setPlakaNumarasi(int plakaNumarasi) {
        this.plakaNumarasi = plakaNumarasi;
    }

    public Koordinat getKoordinat() {
        return koordinat;
    }

    public void setKoordinat(Koordinat koordinat) {
        this.koordinat = koordinat;
    }

    public int getRakim() {
        return rakim;
    }

    public void setRakim(int rakim) {
        this.rakim = rakim;
    }

    public double getUzaklik() {
        return uzaklik;
    }

    public void setUzaklik(double uzaklik) {
        this.uzaklik = uzaklik;
    }

    public Set<Sehir> getKomsular() {
        return komsular;
    }

    public void setKomsular(Set<Sehir> komsular) {
        this.komsular = komsular;
    }

    public Sehir() {
    }
    
    
    
   
    
}
