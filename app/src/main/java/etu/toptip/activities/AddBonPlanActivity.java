package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import etu.toptip.R;
import etu.toptip.controller.NotificationsController;
import etu.toptip.fragments.AccueilFragment;
import etu.toptip.fragments.CameraFragment;
import etu.toptip.fragments.ICameraPermission;
import etu.toptip.fragments.IStorageActivity;
import etu.toptip.fragments.StorageFragment;
import etu.toptip.helper.Infologin;
import etu.toptip.model.BonPlan;
import etu.toptip.views.NotificationsView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AddBonPlanActivity extends AppCompatActivity implements ICameraPermission, IStorageActivity {

    int SELECT_PICTURE = 200;
    private int notifID = 0;
    ArrayList<String> infos = new ArrayList<>();
    ImageView IVPreviewImage;
    Uri uri;

    private Bitmap picture;
    NotificationsController notificationsController;
    NotificationsView notificationsView;
    private CameraFragment cameraFragment;
    private StorageFragment storageFragment;


    public AddBonPlanActivity() throws Throwable {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bon_plan);

        ImageButton retour = findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(myIntent);
            }
            });


        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("idLieu");
        System.out.println("idLieu" + id);

        cameraFragment = (CameraFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentCamera);
        if (cameraFragment == null) cameraFragment = new CameraFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentCamera, cameraFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        storageFragment = (StorageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentStorage);
        if (storageFragment == null) storageFragment = new StorageFragment(this);
        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction2.replace(R.id.fragmentStorage, storageFragment);
        fragmentTransaction2.addToBackStack(null);
        fragmentTransaction2.commit();

        EditText description = (EditText) findViewById(R.id.description);
        EditText expiration = (EditText) findViewById(R.id.expiration);
        TextView erreur = (TextView) findViewById(R.id.idTVHeaderErreur1);

        Button addBP = (Button) findViewById(R.id.BtnAjouterBPOK);
        addBP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String descriptionText = description.getText().toString();
                String expirationText = expiration.getText().toString();

                String sot = uploadImage(descriptionText, expirationText, id);

                if (sot.equals("true")) {
                    erreur.setTextColor(getResources().getColor(R.color.greenAuth));
                    erreur.setText("Bon plan ajouté");
                }else{
                    erreur.setText(sot);
                }


//                System.out.println("INNNNNFFFFFFFFFFFOOOOOO: " + sot);

                if (sot.equals("true")) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(myIntent);
                }
            }
        });

        Button BSelectImage = (Button) findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    void imageChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityIfNeeded(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                uri = data.getData();
                if (null != uri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(uri);
                    //System.out.println("j'ai rentré");
                }
            }
            if (requestCode == 101) {
                Bitmap bitmap = (Bitmap) (data != null ? data.getExtras().get("data") : null);
                IVPreviewImage.setImageBitmap(bitmap);
            }
            if (requestCode == REQUEST_CAMERA) {
                if (resultCode == RESULT_OK) {
                    picture = (Bitmap) data.getExtras().get("data");
                    cameraFragment.setImage(picture);
                    storageFragment.setEnabledSaveButton();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "picture canceled", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "action failed", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }


    private String uriToFilename(Uri uri) {
        String path = null;

        if ((Build.VERSION.SDK_INT < 19) && (Build.VERSION.SDK_INT > 11)) {
            path = getRealPathFromURI_API11to18(this, uri);
            System.out.println("111111111111111111111111111: " + getRealPathFromURI_API11to18(this, uri));
        } else {
            System.out.println("22222222222222222222222");
            path = getFilePath(this, uri);
        }

        return path;
    }

    public String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }


    public String getFilePath(Context context, Uri uri) {
        //Log.e("uri", uri.getPath());
        String filePath = "";
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String wholeID = DocumentsContract.getDocumentId(uri);
            //Log.e("wholeID", wholeID);
            // Split at colon, use second item in the array
            String[] splits = wholeID.split(":");
            if (splits.length == 2) {
                String id = splits[1];

                String[] column = {MediaStore.Images.Media.DATA};
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";

                Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);
                int columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        } else {
            filePath = uri.getPath();
        }

        return filePath;
    }

    public String uploadImage(String descriptionText, String expirationText, String idLieu) {
        File imgFile2 = null;

        if (TextUtils.isEmpty(descriptionText))
            return "Veuillez rentrer une desciption";
        else if (TextUtils.isEmpty(expirationText))
            return "Veuillez rentrer une date d'expiration";
        else if (picture != null) {
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/photo.png");

            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            picture.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imgFile2 = f;

        } else if (picture == null) {
            try {
                imgFile2 = new File(uriToFilename(uri));
            } catch (NullPointerException e) {
                return "Veuillez selectionner une image";
            }
        }


        OkHttpClient client = new OkHttpClient.Builder()
                .build();

//        System.out.println("MimeTypeMap.getFileExtensionFromUrl: " + MimeTypeMap.getFileExtensionFromUrl(imgFile2.getAbsolutePath()));
//        System.out.println(imgFile2.getAbsolutePath());

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imgFile2.getName(),
                        RequestBody.create(MediaType.parse("image/png"), imgFile2))
                .addFormDataPart("description", descriptionText)
                .addFormDataPart("dateExpiration", expirationText)
                .addFormDataPart("idUser", Infologin.getIdUser())
                .addFormDataPart("idLieu", idLieu)
                .build();

        Request request = new Request.Builder()
//                    .url("http://192.168.1.14:3000/api/lieu/")
                .url("http://90.8.219.224:3000/api/bonplan/")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("MB");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("GG");
            }
        });

        return "true";
    }


    /**
     * private void sendNotificationChannel(String title, String message, String channelId, int priority, Bitmap bitmap) {
     * NotificationCompat.Builder notif = new NotificationCompat.Builder(getApplicationContext(), channelId) //création de la notif
     * .setSmallIcon(R.drawable.logo)
     * .setLargeIcon(bitmap)
     * .setContentTitle(title)
     * .setContentText(message)
     * .setPriority(priority)
     * .setStyle(new NotificationCompat.BigPictureStyle()
     * .bigPicture(bitmap)
     * .bigLargeIcon(null));
     * NotificationActivity.getNotificationManager().notify(notifID, notif.build());
     * }
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "CAMERA authorization granted", Toast.LENGTH_LONG);
                    toast.show();
                    cameraFragment.takePicture();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "CAMERA authorization not granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            case REQUEST_MEDIA_WRITE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    storageFragment.saveToInternalStorage(picture);
                    Toast toast = Toast.makeText(getApplicationContext(), "write authorization granted", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "write authorization not granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            case REQUEST_MEDIA_READ: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "read authorization granted", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "read authorization not granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            break;
        }
    }

    //public void setImage(Bitmap bitmap){IVPreviewImage.setImageBitmap(bitmap);}

    /**
     * public void takePicture(){
     * Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
     * startActivity(intent);
     * }
     */

    @Override
    public void onPictureLoad(Bitmap bitmap) {
        cameraFragment.setImage(bitmap);
    }

    @Override
    public Bitmap getPictureToSave() {
        return picture;
    }

    public Bitmap getPicture() {
        return picture;
    }
}
