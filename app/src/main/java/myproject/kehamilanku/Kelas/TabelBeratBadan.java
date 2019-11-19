package myproject.kehamilanku.Kelas;

public class TabelBeratBadan {
    public String minggu;
    public String minimum;
    public String maksimum;
    public String ratarata;

    public TabelBeratBadan(String minggu, String minimum, String maksimum, String ratarata) {
        this.minggu = minggu;
        this.minimum = minimum;
        this.maksimum = maksimum;
        this.ratarata = ratarata;
    }

    public String getMinggu() {
        return minggu;
    }

    public void setMinggu(String minggu) {
        this.minggu = minggu;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaksimum() {
        return maksimum;
    }

    public void setMaksimum(String maksimum) {
        this.maksimum = maksimum;
    }

    public String getRatarata() {
        return ratarata;
    }

    public void setRatarata(String ratarata) {
        this.ratarata = ratarata;
    }
}
