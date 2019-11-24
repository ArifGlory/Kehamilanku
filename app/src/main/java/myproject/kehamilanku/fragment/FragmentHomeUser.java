package myproject.kehamilanku.fragment;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import myproject.kehamilanku.Kelas.TabelBeratBadan;
import myproject.kehamilanku.R;
import myproject.kehamilanku.activity.ListPerkembanganJanin;
import myproject.kehamilanku.activity.ListPetugasActivity;
import myproject.kehamilanku.activity.ListTipsKehamilan;
import myproject.kehamilanku.activity.ListVideoSenamActivity;
import myproject.kehamilanku.activity.ProfilActivity;
import myproject.kehamilanku.activity.TabelBeratBadanActivity;
import myproject.kehamilanku.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHomeUser extends BaseFragment {


    public FragmentHomeUser() {
        // Required empty public constructor
    }

    RelativeLayout rlTipsKehamilan,rlKembangJanin,rlProfil,rlVideo,rlPetugas,rlBeratBadan,rlKeJanin;
    public static android.app.AlertDialog dialog;
    TextView tvNama,tvAlamat,tvHPL,tvSisaKehamilan,tvUsiaKehamilan,tvBulanJanin,tvTrisemester,tvInfoBerat;
    String HTHP;
    Calendar myCalendar,calset;
    int year,month,day;
    CircleImageView ivProfPict;
    CircleImageView ivFotoJanin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        rlTipsKehamilan = view.findViewById(R.id.rlTipsKehamilan);
        rlKembangJanin = view.findViewById(R.id.rlKembangJanin);
        rlKeJanin = view.findViewById(R.id.rlKeJanin);
        rlProfil = view.findViewById(R.id.rlProfil);
        ivFotoJanin = view.findViewById(R.id.ivFotoJanin);
        rlVideo = view.findViewById(R.id.rlVideo);
        rlPetugas = view.findViewById(R.id.rlPetugas);
        rlBeratBadan = view.findViewById(R.id.rlBeratBadan);
        tvNama = view.findViewById(R.id.tvNama);
        tvAlamat = view.findViewById(R.id.tvAlamat);
        tvBulanJanin = view.findViewById(R.id.tvBulanJanin);
        tvHPL = view.findViewById(R.id.tvHPL);
        tvSisaKehamilan = view.findViewById(R.id.tvSisaKehamilan);
        tvUsiaKehamilan = view.findViewById(R.id.tvUsiaKehamilan);
        tvTrisemester = view.findViewById(R.id.tvTrisemester);
        tvInfoBerat = view.findViewById(R.id.tvInfoBerat);
        ivProfPict = view.findViewById(R.id.ivProfPict);



        rlTipsKehamilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListTipsKehamilan.class);
                startActivity(intent);
            }
        });
        rlVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListVideoSenamActivity.class);
                startActivity(intent);
            }
        });
        rlProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfilActivity.class);
                startActivity(intent);
            }
        });
        rlPetugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListPetugasActivity.class);
                startActivity(intent);
            }
        });
        rlKeJanin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListPerkembanganJanin.class);
                startActivity(intent);
            }
        });
        rlKembangJanin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListPerkembanganJanin.class);
                startActivity(intent);
            }
        });
        rlBeratBadan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TabelBeratBadanActivity.class);
                startActivity(intent);
            }
        });

        myCalendar = Calendar.getInstance();
        calset = (Calendar) myCalendar.clone();

        if (mUserPref.getHthp() != null){
            Log.d("myHTHP:"," : "+mUserPref.getHthp());
            HTHP = mUserPref.getHthp();
            year = Integer.parseInt(HTHP.substring(0,4));
            month = Integer.parseInt(HTHP.substring(5,7)) - 1;
            day = Integer.parseInt(HTHP.substring(8,10));

            hitungHariPerkiraanLahir();
            try {
                hitungSisaMasaKehamilan();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                hitungUsiaKehamilan();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        setView();

        return view;
    }

    private void hitungUsiaKehamilan() throws ParseException {
        calset.set(year,month,day,00,00,00);
        int nowMonth = myCalendar.get(Calendar.MONTH) + 1;
        int hphtMonth = calset.get(Calendar.MONTH) + 1;

        String endDate  = myCalendar.get(Calendar.DATE)+"/"+nowMonth+"/"+myCalendar.get(Calendar.YEAR)+" 11:30:10";
        String startDate  = calset.get(Calendar.DATE)+"/"+hphtMonth+"/"+calset.get(Calendar.YEAR)+" 11:30:10";

        //tes
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        Date date1 = simpleDateFormat.parse(startDate);
        Date date2 = simpleDateFormat.parse(endDate);

        long numberOfDays = getUnitBetweenDates(date1, date2, TimeUnit.DAYS);
        long numberOfWeek = numberOfDays / 7;
        long numberOfMonth = numberOfDays / 30;
        int  minggu        = (int) numberOfWeek;
        setKeteranganJanin(numberOfMonth);
        setKeteranganBerat(minggu);


        Log.d("UsiaKehamilan",": "+numberOfDays +" hari");
        Log.d("UsiaKehamilan",": "+numberOfMonth +" bulan");
        tvUsiaKehamilan.setText(numberOfWeek+" Minggu "+numberOfDays+" Hari");

    }

    private void setKeteranganBerat(int numberOfWeek){


        if (mUserPref.getBerat() != 0 && mUserPref.getTinggi() != 0){
            Double bmi = 0.0;
            Double tinggi = 0.0;
            Double getTinggi = Double.valueOf(mUserPref.getTinggi());
            tinggi = getTinggi / 100;
            tinggi = tinggi * tinggi;
            bmi = mUserPref.getBerat() / tinggi;

            if (bmi < 18.5 ){
                setTabelUnderweight(numberOfWeek,bmi);
            }else if(bmi > 18.5 && bmi < 24.9){
                setTabelIdeal(numberOfWeek,bmi);
            }else if (bmi > 25 && bmi < 29.9){
                setTabelOverweight(numberOfWeek,bmi);
            }else{
                setTabelObesitas(numberOfWeek,bmi);
            }
        }

    }

    private void setKeteranganJanin(long numberOfMonth){

        if (numberOfMonth < 9){
            numberOfMonth+=1;

            tvBulanJanin.setText("Usia Janin : "+numberOfMonth+" Bulan");

            if (numberOfMonth == 1){
                ivFotoJanin.setImageResource(R.drawable.janin1);
            }else if (numberOfMonth == 2){
                ivFotoJanin.setImageResource(R.drawable.janin2);
            }else if (numberOfMonth == 3){
                ivFotoJanin.setImageResource(R.drawable.janin3);
            }else if (numberOfMonth == 4){
                ivFotoJanin.setImageResource(R.drawable.janin4);
            }else if (numberOfMonth == 5){
                ivFotoJanin.setImageResource(R.drawable.janin5);
            }else if (numberOfMonth == 6){
                ivFotoJanin.setImageResource(R.drawable.janin6);
            }else if (numberOfMonth == 7){
                ivFotoJanin.setImageResource(R.drawable.janin7);
            }else if (numberOfMonth == 8){
                ivFotoJanin.setImageResource(R.drawable.janin8);
            }

            //set trisemester
            if (numberOfMonth <= 3){
                tvTrisemester.setText("Kehamilan Trisemester Pertama");
            }else if (numberOfMonth > 3 && numberOfMonth <=6){
                tvTrisemester.setText("Kehamilan Trisemester Kedua");
            }else if (numberOfMonth > 6 && numberOfMonth < 10 ){
                tvTrisemester.setText("Kehamilan Trisemester Ketiga");
            }

        }else{
            tvBulanJanin.setText("Usia Janin : "+9+" Bulan");
            ivFotoJanin.setImageResource(R.drawable.janin9);

            tvTrisemester.setText("Kehamilan Trisemester Ketiga");
        }
    }

    private void hitungSisaMasaKehamilan() throws ParseException {


        calset.set(year,month,day,00,00,00);
        hitungHariPerkiraanLahir();
        int newMonth = myCalendar.get(Calendar.MONTH) + 1;
        int endDateMonth = calset.get(Calendar.MONTH) + 1;
        String startDate  = myCalendar.get(Calendar.DATE)+"/"+newMonth+"/"+myCalendar.get(Calendar.YEAR)+" 11:30:10";
        String endDate  = calset.get(Calendar.DATE)+"/"+endDateMonth+"/"+calset.get(Calendar.YEAR)+" 11:30:10";
        Log.d("HPHT","tanggal skrg: "+startDate);
        Log.d("HPHT","tanggal hpl: "+endDate);

        //tes
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        Date date1 = simpleDateFormat.parse(startDate);
        Date date2 = simpleDateFormat.parse(endDate);

        long numberOfDays = getUnitBetweenDates(date1, date2, TimeUnit.DAYS);
        Log.d("HPHT","Sisa masa kehamilan: "+numberOfDays +" hari");
        tvSisaKehamilan.setText(numberOfDays+" hari lagi");
    }

    private void hitungHariPerkiraanLahir(){
        //set HPHT dlu
        calset.set(year,month,day,00,00,00);

        if (month <= 2){
            //bulan dikurang 3
            calset.add(Calendar.MONTH,9);
        }else{
            //coba hari ditambah 7
            calset.add(Calendar.DATE,7);
            //bulan dikurang 3
            calset.add(Calendar.MONTH,-3);
            //tahun nya ditambah satu
            calset.add(Calendar.YEAR,1);
        }


        Log.d("HPHT","tanggal perkiraan lahir : "+calset.getTime());

        String namaBulan = new SimpleDateFormat("MMM").format(calset.getTime());
        tvHPL.setText(calset.get(Calendar.DATE)+"-"+namaBulan+"-"+calset.get(Calendar.YEAR));
    }

    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        Log.d("HPHT","endDate time nya :"+endDate.getTime());
        Log.d("HPHT","startDate time nya :"+startDate.getTime());
        long timeDiff = endDate.getTime() - startDate.getTime();

        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    private void setView(){
        if (mUserPref.getNama() != null){
            tvNama.setText(mUserPref.getNama());
        }else{
            tvNama.setText("Nama Belum di setting");
        }

        if (mUserPref.getAlamat() != null){
            tvAlamat.setText(mUserPref.getAlamat());
        }else{
            tvAlamat.setText("Alamat Belum di setting");
        }

        if (mUserPref.getFoto() != null){
            Glide.with(getActivity())
                    .load(mUserPref.getFoto())
                    .into(ivProfPict);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setView();
        if (mUserPref.getHthp() != null){
            Log.d("myHTHP:"," : "+mUserPref.getHthp());
            HTHP = mUserPref.getHthp();
            year = Integer.parseInt(HTHP.substring(0,4));
            month = Integer.parseInt(HTHP.substring(5,7)) - 1;
            day = Integer.parseInt(HTHP.substring(8,10));

            hitungHariPerkiraanLahir();
            try {
                hitungSisaMasaKehamilan();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                hitungUsiaKehamilan();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    private void setTabelIdeal(int numberOfWeek,Double bmi){


        Double rangeMin = 11.0;
        rangeMin = rangeMin / 20;
        Double rangeMax = 16.0;
        rangeMax = rangeMax / 20;
        DecimalFormat df = new DecimalFormat("#.##");

        Double beratMin = Double.valueOf(mUserPref.getBerat());
        Double beratMax = Double.valueOf(mUserPref.getBerat());
        Double rata2 = 0.0;

        for (int c=2;c <=40; c+=2){
            beratMin = beratMin + rangeMin;
            beratMax = beratMax +rangeMax;
            rata2 = (beratMax + beratMin) / 2;

            String myMinggu = String.valueOf(df.format(c));
            String myBeratMin = String.valueOf(df.format(beratMin));
            String myBeratMax = String.valueOf(df.format(beratMax));
            String myRata2 = String.valueOf(df.format(rata2));

            //berarti dalam rentang 2 minggu yg sama
            if (c == numberOfWeek || c - numberOfWeek == 1){
                tvInfoBerat.setText("Berat badan yang direkomendasikan : \n" +
                        "Minimum : "+myBeratMin+ " kg \n" +
                        "Maksimum : "+myBeratMax+ " kg \n" +
                        "Rata-rata : "+myRata2+ " kg");
            }

        }
    }

    private void setTabelUnderweight(int numberOfWeek,Double bmi){


        Double rangeMin = 12.0;
        rangeMin = rangeMin / 20;
        Double rangeMax = 18.0;
        rangeMax = rangeMax / 20;
        DecimalFormat df = new DecimalFormat("#.##");

        Double beratMin = Double.valueOf(mUserPref.getBerat());
        Double beratMax = Double.valueOf(mUserPref.getBerat());
        Double rata2 = 0.0;

        for (int c=2;c <=40; c+=2){
            beratMin = beratMin + rangeMin;
            beratMax = beratMax +rangeMax;
            rata2 = (beratMax + beratMin) / 2;

            String myMinggu = String.valueOf(df.format(c));
            String myBeratMin = String.valueOf(df.format(beratMin));
            String myBeratMax = String.valueOf(df.format(beratMax));
            String myRata2 = String.valueOf(df.format(rata2));

            //berarti dalam rentang 2 minggu yg sama
            if (c == numberOfWeek || c - numberOfWeek == 1){
                tvInfoBerat.setText("Berat badan yang direkomendasikan : \n" +
                        "Minimum : "+myBeratMin+ " kg \n" +
                        "Maksimum : "+myBeratMax+ " kg \n" +
                        "Rata-rata : "+myRata2+ " kg");
            }

        }
    }

    private void setTabelOverweight(int numberOfWeek,Double bmi){


        Double rangeMin = 7.0;
        rangeMin = rangeMin / 20;
        Double rangeMax = 11.0;
        rangeMax = rangeMax / 20;
        DecimalFormat df = new DecimalFormat("#.##");

        Double beratMin = Double.valueOf(mUserPref.getBerat());
        Double beratMax = Double.valueOf(mUserPref.getBerat());
        Double rata2 = 0.0;

        for (int c=2;c <=40; c+=2){
            beratMin = beratMin + rangeMin;
            beratMax = beratMax +rangeMax;
            rata2 = (beratMax + beratMin) / 2;

            String myMinggu = String.valueOf(df.format(c));
            String myBeratMin = String.valueOf(df.format(beratMin));
            String myBeratMax = String.valueOf(df.format(beratMax));
            String myRata2 = String.valueOf(df.format(rata2));

            //berarti dalam rentang 2 minggu yg sama
            if (c == numberOfWeek || c - numberOfWeek == 1){
                tvInfoBerat.setText("Berat badan yang direkomendasikan : \n" +
                        "Minimum : "+myBeratMin+ " kg \n" +
                        "Maksimum : "+myBeratMax+ " kg \n" +
                        "Rata-rata : "+myRata2+ " kg");
            }

        }
    }

    private void setTabelObesitas(int numberOfWeek,Double bmi){


        Double rangeMin = 5.0;
        rangeMin = rangeMin / 20;
        Double rangeMax = 9.0;
        rangeMax = rangeMax / 20;
        DecimalFormat df = new DecimalFormat("#.##");

        Double beratMin = Double.valueOf(mUserPref.getBerat());
        Double beratMax = Double.valueOf(mUserPref.getBerat());
        Double rata2 = 0.0;

        for (int c=2;c <=40; c+=2){
            beratMin = beratMin + rangeMin;
            beratMax = beratMax +rangeMax;
            rata2 = (beratMax + beratMin) / 2;

            String myMinggu = String.valueOf(df.format(c));
            String myBeratMin = String.valueOf(df.format(beratMin));
            String myBeratMax = String.valueOf(df.format(beratMax));
            String myRata2 = String.valueOf(df.format(rata2));

            //berarti dalam rentang 2 minggu yg sama
            if (c == numberOfWeek || c - numberOfWeek == 1){
                tvInfoBerat.setText("Berat badan yang direkomendasikan : \n" +
                        "Minimum : "+myBeratMin+ " kg \n" +
                        "Maksimum : "+myBeratMax+ " kg \n" +
                        "Rata-rata : "+myRata2+ " kg");
            }

        }
    }
}
