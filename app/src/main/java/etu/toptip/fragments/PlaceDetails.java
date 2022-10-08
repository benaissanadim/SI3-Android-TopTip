package etu.toptip.fragments;

import static java.lang.Integer.parseInt;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import etu.toptip.IListner;
import etu.toptip.R;
import etu.toptip.activities.AddBonPlanActivity;
import etu.toptip.helper.Infologin;
import etu.toptip.helper.ListBonPlanThread;
import etu.toptip.helper.ListPlacesThread;
import etu.toptip.model.BonPlan;
import etu.toptip.model.BonPlanAdapter;
import etu.toptip.model.Place;
import etu.toptip.model.PlaceAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaceDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceDetails extends Fragment implements IListner, FragmentChangeListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    OkHttpClient client;
    ListBonPlanThread bonplan;

    public PlaceDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaceDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaceDetails newInstance(String param1, String param2) {
        PlaceDetails fragment = new PlaceDetails();
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
        View view = inflater.inflate(R.layout.fragment_place_details, container, false);
        Bundle bundle = this.getArguments();
        Place model = bundle.getParcelable("place");

        ListView listView = view.findViewById(R.id.bonplan_list_view);

        String url = "http://90.8.219.224:3000/api/bonplan/lieu/" + model.getId();

        try {
            bonplan = new ListBonPlanThread();
            bonplan.execute(url);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        try {
            bonplan.get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //tu ajoute ici les bon plan obtenu
        ArrayList<BonPlan> plans = ListBonPlanThread.getListBonPlan();
        BonPlanAdapter adap = new BonPlanAdapter(container.getContext(), plans);
        listView.setAdapter(adap);


        TextView nameView = view.findViewById(R.id.name);
        nameView.setText(model.getName());

        TextView detailsView = view.findViewById(R.id.description);
        detailsView.setText(model.getVille());

        ImageView imageView = view.findViewById(R.id.icon);
        Picasso.get().load(model.getImage()).into(imageView);


        Button addToFavs = (Button) view.findViewById(R.id.BAjouterAuxFavs);

        Button add = (Button) view.findViewById(R.id.BAjouterBP);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(container.getContext(), AddBonPlanActivity.class);
                myIntent.putExtra("idLieu", model.getId());
                startActivity(myIntent);
            }
        });

        ImageButton retour =  view .findViewById(R.id.btn);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AccueilFragment());
            }
        });


        addToFavs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("idLieu", model.getId())
                        .add("idUser", Infologin.getIdUser())
                        .build();

                Request request = new Request.Builder()
//                        .url("http://192.168.1.14:3000/api/favori")
                        .url("http://90.8.219.224:3000/api/favori")
                        .post(requestBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            System.out.println("response PlaceDetail: " + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }

        });

        return view;
    }

    @Override
    public void OnClickPlace(Place place) {
    }

    @Override
    public void replaceFragment(Fragment fragment) {
    }
}