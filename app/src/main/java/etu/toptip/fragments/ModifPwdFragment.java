package etu.toptip.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import etu.toptip.R;
import etu.toptip.controller.ModifPasswordController;
import etu.toptip.controller.ModifProfilController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModifPwdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModifPwdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView titre;
    EditText oldPass, newPass;
    ModifPasswordController modifPasswordController;

    public ModifPwdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModifPwdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModifPwdFragment newInstance(String param1, String param2) {
        ModifPwdFragment fragment = new ModifPwdFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modif_pwd, container, false);

        this.titre = view.findViewById(R.id.idErreurModifPwd);
        this.titre.setText("ok");

        this.oldPass = view.findViewById(R.id.pwd1);
        this.newPass = view.findViewById(R.id.pwd2);

        Button buttonOK = view.findViewById(R.id.idBtn);
        buttonOK.setText("JE SUIS UN TEST");
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });




        return view;
    }
}