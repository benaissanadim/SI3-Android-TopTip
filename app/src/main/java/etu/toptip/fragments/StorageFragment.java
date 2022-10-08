package etu.toptip.fragments;

import android.Manifest;
import android.content.ContentValues;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import etu.toptip.R;

public class StorageFragment extends Fragment {

    private IStorageActivity storageActivity;
    private Button buttonSave;
    private Button buttonLoad;

    public String getPicturename() {
        return picturename;
    }

    public void setPicturename(String picturename) {
        this.picturename = picturename;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    private String picturename;
    private String directoryName;

    public StorageFragment(){
    }

    public StorageFragment(IStorageActivity activity){
        this.storageActivity=activity;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_storage, container, false);
        picturename="_test.jpg";
        ContextWrapper contextWrapper = new ContextWrapper(getContext());
        directoryName=contextWrapper.getDir("imageDirectory",ContextWrapper.MODE_PRIVATE).getPath();

        buttonSave = view.findViewById(R.id.camera_savebutton);
        buttonLoad = view.findViewById(R.id.camera_loadbutton);
        setDisabledSaveButton();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap picture = storageActivity.getPictureToSave();
                if (picture != null) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},IStorageActivity.REQUEST_MEDIA_WRITE);
                    }else {
                        saveToInternalStorage(picture);
                        setDisabledSaveButton();
                    }

                }
            }
        });

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap picture = storageActivity.getPictureToSave();
                if (picture != null) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},IStorageActivity.REQUEST_MEDIA_READ);
                    }else {
                        storageActivity.onPictureLoad(loadImageFromStorage());
                    }

                }
            }
        });

        return view;
    }

    public Bitmap loadImageFromStorage() {
        try {
            File file = new File(directoryName,picturename);
            Toast.makeText(getContext(),"Picture load",Toast.LENGTH_LONG).show();
            return BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveToInternalStorage(Bitmap picture) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, picturename);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/*");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, directoryName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
        File file = new File(directoryName,picturename);
        try{
            FileOutputStream fout = new FileOutputStream(file);
            picture.compress(Bitmap.CompressFormat.PNG,90,fout);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setDisabledSaveButton(){buttonSave.setEnabled(false);}
    public void setEnabledSaveButton(){buttonSave.setEnabled(true);}

    public void setDisabledLoadButton(){buttonLoad.setEnabled(false);}
    public void setEnabledLoadButton(){buttonLoad.setEnabled(true);}
}
