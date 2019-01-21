package zeplinx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Zeplinx {

    public static void main(String[] args) throws Exception {
        Path latLotPath = Paths.get("latlot.txt");
        List<String> latLotDosyaİcerigi = Files.readAllLines(latLotPath);

        Map<Integer, Sehir> sehirMap = new HashMap<>();
        if (latLotDosyaİcerigi != null && !latLotDosyaİcerigi.isEmpty()) {
            for (String satir : latLotDosyaİcerigi) {
                satir = satir.trim();
                if (!satir.isEmpty()) {
                    String[] split = satir.split(",");
                    Sehir sehir = new Sehir();
                    sehir.setKoordinat(new Koordinat(Double.valueOf(split[0]), Double.valueOf(split[1])));
                    sehir.setPlakaNumarasi(Integer.valueOf(split[2]));
                    sehir.setRakim(Integer.valueOf(split[3]));
                    sehir.setIsim(split[4]);

                    sehirMap.put(sehir.getPlakaNumarasi(), sehir);
                }
            }
        }

        Path komsulukPath = Paths.get("komsuluk.txt");
        List<String> komsulukDosyaİcerigi = Files.readAllLines(komsulukPath);

        if (komsulukDosyaİcerigi != null && !komsulukDosyaİcerigi.isEmpty()) {
            for (String satir : komsulukDosyaİcerigi) {
                satir = satir.trim();
                if (!satir.isEmpty()) {
                    String[] split = satir.split(",");
                    Integer key = Integer.valueOf(split[0]);
                    Sehir sehir = sehirMap.get(key);

                    for (int i = 1; i < split.length; i++) {
                        Integer k = Integer.valueOf(split[i]);
                        Sehir komsu = sehirMap.get(k);
                        sehir.komsuEkle(komsu);
                    }
                    sehirMap.replace(key, sehir);
                }
            }
        }
        int[] numara = new int[46];
        int sayac = 0;
        int gecici;
        int[] diziKar = new int[46];
        StringBuilder dosyayaYazilacak = new StringBuilder();
        StringBuilder dosyaYazilacak = new StringBuilder();
        int[] dizi = {10, 20, 30, 40, 50};
        System.out.println("--------------------YUZDE ELLI KAR PROBLEMI-----------------------------");
        Scanner klavye = new Scanner(System.in);
        String baslangicSehri = klavye.next();
        String bitisSehri = klavye.next();
        for (int i = 0; i < dizi.length; i++) {
            ArrayList<Sehir> hesaplanacak = new ArrayList<>(sehirMap.values());
            hesaplanacak.forEach(s -> {
                s.setUzaklik(Double.MAX_VALUE);
                s.getEnkisaYol().clear();
                s.gidebilecegiSehirleriTemizle();
            });
            int yolcuSayisi = dizi[i];
            yolcuSayisinaGoreEnKisaRota(yolcuSayisi, hesaplanacak);

            Graph graph = new Graph();
            for (Sehir sehir : hesaplanacak) {
                graph.sehirEkle(sehir);
            }

            Sehir baslangic = sehirAra(graph, baslangicSehri);
            Sehir bitis = sehirAra(graph, bitisSehri);

            graph = DjikstraAlgorithm.enKisaYoluHesapla(graph, baslangic);

            dosyayaYazilacak.append("\nYOLCU SAYISI: " + yolcuSayisi);
            if (bitis.getEnkisaYol() == null || bitis.getEnkisaYol().isEmpty()) {
                dosyayaYazilacak.append("\n Yol Yok !! \n");
                System.out.println("Yol yok");
            } else {
                StringBuilder yol = new StringBuilder();
                for (Sehir sehir : bitis.getEnkisaYol()) {
                    yol.append(sehir.getIsim() + "(" + sehir.getUzaklik() + ")" + " > ");
                    dosyayaYazilacak.append("\n" + sehir.getIsim());
                    dosyayaYazilacak.append("\n");
                    dosyayaYazilacak.append("Enlem :  " + sehir.getKoordinat().getEnlem() + "\t" + "Boylam  :  " + sehir.getKoordinat().getBoylam() + "\t" + "Uzaklik  :   " + sehir.getUzaklik());

                }
                yol.append(bitis.getIsim() + "(" + bitis.getUzaklik() + ")");
                System.out.println(yolcuSayisi + "  Yolcu Sayisi Icin  " + yol);
            }
            int istenen = ((int) bitis.getUzaklik() * 20) / dizi[i];
            System.out.println("Yuzde Elli Kar Icin Alinmasi Gereken Para  " + istenen);

        }
        System.out.println("--------------------SABIT UCRET PROBLEMI-----------------------------");
        String baslangicSehri2 = klavye.next();
        String bitisSehri2 = klavye.next();
        System.out.println("Sabit Ucreti Giriniz");
        int sabitUcret = klavye.nextInt();
        for (int i = 5; i < 51; i++) {
            ArrayList<Sehir> hesaplanacak = new ArrayList<>(sehirMap.values());
            hesaplanacak.forEach(s -> {
                s.setUzaklik(Double.MAX_VALUE);
                s.getEnkisaYol().clear();
                s.gidebilecegiSehirleriTemizle();
            });
            int yolcuSayisi = i;
            yolcuSayisinaGoreEnKisaRota(yolcuSayisi, hesaplanacak);
            Graph graph = new Graph();
            for (Sehir sehir : hesaplanacak) {
                graph.sehirEkle(sehir);
            }
            Sehir baslangic = sehirAra(graph, baslangicSehri2);
            Sehir bitis = sehirAra(graph, bitisSehri2);

            graph = DjikstraAlgorithm.enKisaYoluHesapla(graph, baslangic);

            dosyaYazilacak.append("\nYOLCU SAYISI: " + yolcuSayisi);
            if (bitis.getEnkisaYol() == null || bitis.getEnkisaYol().isEmpty()) {
                dosyaYazilacak.append("\n Yol Yok !! \n");
                System.out.println("Yol yok");
                diziKar[sayac] = 0;
            } else {
                StringBuilder yol = new StringBuilder();
                for (Sehir sehir : bitis.getEnkisaYol()) {
                    yol.append(sehir.getIsim() + "(" + sehir.getUzaklik() + ")" + " > ");
                    dosyaYazilacak.append("\n" + sehir.getIsim());
                    dosyaYazilacak.append("\n");
                    dosyaYazilacak.append("Enlem :  " + sehir.getKoordinat().getEnlem() + "\t" + "Boylam  :  " + sehir.getKoordinat().getBoylam() + "\t" + "Uzaklik  :   " + sehir.getUzaklik());

                }
                yol.append(bitis.getIsim() + "(" + bitis.getUzaklik() + ")");
                System.out.println(sayac + ". yol " + yol);
                diziKar[sayac] = (i * 1000) - ((int) bitis.getUzaklik() * 10);
            }
            sayac++;

        }
        for (int i = 0; i < numara.length; i++) {
            numara[i] = diziKar[i];
            System.out.println(diziKar[i]);
        }
        for (int j = 0; j < diziKar.length; j++) {
            for (int k = 0; k < diziKar.length; k++) {
                if (diziKar[j] < diziKar[k]) {
                    gecici = diziKar[k];
                    diziKar[k] = diziKar[j];
                    diziKar[j] = gecici;
                }
            }
        }
        int aranan = diziKar[45];
        for (int i = 0; i < 46; i++) {
            if (numara[i] == aranan) {
                System.out.println("Sabit Ucret ile Max Kar   " + (i - 5) + "  Yolcu Sayisi Ile Yapilir");
            }
        }
        System.out.println("En yuksek kar  " + diziKar[44]);
        byte[] byteArray2 = dosyaYazilacak.toString().getBytes();
        byte[] byteArray = dosyayaYazilacak.toString().getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(byteArray2);
        dosyayaYaz("yarikar.txt", byteBuffer);
        dosyayaYaz("sabitucret.txt", byteBuffer2);

    }

    public static void yolcuSayisinaGoreEnKisaRota(Integer yolcuSayisi, List<Sehir> sehirler) {
        //System.out.println("||||||||||||||||||||||||||||||||||||" +  yolcuSayisi +"||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        for (Sehir sehir : sehirler) {
            if (sehir.getKomsular() != null && !sehir.getKomsular().isEmpty()) {
                for (Sehir komsusu : sehir.getKomsular()) {
                    //System.out.println("SEHIR: " + sehir.getIsim()+ " Komsusu: " + komsusu.getIsim());
                    int yukseklik = Math.abs(komsusu.getRakim() - sehir.getRakim()) + 50;
                    double mesafe = DjikstraAlgorithm.distance(sehir.getKoordinat().getEnlem(), sehir.getKoordinat().getBoylam(), komsusu.getKoordinat().getEnlem(), komsusu.getKoordinat().getBoylam());
                    double atan;
                    if (komsusu.getRakim() - sehir.getRakim() < 0) {
                        atan = Math.atan(mesafe / yukseklik);
                    } else {
                        atan = Math.atan(yukseklik / mesafe);
                    }
                    double egim = Math.toDegrees(atan);

                    double x = Math.pow(yukseklik, 2) + Math.pow(mesafe, 2);
                    double asilUzaklik = Math.sqrt(x);

                    //System.out.println("YUK:" + yukseklik +"\t Mes:" +mesafe +"\tAtan:"+atan+"\tEgim:"+egim +"\tUz:" +asilUzaklik);
                    if ((80 - yolcuSayisi) >= egim) {
                        // System.out.println("KOMSU EKLENDI!!!!");
                        sehir.gercekKomsuEkle(komsusu, (int) asilUzaklik);
                    }
                }

            }
        }

    }

    public static Sehir sehirAra(Graph graph, String isim) {
        Optional<Sehir> first = graph.getSehirler().stream().filter(s -> isim.toUpperCase().equals(s.getIsim().toUpperCase())).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    private static final String USER_DIR = "user.dir";

    public static void dosyayaYaz(String dosyaAdi, ByteBuffer byteBuff) throws IOException {

        Set options = new HashSet();
        options.add(StandardOpenOption.CREATE);
        options.add(StandardOpenOption.TRUNCATE_EXISTING);
        options.add(StandardOpenOption.WRITE);

        Path dosya = Paths.get(dosyaAdi);
        FileChannel fileChannel = FileChannel.open(dosya, options);
        fileChannel.write(byteBuff);
        fileChannel.close();
    }
}
