package etu.toptip.fragments;

import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import etu.toptip.R;
import etu.toptip.helper.Infologin;
import etu.toptip.helper.InfoCompteThread;

public class InformationsCompteFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TextView email, pseudo;
    public String EMAIL, PSEUDO;

    public InformationsCompteFragment() {
    }

    public static InformationsCompteFragment newInstance(String param1, String param2) {
        InformationsCompteFragment fragment = new InformationsCompteFragment();
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
        View view = inflater.inflate(R.layout.fragment_infocompte, container, false);

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
        modifMdp.setText("WEfgwegweg");
        modifMdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment modif = new ModifPasswordFragment();
                replaceFragment(modif);
            }

        });
        return view;
    }

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
