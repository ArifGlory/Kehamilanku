package myproject.kehamilanku.base;


import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import myproject.kehamilanku.Kelas.TipsKehamilan;
import myproject.kehamilanku.adapter.AdapterTipsKehamilan;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }

    public SweetAlertDialog pDialogLoading,pDialodInfo;
    public FirebaseFirestore firestore;
    public FirebaseAuth fAuth;
    public FirebaseUser fbUser;
    public CollectionReference ref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
        FirebaseApp.initializeApp(getActivity());
        fAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        fbUser = FirebaseAuth.getInstance().getCurrentUser();

        pDialogLoading = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);
    }

    public void showDialogLoading(){
        pDialogLoading.show();
    }

    public void dismissLoading(){
        pDialogLoading.dismiss();
    }

    public void showErrorMessage(String message){
        Toasty.error(getActivity(),message, Toast.LENGTH_SHORT,true).show();
    }

    public void showSuccessMessage(String message){
        Toasty.success(getActivity(),message, Toast.LENGTH_SHORT,true).show();
    }

    public void showWarningMessage(String message){
        Toasty.warning(getActivity(),message, Toast.LENGTH_SHORT,true).show();
    }

    public void showInfoMessage(String message){
        Toasty.info(getActivity(),message, Toast.LENGTH_SHORT,true).show();
    }


    public void getDataTips(CollectionReference reference, final List<TipsKehamilan> tipsKehamilanList, final AdapterTipsKehamilan adapterTipsKehamilan){
        showDialogLoading();

        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               dismissLoading();
                tipsKehamilanList.clear();

                if (task.isSuccessful()){

                    int size = task.getResult().size();
                    if (size > 0){

                        for (DocumentSnapshot doc : task.getResult()){

                            TipsKehamilan tipsKehamilan = doc.toObject(TipsKehamilan.class);
                            tipsKehamilanList.add(tipsKehamilan);

                        }
                        adapterTipsKehamilan.notifyDataSetChanged();

                    }else{
                        showInfoMessage("Belum ada data laundry");
                    }

                }else{
                    showErrorMessage("Terjadi kesalahan,coba lagi nanti");
                }
            }
        });
    }




}
