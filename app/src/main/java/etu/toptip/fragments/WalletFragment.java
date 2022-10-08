package etu.toptip.fragments;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;

import java.util.concurrent.TimeUnit;

import etu.toptip.R;
import etu.toptip.activities.AddWalletActivity;
import etu.toptip.helper.Infologin;
import etu.toptip.helper.ListBonPlanThread;
import etu.toptip.helper.ListFavoriThread;
import etu.toptip.helper.ListPlacesThread;
import etu.toptip.helper.ListWalletThread;
import etu.toptip.model.ListWallet;
import etu.toptip.model.WalletAdapter;

public class WalletFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    TextView text;
    LottieAnimationView lottie;

    public WalletFragment() {
    }

    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        // Inflate the layout for this fragment
        ListWalletThread wallet = null;

        GridView listView = view.findViewById(R.id.wallet);

        String url = "http://90.8.219.224:3000/api/carte/" + Infologin.getIdUser();

        try {
            wallet = new ListWalletThread();
            wallet.execute(url);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        try {
            wallet.get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        text = (TextView) view.findViewById(R.id.textSearch) ;
        lottie = view.findViewById(R.id.search);

        text.setVisibility(view.INVISIBLE);
        lottie.setVisibility(view.INVISIBLE);
        if(wallet.getListWallet().size() ==0){
            text.setVisibility(view.VISIBLE);
            lottie.setVisibility(view.VISIBLE);
        }


        WalletAdapter adap = new WalletAdapter(container.getContext(), wallet.getListWallet());
        listView.setAdapter(adap);
        Button addBP = (Button) view.findViewById(R.id.add);
        addBP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(container.getContext(), AddWalletActivity.class);
                startActivity(myIntent);
            }

        });
        return view;
    }
}
