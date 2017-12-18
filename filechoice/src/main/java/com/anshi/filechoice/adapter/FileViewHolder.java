package com.anshi.filechoice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anshi.filechoice.R;

/**
 *
 * Created by yulu on 2017/11/29.
 */

public class FileViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;

    public FileViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.file_image);
        textView  = itemView.findViewById(R.id.file_text);
    }


}
