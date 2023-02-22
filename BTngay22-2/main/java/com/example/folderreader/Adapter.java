package com.example.folderreader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class Adapter extends BaseAdapter {

    private Context context;
    private List<FileInfo> list_files;

    public Adapter(Context context,List<FileInfo> list_files)
    {
        this.context = context;
        this.list_files = list_files;
    }
    @Override
    public int getCount() {
        return list_files.size();
    }

    @Override
    public Object getItem(int i) {
        return list_files.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ItemViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_item, null);
            viewHolder = new ItemViewHolder();
            viewHolder.icon = view.findViewById(R.id.imageView);
            viewHolder.path = view.findViewById(R.id.textView);
            view.setTag(viewHolder);
        } else
            viewHolder = (ItemViewHolder) view.getTag();

        FileInfo item = list_files.get(i);
        if(item.isFile()){
            viewHolder.icon.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
        }else{
            viewHolder.icon.setImageResource(R.drawable.ic_baseline_folder_24);
        }
        viewHolder.path.setText(item.getPath().getName());

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!item.isFile()) {
//                    Intent i = new Intent(context, SecondActivity.class);
//                    i.putExtra("path",item.getPath());
//                    context.startActivity(i);
//                }
//            }
//        });

        return view;
    }

    static class ItemViewHolder{
        ImageView icon;
        TextView path;
    }
}
