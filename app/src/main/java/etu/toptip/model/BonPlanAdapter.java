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

public class BonPlanAdapter extends BaseAdapter {

    private Context context;
    private List<BonPlan> plans;
    private LayoutInflater inflater;
    private IListner listner;


    public BonPlanAdapter(Context context, List<BonPlan> plans) {
        this.context = context;
        this.plans = plans;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return plans.size();
    }

    @Override
    public BonPlan getItem(int i) {
        return plans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LinearLayout view;
        view = (LinearLayout) (convertView == null ? inflater.inflate(R.layout.adapter_bonplan, parent, false) : convertView);
        BonPlan current = getItem(i);
        String description = current.getDescription();
        String date = current.getDate();
        String image = current.getImage();

        TextView detailsView = view.findViewById(R.id.plan_description);
        TextView dateView = view.findViewById(R.id.plan_date);
        ImageView imageView = view.findViewById(R.id.plan_icon);

        detailsView.setText(description);
        dateView.setText("date expiration: "+ date);
        Picasso.get().load(image).into(imageView);

        return view;
    }

    public void addListner(IListner listner) {
        this.listner = listner;
    }
}