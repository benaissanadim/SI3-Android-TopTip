package etu.toptip.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import etu.toptip.R;
import etu.toptip.activities.CameraActivity;
import etu.toptip.activities.LoginActivity;
import etu.toptip.helper.InfoCompteThread;
import etu.toptip.helper.Infologin;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment implements FragmentChangeListener {

    Button infoButton;
    Button histButton;
    Button supprimerCompteButton;
    Button decoButton;

    public TextView email, pseudo;
    public String EMAIL, PSEUDO;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
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
        View view = inflater.inflate(R.layout.fragment_profil, container, false);


        try {
            String url = "http://90.8.219.224:3000/api/user/" + Infologin.getIdUser();

            InfoCompteThread infoCompteThread = new InfoCompteThread();
            AsyncTask<String, Integer, JSONObject> execute;
            execute = infoCompteThread.execute(url);

            this.email = view.findViewById(R.id.emailInfoCompteET);
            this.EMAIL = execute.get().getString("email");
            this.email.setText(EMAIL);
            this.pseudo = view.findViewById(R.id.pseudoInfoCompteET);
            this.PSEUDO = execute.get().getString("userName");
            this.pseudo.setText(this.PSEUDO);

        } catch (InterruptedException | ExecutionException | JSONException e) {
            Log.d("Emile", "EROR: " + e.toString());
            e.printStackTrace();
        }

        Button modifProfil = view.findViewById(R.id.idBtnModifProfil);
        modifProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment modif = new ModifProfilFragment();
                replaceFragment(modif);
            }

        });

        Button modifMdp = view.findViewById(R.id.idBtnModifMdp);
        modifMdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment modif = new ModifPasswordFragment();
                replaceFragment(modif);
            }

        });


        supprimerCompteButton = view.findViewById(R.id.idButtonSupprimerCompte);
        supprimerCompteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // à ajouter
            }
        });

        histButton = view.findViewById(R.id.idButtonHistorique);
        histButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new HistoriqueFragment());
            }
        });

        // reglagesButton = view.findViewById(R.id.idButtonRéglages);


        decoButton = view.findViewById(R.id.idButtonDeconnexion);
        decoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), LoginActivity.class);
                startActivity(myIntent);
            }
        });

        decoButton = view.findViewById(R.id.idButtonSupprimerCompte);
        decoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient client = new OkHttpClient();

                String url = "http://90.8.219.224:3000/api/user/" + Infologin.getIdUser();
//                String url = "http://192.168.1.14:3000/api/user/" + Infologin.getIdUser();

                Request request = new Request.Builder()
                        .url(url)
                        .delete()
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        Intent myIntent = new Intent(getContext(), LoginActivity.class);
                        startActivity(myIntent);
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("pseudo", this.PSEUDO);//value= my value from code
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }
}