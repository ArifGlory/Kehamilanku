package myproject.kehamilanku;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.kehamilanku.Kelas.SharedVariable;
import myproject.kehamilanku.Kelas.Utils;
import myproject.kehamilanku.activity.HomeActivity;
import myproject.kehamilanku.activity.PanelAdminActivity;
import myproject.kehamilanku.base.BaseActivity;

public class MainActivity extends BaseActivity {

    TextView tvKeHome;
    Button btnLogin;
    EditText etEmail,etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvKeHome = findViewById(R.id.tvKeHome);
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        tvKeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        String getEmailId = etEmail.getText().toString();
        String getPassword = etPassword.getText().toString();

        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {

            showErrorMessage("Semua Field Harus diisi");
        }
        else if (!m.find()) {
            showErrorMessage("Email anda tidak valid");
        }
        else {
            pDialogLoading.show();
            doLogin(getEmailId,getPassword);

        }
    }

    private void doLogin(final String email,String passwordUser){
        fAuth.signInWithEmailAndPassword(email,passwordUser).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()){
                    pDialogLoading.dismiss();
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Login gagal")
                            .setContentText("Periksa kembali Email dan Password anda")
                            .show();

                }else{
                    pDialogLoading.dismiss();
                    // Successfully signed in
                    SharedVariable.nama = fAuth.getCurrentUser().getDisplayName();
                    SharedVariable.userID = fAuth.getCurrentUser().getUid();
                    SharedVariable.email = fAuth.getCurrentUser().getEmail();
                    // get the Firebase user
                    FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                    // get the FCM token
                    String token = FirebaseInstanceId.getInstance().getToken();

                    Intent i = new Intent(MainActivity.this, PanelAdminActivity.class);
                    startActivity(i);

                    /*if (fbUser.isEmailVerified()){

                    }else{
                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Login gagal")
                                .setContentText("Email anda belum diverifikasi")
                                .show();
                    }*/



                }


            }
        });
    }
}
