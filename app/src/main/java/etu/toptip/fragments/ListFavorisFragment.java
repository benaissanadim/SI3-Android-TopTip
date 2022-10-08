package etu.toptip.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import etu.toptip.IListner;
import etu.toptip.R;
import etu.toptip.helper.Infologin;
import etu.toptip.helper.ListFavoriThread;
import etu.toptip.model.Place;
import etu.toptip.model.PlaceAdapter;

public class ListFavorisFragment extends Fragment implements IListner {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    TextView text;
    LottieAnimationView lottie;


    public ListFavorisFragment() {
    }

    public static ListFavorisFragment newInstance(String param1, String param2) {
        ListFavorisFragment fragment = new ListFavorisFragment();
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
        View view = inflater.inflate(R.layout.fragment_listefavoris, container, false);

        ListFavoriThread favoris = new ListFavoriThread();
//        String url = "http://192.168.1.14:3000/api/favori/" + Infologin.getIdUser();
        String url = "http://90.8.219.224:3000/api/favori/" + Infologin.getIdUser();
        favoris.execute(url);

        try {
            favoris.get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }


        ListView listView = view.findViewById(R.id.place_fav_list_view);
        PlaceAdapter adap = new PlaceAdapter(container.getContext(), ListFavoriThread.getFavoris());
        listView.setAdapter(adap);
        text = (TextView) view.findViewById(R.id.textSearch) ;
        lottie = view.findViewById(R.id.search);

        text.setVisibility(view.INVISIBLE);
        lottie.setVisibility(view.INVISIBLE);
        if(ListFavoriThread.getFavoris().size() ==0){
            text.setVisibility(view.VISIBLE);
            lottie.setVisibility(view.VISIBLE);
        }


        adap.addListner(this);

        return view;
    }

    @Override
    public void OnClickPlace(Place place) {
    }
}
