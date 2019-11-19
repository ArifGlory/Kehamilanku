package myproject.kehamilanku.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import myproject.kehamilanku.Kelas.PermissionHelper;
import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;

public class ChangeAvatarActivity extends BaseActivity implements PermissionHelper.PermissionListener {

    CircleImageView ivProfPict;
    Button btnEdit;
    PermissionHelper permissionHelper;
    static final int RC_PERMISSION_READ_EXTERNAL_STORAGE = 1;
    static final int RC_IMAGE_GALLERY = 2;
    Uri uri,file;
    String myUrlFoto;
    String TAG = "changeAvatar";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);

        ivProfPict = findViewById(R.id.ivProfPict);
        btnEdit = findViewById(R.id.btnEdit);

        if (mUserPref.getFoto() != null){
            Glide.with(this)
                    .load(mUserPref.getFoto())
                    .into(ivProfPict);
        }

        permissionHelper = new PermissionHelper(this);
        permissionHelper.setPermissionListener(this);

        ivProfPict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> listPermissions = new ArrayList<>();
                listPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                listPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                permissionHelper.checkAndRequestPermissions(listPermissions);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }

    private void checkValidation() {


        if (uri == null) {

            showErrorMessage("Anda belum memilih gambar");
        }
        else{
            showLoading();
            uploadFoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestCallBack(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        // menangkap hasil balikan dari Place Picker, dan menampilkannya pada TextView

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_IMAGE_GALLERY && resultCode == RESULT_OK) {
            uri = data.getData();
            ivProfPict.setImageURI(uri);
        } else if (requestCode == 100 && resultCode == RESULT_OK) {
            uri = file;
            ivProfPict.setImageURI(uri);

        }
    }

    public void uploadFoto(){
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imagesRef = storageRef.child("images");
        StorageReference userRef = imagesRef.child(timeStamp);

        String filename = mUserPref.getIdUser() + "_" + timeStamp;
        StorageReference fileRef = userRef.child(filename);

        UploadTask uploadTask = fileRef.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(ChangeAvatarActivity.this, "Upload failed!\n" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                dismissLoading();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Toast.makeText(ChangeAvatarActivity.this, "Upload finished!", Toast.LENGTH_SHORT).show();
                myUrlFoto = downloadUrl.toString();
                Log.d("downloadurl :",myUrlFoto);

                // save  to UserPreference
               mUserPref.setFoto(myUrlFoto);
                Log.d("fotoTersimpan:",myUrlFoto);
                showSuccessMessage("Foto Telah Di Update");
                dismissLoading();

            }
        });
    }


    @Override
    public void onPermissionCheckDone() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, RC_IMAGE_GALLERY);
    }
}
