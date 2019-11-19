package myproject.kehamilanku.Kelas;

import java.io.Serializable;

public class Petugas implements Serializable {
    public String idPetugas;
    public String namaPetugas;
    public String phonePetugas;

    public Petugas(){}

    public Petugas(String idPetugas, String namaPetugas, String phonePetugas) {
        this.idPetugas = idPetugas;
        this.namaPetugas = namaPetugas;
        this.phonePetugas = phonePetugas;
    }

    public String getIdPetugas() {
        return idPetugas;
    }

    public void setIdPetugas(String idPetugas) {
        this.idPetugas = idPetugas;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getPhonePetugas() {
        return phonePetugas;
    }

    public void setPhonePetugas(String phonePetugas) {
        this.phonePetugas = phonePetugas;
    }
}
