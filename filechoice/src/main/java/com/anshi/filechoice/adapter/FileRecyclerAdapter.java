package com.anshi.filechoice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anshi.filechoice.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

/**
 *
 * Created by yulu on 2017/11/29.
 */

public class FileRecyclerAdapter extends RecyclerView.Adapter<FileViewHolder> implements View.OnClickListener {
    private List<String> fileName;
    private List<String> filePath;
    private Context context;
    public FileRecyclerAdapter(Context context,List<String> fileName,List<String> filePath){
        this.context = context;
        this.fileName = fileName;
        this.filePath = filePath;
    }
    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false);
        FileViewHolder fileViewHolder = new FileViewHolder(view);
        view.setOnClickListener(this);
        return fileViewHolder;
    }
    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public  interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        String fileNames = fileName.get(position);
        String filePaths = filePath.get(position);
        File f = new File(filePaths);
        if (fileNames.equals("@1")){
            holder.textView.setText("返回根目录");
            Glide.with(context).load(R.drawable.album).into(holder.imageView);
        }else if (fileNames.equals("@2")){
            holder.textView.setText("返回上一层目录");
            Glide.with(context).load(R.drawable.album).into(holder.imageView);
        }else {
            String name = f.getName();
            holder.textView.setText(name);
            if (f.isDirectory()){
                Glide.with(context).load(R.drawable.album).into(holder.imageView);
            }else if (f.isFile()){
                holder.textView.setText(name);
                String end = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
                switch (end){
                    case "m4a":
                    case "mp3":
                    case "wav":
                        Glide.with(context).load(R.drawable.audio).into(holder.imageView);
                        break;
                    case "mp4":
                    case "3gp":
                        Glide.with(context).load(R.drawable.video).into(holder.imageView);
                        break;
                    case "doc":
                        Glide.with(context).load(R.drawable.doc).into(holder.imageView);
                        break;
                    case "txt":
                        Glide.with(context).load(R.drawable.txt).into(holder.imageView);
                        break;
                    case "jpg":
                    case "png":
                    case "jpeg":
                    case "bmp":
                    case "gif":
                        Glide.with(context).load(f.getPath()).into(holder.imageView);
                        break;
                    case "ppt":
                    case "pptx":
                        Glide.with(context).load(R.drawable.ppt).into(holder.imageView);
                        break;
                    case "xls":
                    case "xlsx":
                        Glide.with(context).load(R.drawable.excel).into(holder.imageView);
                        break;
                    default:
                        Glide.with(context).load(R.drawable.unknown).into(holder.imageView);
                        break;
                }
            }else {
                Log.e("xxx", name);
            }
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null==fileName?0:fileName.size();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
}
