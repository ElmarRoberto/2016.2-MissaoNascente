package com.example.jbbmobile.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.BooksController;

public class CustomAdapter extends BaseAdapter{

    private String [] nameElement;
    private Context context;
    private int [] imageId;
    private int idBook;
    private static LayoutInflater inflater = null;
    private int[] idElements;

    public CustomAdapter(AlmanacScreenActivity mainActivity, BooksController booksController, int idBook) {
        this.idElements = booksController.getElementsId(idBook);
        this.nameElement = booksController.getElementsForBook(idBook);
        this.imageId = booksController.getElementsImage(mainActivity, idBook);
        this.context = mainActivity;
        this.idBook = idBook;

        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return nameElement.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class Holder
    {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(nameElement[position]);
        holder.img.setImageResource(imageId[position]);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent elementIntent = new Intent(CustomAdapter.this.context, ElementScreenActivity.class);
                elementIntent.putExtra("idElement", idElements[position]);
                elementIntent.putExtra("idBook", idBook);
                CustomAdapter.this.context.startActivity(elementIntent);
            }
        });

        return rowView;
    }
}