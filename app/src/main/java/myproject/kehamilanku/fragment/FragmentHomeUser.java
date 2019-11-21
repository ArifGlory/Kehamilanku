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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
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

    RelativeLayout rlTipsKehamilan,rlKembangJanin,rlProfil,rlVideo,rlPetugas,rlBeratBadan;
    public static android.app.AlertDialog dialog;
    TextView tvNama,tvAlamat,tvHPL,tvSisaKehamilan,tvUsiaKehamilan;
    String HTHP;
    Calendar myCalendar,calset;
    int year,month,day;
    CircleImageView ivProfPict;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        rlTipsKehamilan = view.findViewById(R.id.rlTipsKehamilan);
        rlKembangJanin = view.findViewById(R.id.rlKembangJanin);
        rlProfil = view.findViewById(R.id.rlProfil);
        rlVideo = view.findViewById(R.id.rlVideo);
        rlPetugas = view.findViewById(R.id.rlPetugas);
        rlBeratBadan = view.findViewById(R.id.rlBeratBadan);
        tvNama = view.findViewById(R.id.tvNama);
        tvAlamat = view.findViewById(R.id.tvAlamat);
        tvHPL = view.findViewById(R.id.tvHPL);
        tvSisaKehamilan = view.findViewById(R.id.tvSisaKehamilan);
        tvUsiaKehamilan = view.findViewById(R.id.tvUsiaKehamilan);
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
        Log.d("UsiaKehamilan",": "+numberOfDays +" hari");
        tvUsiaKehamilan.setText(numberOfWeek+" Minggu "+numberOfDays+" Hari");

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
}
