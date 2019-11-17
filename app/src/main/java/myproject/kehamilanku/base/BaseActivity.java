package myproject.kehamilanku.base;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import myproject.kehamilanku.Kelas.UserPreference;
import myproject.kehamilanku.Kelas.VideoSenam;
import myproject.kehamilanku.adapter.AdapterTipsKehamilan;
import myproject.kehamilanku.adapter.AdapterVideoSenam;


/**
 * Created by Miroslaw Stanek on 19.01.15.
 */
public class BaseActivity extends AppCompatActivity {

    public SweetAlertDialog pDialogLoading,pDialodInfo;
    public FirebaseFirestore firestore;
    public FirebaseAuth fAuth;
    public FirebaseUser fbUser;
    public CollectionReference ref;
    public UserPreference mUserPref;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        Firebase.setAndroidContext(this);
        FirebaseApp.initializeApp(this);
        fAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserPref = new UserPreference(this);

        pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);

    }

    public void showLoading(){
        pDialogLoading.show();
    }

    public void dismissLoading(){
        pDialogLoading.dismiss();
    }

    public void setContentViewWithoutInject(int layoutResId) {
        super.setContentView(layoutResId);
    }

    public void showErrorMessage(String message){
        Toasty.error(getApplicationContext(),message, Toast.LENGTH_SHORT,true).show();
    }

    public void showSuccessMessage(String message){
        Toasty.success(getApplicationContext(),message, Toast.LENGTH_SHORT,true).show();
    }

    public void showWarningMessage(String message){
        Toasty.warning(getApplicationContext(),message, Toast.LENGTH_SHORT,true).show();
    }

    public void showInfoMessage(String message){
        Toasty.info(getApplicationContext(),message, Toast.LENGTH_SHORT,true).show();
    }

    public void getDataVideoSenam(CollectionReference reference, final List<VideoSenam> videoSenamList, final AdapterVideoSenam adapterVideoSenam){
        showLoading();

        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                dismissLoading();
                videoSenamList.clear();

                if (task.isSuccessful()){

                    int size = task.getResult().size();
                    if (size > 0){

                        for (DocumentSnapshot doc : task.getResult()){

                            VideoSenam videoSenam = doc.toObject(VideoSenam.class);
                            videoSenamList.add(videoSenam);

                        }
                        adapterVideoSenam.notifyDataSetChanged();

                    }else{
                        showInfoMessage("Belum ada data laundry");
                    }

                }else{
                    showErrorMessage("Terjadi kesalahan,coba lagi nanti");
                }
            }
        });
    }

    public void getDataTips(CollectionReference reference, final List<TipsKehamilan> tipsKehamilanList, final AdapterTipsKehamilan adapterTipsKehamilan){
        showLoading();

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
