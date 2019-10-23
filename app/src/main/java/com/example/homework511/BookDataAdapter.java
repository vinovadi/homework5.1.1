package com.example.homework511;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class BookDataAdapter extends BaseAdapter {

    private List<BookData> items;
    private LayoutInflater inflater;

    BookDataAdapter(Context context, List<BookData> items) {
        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void addItem(BookData item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public BookData getItem(int position) {
        if (position < items.size()) {
            return items.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_book, parent, false);
        }
        BookData bookData = items.get(position);

        ImageView image = view.findViewById(R.id.imgBook);
        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        Button deleteBtn = view.findViewById(R.id.deleteBtn);

        image.setImageDrawable(bookData.getImage());
        title.setText(bookData.getTitle());
        subtitle.setText(bookData.getSubtitle());

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO change so that is deletes data from the textfile
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}