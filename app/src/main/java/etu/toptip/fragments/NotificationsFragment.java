package etu.toptip.fragments;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import etu.toptip.R;
import etu.toptip.controller.NotificationsController;
import etu.toptip.views.NotificationsView;

public class NotificationsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        NotificationsView notificationsView = new NotificationsView(view);
        NotificationsController notificationsController = new NotificationsController(this.getActivity(), notificationsView);
        notificationsView.setController(notificationsController);

        return view;
    }
}
