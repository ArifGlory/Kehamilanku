package myproject.kehamilanku.Kelas;

import java.io.Serializable;

public class TipsKehamilan implements Serializable {
    public String idTips;
    public String namaTips;
    public String foto;
    public String deskripsi;

    public TipsKehamilan(){}

    public TipsKehamilan(String idTips, String namaTips, String foto, String deskripsi) {
        this.idTips = idTips;
        this.namaTips = namaTips;
        this.foto = foto;
        this.deskripsi = deskripsi;
    }

    public String getIdTips() {
        return idTips;
    }

    public void setIdTips(String idTips) {
        this.idTips = idTips;
    }

    public String getNamaTips() {
        return namaTips;
    }

    public void setNamaTips(String namaTips) {
        this.namaTips = namaTips;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
