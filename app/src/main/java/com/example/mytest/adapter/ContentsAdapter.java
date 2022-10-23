package com.example.mytest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.mytest.R;
import com.example.mytest.entity.Content;

import java.util.List;

public class ContentsAdapter extends ArrayAdapter<Content> {
    public ContentsAdapter(@NonNull Context context, int resource, @NonNull List<Content> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Content contents = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.contents_item,parent,false);

        TextView ids = view.findViewById(R.id.id);
        TextView title = view.findViewById(R.id.title);

        ids.setText(""+contents.getId());
        title.setText(contents.getTitle());

        return view;
    }

}
