package myproject.kehamilanku.admin;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import myproject.kehamilanku.Kelas.PermissionHelper;
import myproject.kehamilanku.Kelas.TipsKehamilan;
import myproject.kehamilanku.Kelas.Utils;
import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;

public class AddTipsActivity extends BaseActivity implements PermissionHelper.PermissionListener  {

    EditText etDeskripsi,etNama;
    Button btnSimpan;
    ImageView ivTips;

    static final int RC_PERMISSION_READ_EXTERNAL_STORAGE = 1;
    static final int RC_IMAGE_GALLERY = 2;

    PermissionHelper permissionHelper;
    Uri uri,file;
    String myUrlFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tips);

        ivTips  = findViewById(R.id.ivTips);
        etDeskripsi  = findViewById(R.id.etDeskripsi);
        etNama  = findViewById(R.id.etNama);
        btnSimpan  = findViewById(R.id.btnSimpan);
        ref = firestore.collection("tipskehamilan");

        permissionHelper = new PermissionHelper(this);
        permissionHelper.setPermissionListener(this);

        ivTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> listPermissions = new ArrayList<>();
                listPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                listPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                permissionHelper.checkAndRequestPermissions(listPermissions);
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }

    private void checkValidation() {

        // Get all edittext texts
        String getNama      = etNama.getText().toString();
        String getDeskripsi     = etDeskripsi.getText().toString();
        Pattern p = Pattern.compile(Utils.regEx);

        // Check if all strings are null or not
        if (getNama.equals("") || getNama.length() == 0 ||
                getDeskripsi.equals("") || getDeskripsi.length() == 0 ||
                uri == null) {

            showErrorMessage("Semua data harus diisi");
        }
        else{
            pDialogLoading.show();
            simpanData();
        }
    }

    private void simpanData(){
        showLoading();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imagesRef = storageRef.child("images");
        StorageReference userRef = imagesRef.child(fbUser.getUid());
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = fbUser.getUid() + "_" + timeStamp;
        StorageReference fileRef = userRef.child(filename);

        UploadTask uploadTask = fileRef.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(AddTipsActivity.this, "Upload failed!\n" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                dismissLoading();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Toast.makeText(AddTipsActivity.this, "Upload finished!", Toast.LENGTH_SHORT).show();

                // save image to database
                final String urlGambar = downloadUrl.toString();

                TipsKehamilan tipsKehamilan = new TipsKehamilan(
                        timeStamp,
                        etNama.getText().toString(),
                        urlGambar,
                        etDeskripsi.getText().toString()

                );

                ref.document(timeStamp).set(tipsKehamilan).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dismissLoading();
                        if (task.isSuccessful()){
                            showSuccessMessage("Data berhasil disimpan");
                            resetKomponen();
                        }else {
                            showErrorMessage("Terjadi kesalahan, coba lagi nanti");
                            Log.d("AddTips:","eror"+task.getException().toString());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dismissLoading();
                        showErrorMessage("Terjadi kesalahan, coba lagi nanti");
                        Log.d("AddTips:","eror"+e.toString());
                    }
                });

            }
        });
    }

    private void resetKomponen(){
        etDeskripsi.setText("");
        etNama.setText("");
        uri = null;
        ivTips.setImageResource(R.drawable.img_placeholder);
    }

    @Override
    public void onPermissionCheckDone() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, RC_IMAGE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        // menangkap hasil balikan dari Place Picker, dan menampilkannya pada TextView

        if (requestCode == RC_IMAGE_GALLERY && resultCode == RESULT_OK) {
            uri = data.getData();
            ivTips.setImageURI(uri);
        }
        else if (requestCode == 100 && resultCode == RESULT_OK){
            uri = file;
            ivTips.setImageURI(uri);

        }
    }
}
