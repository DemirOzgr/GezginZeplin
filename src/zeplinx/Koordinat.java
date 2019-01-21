package zeplinx;

public class Koordinat {

    private double enlem;
    private double boylam;

    public Koordinat(double enlem, double boylam) {
        this.enlem = enlem;
        this.boylam = boylam;
    }

    public double getEnlem() {
        return enlem;
    }

    public void setEnlem(double enlem) {
        this.enlem = enlem;
    }

    public double getBoylam() {
        return boylam;
    }

    public void setBoylam(double boylam) {
        this.boylam = boylam;
    }
}
