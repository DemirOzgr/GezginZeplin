package zeplinx;

import java.util.*;

public class DjikstraAlgorithm {


    public static Graph enKisaYoluHesapla(Graph graph, Sehir baslangicSehir) {
        baslangicSehir.setUzaklik(Double.valueOf(0));

        Set<Sehir> ziyaretEdilenSehirler = new HashSet<>();
        Set<Sehir> ziyaretEdilmeyenSehirler = new HashSet<>();

        ziyaretEdilmeyenSehirler.add(baslangicSehir);

        while (!ziyaretEdilmeyenSehirler.isEmpty()) {
            Sehir suankiSehir = enYakinSehiriGetir(ziyaretEdilmeyenSehirler);

            ziyaretEdilmeyenSehirler.remove(suankiSehir);

            for (Map.Entry<Sehir, Integer> sehirIntegerEntry : suankiSehir.getGidebildigiSehirler().entrySet()) {
                Sehir komsusu = sehirIntegerEntry.getKey();
                Integer komsuUzakligi = sehirIntegerEntry.getValue();

                if (!ziyaretEdilenSehirler.contains(komsusu)) {
                    enKisaYoluHesapla(komsusu, komsuUzakligi, suankiSehir);
                    ziyaretEdilmeyenSehirler.add(komsusu);
                }
            }
            ziyaretEdilenSehirler.add(suankiSehir);
        }
        return graph;
    }

    public static Sehir enYakinSehiriGetir(Set<Sehir> ziyaretEdilmeyenSehirler) {
        Sehir enYakinSehir = null;

        double enKisaMesafe = Double.MAX_VALUE;

        for (Sehir sehir : ziyaretEdilmeyenSehirler) {
            double sehirUzakligi = sehir.getUzaklik();
            if (sehirUzakligi < enKisaMesafe) {
                enYakinSehir = sehir;
                enKisaMesafe = sehirUzakligi;
            }
        }
        return enYakinSehir;
    }

    public static void enKisaYoluHesapla(Sehir hesaplamayaGirenSehir, Integer komsulukUzakligi, Sehir kaynakSehir) {
        double kaynakUzaklik = kaynakSehir.getUzaklik();

        if ((kaynakUzaklik + komsulukUzakligi) < hesaplamayaGirenSehir.getUzaklik()) {
            hesaplamayaGirenSehir.setUzaklik(kaynakUzaklik + komsulukUzakligi);
            List<Sehir> enKisaYol = new LinkedList<>(kaynakSehir.getEnkisaYol());
            enKisaYol.add(kaynakSehir);
            hesaplamayaGirenSehir.setEnkisaYol(enKisaYol);
        }
    }


    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        return distance;
    }


}



