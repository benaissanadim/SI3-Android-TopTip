package etu.toptip.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import etu.toptip.IListner;
import etu.toptip.R;
import etu.toptip.activities.AddPlaceActivity;

import etu.toptip.helper.ListPlacesThread;
import etu.toptip.model.Place;
import etu.toptip.model.PlaceAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccueilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccueilFragment extends Fragment implements IListner, FragmentChangeListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView listView;

    private int selectedFilter = -1;
    private String currentSearchText = "";
    private SearchView searchView;
    ListPlacesThread places;
    TextView text;
    LottieAnimationView lottie;
    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccueilFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccueilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccueilFragment newInstance(String param1, String param2) {
        AccueilFragment fragment = new AccueilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            places = new ListPlacesThread();
            places.execute("http://90.8.219.224:3000/api/lieu"); //2 endroits ou on actualise les lieux
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        try {
            places.get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

       view = inflater.inflate(R.layout.fragment_accueil, container, false);
        listView = view.findViewById(R.id.place_list_view);
        PlaceAdapter adap = new PlaceAdapter(container.getContext(), places.getPlaces());

        text = (TextView) view.findViewById(R.id.textSearch) ;
        lottie = view.findViewById(R.id.search);

        text.setVisibility(view.INVISIBLE);
        lottie.setVisibility(view.INVISIBLE);
        if(places.getPlaces().size() ==0){
            text.setVisibility(view.VISIBLE);
            lottie.setVisibility(view.VISIBLE);
        }

        listView.setAdapter(adap);
        adap.addListner(this);
        initSearchWidgets(places, view);

        //supermarchés
        Button buttonA = (Button) view.findViewById(R.id.lieux);
        Button buttonR = (Button) view.findViewById(R.id.resturants);
        Button buttonB = (Button) view.findViewById(R.id.boulangeries);
        Button buttonS = (Button) view.findViewById(R.id.supermarchés);
        Button buttonBoucherie = (Button) view.findViewById(R.id.boucherie);

        buttonS.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                filterList(0);
                selectedFilter = 0;
                buttonR.setSelected(false);
                buttonB.setSelected(false);
                buttonS.setSelected(true);
                buttonA.setSelected(false);
                buttonBoucherie.setSelected(false);

            }
        });

        buttonR.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                filterList(2);
                selectedFilter = 2;
                buttonR.setSelected(true);
                buttonB.setSelected(false);
                buttonS.setSelected(false);
                buttonA.setSelected(false);
                buttonBoucherie.setSelected(false);

            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                filterList(5);
                selectedFilter = 5;
                buttonB.setSelected(true);
                buttonR.setSelected(false);
                buttonS.setSelected(false);
                buttonA.setSelected(false);
                buttonBoucherie.setSelected(false);
            }
        });

        buttonBoucherie.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                filterList(4);
                selectedFilter = 4;
                buttonB.setSelected(false);
                buttonR.setSelected(false);
                buttonS.setSelected(false);
                buttonA.setSelected(false);
                buttonBoucherie.setSelected(true);

            }
        });

        buttonA.setSelected(true);
        buttonA.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                buttonA.setSelected(true);
                buttonB.setSelected(false);
                buttonR.setSelected(false);
                buttonS.setSelected(false);
                buttonBoucherie.setSelected(false);
                allFilter();
            }
        });


        Button addBP = (Button) view.findViewById(R.id.BAjouterBP);
        addBP.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent myIntent = new Intent(container.getContext(), AddPlaceActivity.class);
                startActivity(myIntent);
            }

        });

        return view;
    }

    @Override
    public void OnClickPlace(Place place) {
        Fragment placeDetails = new PlaceDetails();
        Bundle bundle = new Bundle();
        bundle.putParcelable("place", (Parcelable) place);
        placeDetails.setArguments(bundle);
        replaceFragment(placeDetails);
    }


    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    public void initSearchWidgets(ListPlacesThread places, View view) {
        searchView = view.findViewById(R.id.idSearchView2);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currentSearchText = s;
                ArrayList<Place> filtredPlaces = new ArrayList<>();
                for (Place place : places.getPlaces()) {
                    if (place.getVille().toLowerCase().contains(s.toLowerCase())) {
                        if (selectedFilter == -1) filtredPlaces.add(place);
                        else if (place.getType() == selectedFilter) filtredPlaces.add(place);
                    }
                }
                PlaceAdapter p = new PlaceAdapter(getContext().getApplicationContext(), filtredPlaces);
                listView.setAdapter(p);
                p.addListner(AccueilFragment.this);
                text.setVisibility(view.INVISIBLE);
                lottie.setVisibility(view.INVISIBLE);
                if(filtredPlaces.size() ==0){
                    text.setVisibility(view.VISIBLE);
                    lottie.setVisibility(view.VISIBLE);
                }

                return false;
            }
        });
    }

    private void filterList(int status) {
        selectedFilter = status;
        ArrayList<Place> filtredPlaces = new ArrayList<>();
        for (Place place : places.getPlaces()) {
            if (place.getType() == status) {
                if (currentSearchText.equals("")) {
                    filtredPlaces.add(place);
                } else if (place.getVille().toLowerCase().contains(currentSearchText.toLowerCase())) {
                    filtredPlaces.add(place);
                }
            }
        }
        PlaceAdapter placeAdapter = new PlaceAdapter(getContext().getApplicationContext(), filtredPlaces);
        text.setVisibility(view.INVISIBLE);
        lottie.setVisibility(view.INVISIBLE);
        if(filtredPlaces.size() ==0){
            text.setVisibility(view.VISIBLE);
            lottie.setVisibility(view.VISIBLE);
        }
        listView.setAdapter(placeAdapter);
        placeAdapter.addListner(this);

    }

    public void allFilter() {
        searchView.setQuery("", false);
        searchView.clearFocus();
        selectedFilter = -1;
        PlaceAdapter placeAdapter = new PlaceAdapter(getContext().getApplicationContext(), places.getPlaces());
        text.setVisibility(view.INVISIBLE);
        lottie.setVisibility(view.INVISIBLE);
        if(places.getPlaces().size() ==0){
            text.setVisibility(view.VISIBLE);
            lottie.setVisibility(view.VISIBLE);
        }
        listView.setAdapter(placeAdapter);
        placeAdapter.addListner(this);

    }
}