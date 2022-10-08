package etu.toptip.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import etu.toptip.IListner;
import etu.toptip.R;


public class WalletAdapter extends BaseAdapter {

    private Context context;
    private List<Wallet> wallets;
    private LayoutInflater inflater;


    public WalletAdapter(Context context, List<Wallet> wallets) {
        this.context = context;
        this.wallets = wallets;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return wallets.size();
    }

    @Override
    public Wallet getItem(int i) {
        return wallets.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LinearLayout view;
        view = (LinearLayout) (convertView == null ? inflater.inflate(R.layout.adapterwallet, parent, false) : convertView);
        Wallet current = getItem(i);
        String name = current.getName();
        String image = current.getImage();

        TextView nameView = view.findViewById(R.id.name);
        nameView.setText(name);

        ImageView imageView = view.findViewById(R.id.wallet_icon);
        Picasso.get().load(image).into(imageView);

        return view;
    }

}