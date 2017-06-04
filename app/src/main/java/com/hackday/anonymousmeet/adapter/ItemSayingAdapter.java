package com.hackday.anonymousmeet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hackday.anonymousmeet.Entity.ItemSaying;
import com.hackday.anonymousmeet.R;

import java.util.List;

/**
 * Created by Liu jiaohan on 2017-06-04.
 */

public class ItemSayingAdapter extends ArrayAdapter<ItemSaying> {
    private int resourceId;

    public ItemSayingAdapter(Context context, int resource, List<ItemSaying> list) {
        super(context, resource, list);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemSaying itemSaying=getItem(position);
        View view;

        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else {
            view=convertView;
        }

        TextView txContent= (TextView) view.findViewById(R.id.item_saying_content);
        TextView txLikeAmount= (TextView) view.findViewById(R.id.item_likeamount);

        txContent.setText(itemSaying.getContent());

        txLikeAmount.setText(itemSaying.getLikeamount());

        return view;
    }
}
