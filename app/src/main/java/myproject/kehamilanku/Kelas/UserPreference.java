package myproject.kehamilanku.Kelas;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    private String KEY_EMAIL = "email";
    private String KEY_ALAMAT = "alamat";
    private String KEY_FOTO = "foto";
    private String KEY_TOKEN = "token";
    private String KEY_ID_USER = "username";
    private String KEY_IS_LOGGED_IN = "islogin";
    private String KEY_NAMA = "nama";
    private String KEY_NO_HP = "nope";
    private String KEY_PASSWORD = "password";
    private String KEY_HTHP = "hthp";
    private String KEY_TINGGI = "tinggi";
    private String KEY_BERAT = "berat";
    private SharedPreferences preferences;

    public UserPreference(Context context){
        String PREFS_NAME = "UserPref";
        preferences = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
    }



    public void setEmail(String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_EMAIL,email);
        editor.apply();
    }

    public void setBerat(int berat){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_BERAT,berat);
        editor.apply();
    }

    public void setHthp(String hthp){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_HTHP,hthp);
        editor.apply();
    }

    public void setTinggi(int tinggi){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_TINGGI,tinggi);
        editor.apply();
    }


    public void setPassword(String password){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_PASSWORD,password);
        editor.apply();
    }



    public void setNama(String nama){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NAMA,nama);
        editor.apply();
    }

    public void setAlamat(String alamat){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_ALAMAT,alamat);
        editor.apply();
    }


    public void setFoto(String foto){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_FOTO,foto);
        editor.apply();
    }


    public void setNope(String nope){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NO_HP,nope);
        editor.apply();
    }

    public void setToken(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_TOKEN,token);
        editor.apply();
    }

    public void setIdUser(String idUser){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_ID_USER,idUser);
        editor.apply();
    }

    public void setIsLoggedIn(String isLoggedIn){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_IS_LOGGED_IN,isLoggedIn);
        editor.apply();
    }

    public String getIsLoggedIn(){
        return preferences.getString(KEY_IS_LOGGED_IN,null);
    }


    public String getEmail(){
        return preferences.getString(KEY_EMAIL,null);
    }

    public int getBerat(){
        return preferences.getInt(KEY_BERAT,0);
    }

    public int getTinggi(){
        return preferences.getInt(KEY_TINGGI,0);
    }

    public String getHthp(){
        return preferences.getString(KEY_HTHP,null);
    }


    public String getPassword(){
        return preferences.getString(KEY_PASSWORD,null);
    }

    public String getAlamat(){
        return preferences.getString(KEY_ALAMAT,null);
    }


    public String getFoto(){
        return preferences.getString(KEY_FOTO,null);
    }


    public String getIdUser(){
        return preferences.getString(KEY_ID_USER,null);
    }

    public String getToken(){
        return preferences.getString(KEY_TOKEN,null);
    }

    public String getNama(){
        return preferences.getString(KEY_NAMA,null);
    }

    public String getNope(){
        return preferences.getString(KEY_NO_HP,null);
    }
}