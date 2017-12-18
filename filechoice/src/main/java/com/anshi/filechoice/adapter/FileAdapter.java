package com.anshi.filechoice.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anshi.filechoice.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * Created by yulu on 2017/11/28.
 */

public class FileAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    //存储文件名称
    private ArrayList<String> names = null;
    //存储文件路径
    private ArrayList<String> paths = null;

    //参数初始化
    public FileAdapter(Context context, ArrayList<String> na, ArrayList<String> pa) {
        names = na;
        paths = pa;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return null==names?0:names.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.file, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.textView);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        File f = new File(paths.get(position));
        if (names.get(position).equals("@1")) {
            holder.text.setText("返回根目录");
            Glide.with(context).load(R.drawable.album).into(holder.image);
        } else if (names.get(position).equals("@2")) {
            holder.text.setText("返回上一层目录");
            Glide.with(context).load(R.drawable.album).into(holder.image);
        } else {
            String name = f.getName();
            holder.text.setText(name);
            if (f.isDirectory()) {
                Glide.with(context).load(R.drawable.album).into(holder.image);
            } else if (f.isFile()) {
                holder.text.setText(name);
                String end = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();;
                switch (end){
                    case "m4a":
                    case "mp3":
                    case "wav":
                        Glide.with(context).load(R.drawable.audio).into(holder.image);
                        break;
                    case "mp4":
                    case "3gp":
                        Glide.with(context).load(R.drawable.video).into(holder.image);
                        break;
                    case "doc":
                        Glide.with(context).load(R.drawable.doc).into(holder.image);
                        break;
                    case "txt":
                        Glide.with(context).load(R.drawable.txt).into(holder.image);
                        break;
                    case "jpg":
                    case "png":
                    case "jpeg":
                    case "bmp":
                    case "gif":
                        Glide.with(context).load(f.getPath()).into(holder.image);
                        break;
                    case "ppt":
                    case "pptx":
                        Glide.with(context).load(R.drawable.ppt).into(holder.image);
                        break;
                    case "xls":
                    case "xlsx":
                        Glide.with(context).load(R.drawable.excel).into(holder.image);
                        break;
                    default:
                        Glide.with(context).load(R.drawable.unknown).into(holder.image);
                        break;
                }
            } else {
                Log.e("xxx",name);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView text;
        private ImageView image;
    }

    private Bitmap small(Bitmap map, float num) {
        Matrix matrix = new Matrix();
        matrix.postScale(num, num);
        return Bitmap.createBitmap(map, 0, 0, map.getWidth(), map.getHeight(), matrix, true);
    }
}
