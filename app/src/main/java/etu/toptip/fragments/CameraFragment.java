package etu.toptip.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import etu.toptip.R;
import etu.toptip.activities.AddPlaceActivity;

public class CameraFragment extends Fragment {

    ImageView image;
    Bitmap bitmap;

    public CameraFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        image = view.findViewById(R.id.click_image);

        Button btnCamera = view.findViewById(R.id.BCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, ICameraPermission.REQUEST_CAMERA);
                }else{
                    takePicture();
                }
            }
        });

        return view;
    }

    public void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityIfNeeded(intent, ICameraPermission.REQUEST_CAMERA);
    }

    public void setImage(Bitmap bitmap){image.setImageBitmap(bitmap);}
    public Bitmap getBitmap() {
        return bitmap;
    }
}
