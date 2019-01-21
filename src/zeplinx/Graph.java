package zeplinx;

import java.util.HashSet;
import java.util.Set;

public class Graph {

    private Set<Sehir> sehirler = new HashSet<>();

    public void sehirEkle(Sehir sehir) {
        sehirler.add(sehir);
    }

    public Set<Sehir> getSehirler() {
        return sehirler;
    }
}
