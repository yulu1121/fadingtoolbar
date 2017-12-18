package com.anshi.filechoice.ui;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.anshi.filechoice.R;
import com.anshi.filechoice.adapter.FileAdapter;
import com.anshi.filechoice.adapter.FileRecyclerAdapter;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * Created by yulu on 2017/11/28.
 */

public class FileActivity extends AppCompatActivity {
    private static final String ROOT_PATH = "/";
    //存储文件名称
    private ArrayList<String> mFileName = null;
    //存储文件路径
    private ArrayList<String> mFilePath = null;
    private FileAdapter mAdapter;
    private DrawerLayout drawerLayout;
    private ListView mListView;
    private RecyclerView recyclerView;
    private FileRecyclerAdapter adapter;
    private String parent;

    public static final String FILE_DATA = Environment.getExternalStorageDirectory()+File.separator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_activity_main);
        initView();
        showFileDir(FILE_DATA);
        clickListener();
    }

    private void initView() {
         recyclerView = (RecyclerView) findViewById(R.id.list);
         mListView = (ListView) findViewById(R.id.right_list);
         drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
         recyclerView.setLayoutManager(new GridLayoutManager(this,5));
    }


    /**
     * 扫描显示文件列表
     * @param path 路径
     */
    private void showFileDir(final String path) {
        mFileName = new ArrayList<String>();
        mFilePath = new ArrayList<String>();
        File file = new File(path);

        File[] files = file.listFiles();
        //如果当前目录不是根目录
        if (!ROOT_PATH.equals(path)) {
            mFileName.add("@1");
            mFilePath.add(ROOT_PATH);
            mFileName.add("@2");
            parent = file.getParent();
            mFilePath.add(parent);
        }
        //添加所有文件
        for (File f : files) {
            mFileName.add(f.getName());
            mFilePath.add(f.getPath());
        }
        adapter = new FileRecyclerAdapter(this,mFileName,mFilePath);
        recyclerView.setAdapter(adapter);
        mAdapter = new FileAdapter(this,mFileName,mFilePath);
        mListView.setAdapter(mAdapter);
        adapter.setOnItemClickListener(new FileRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String path = mFilePath.get(position);
                File file = new File(path);
                // 文件存在并可读
                if (file.exists() && file.canRead()) {
                    if (file.isDirectory()) {
                        //显示子目录及文件
                        showFileDir(path);
                        adapter.notifyDataSetChanged();
                    } else {
                        //处理文件
                        openFile(file);
                    }
                }
                //没有权限
                else {
                    new AlertDialog.Builder(FileActivity.this).setTitle("Message")
                            .setMessage("没有权限")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });
    }

    private  void clickListener(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = mFilePath.get(position);
                File file = new File(path);
                // 文件存在并可读
                if (file.exists() && file.canRead()) {
                    if (file.isDirectory()) {
                        //显示子目录及文件
                        showFileDir(path);
                        adapter.notifyDataSetChanged();
                    } else {
                        //处理文件
                        openFile(file);
                    }
                }
                //没有权限
                else {
                    new AlertDialog.Builder(FileActivity.this).setTitle("Message")
                            .setMessage("没有权限")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });
    }
    //打开文件
    private void openFile(File file) {
        String type = getMIMEType(file);
        switch (type){
            case "image/*":
               Intent  intentImage = new Intent(this,ImageActivity.class);
                intentImage.putExtra("picPath",file.getPath());
                startActivity(intentImage);
                break;
            case "application/msword":
                Intent intentDoc = new Intent(this,FileDisplayActivity.class);
                intentDoc.putExtra("path",file.getPath());
                startActivity(intentDoc);
                break;
            case "application/xls":
                FileDisplayActivity.show(this,file.getPath());
                break;
            case "application/vnd.ms-powerpoint":
                FileDisplayActivity.show(this,file.getPath());
                break;
            case "application/text/plain":
                FileDisplayActivity.show(this,file.getPath());
                break;
            default:
              Intent  intentOther = new Intent();
                intentOther.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentOther.setAction(android.content.Intent.ACTION_VIEW);
                intentOther.setDataAndType(Uri.fromFile(file), type);
                try{
                    startActivity(intentOther);
                }catch (ActivityNotFoundException e){
                    new AlertDialog.Builder(FileActivity.this).setTitle("提醒")
                            .setMessage("没有相关应用来处理文件")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    e.printStackTrace();
                }
                break;
        }

    }

    //获取文件mimetype
    private String getMIMEType(File file) {
        String type = "";
        String name = file.getName();
        //文件扩展名
        String end = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
        switch (end) {
            case "m4a":
            case "mp3":
            case "wav":
                type = "audio/*";
                break;
            case "mp4":
            case "3gp":
                type = "video/*";
                break;
            case "jpg":
            case "png":
            case "jpeg":
            case "bmp":
            case "gif":
                type = "image/*";
                break;
            case "doc":
                type = "application/msword";
                break;
            case "pptx":
            case "ppt":
                type = "application/vnd.ms-powerpoint";
                break;
            case "xls":
            case "xlsx":
                type="application/xls";
                break;
            case "txt":
                type="application/text/plain";
                break;
            default:
                //如果无法直接打开，跳出列表由用户选择
                type = "*/*";
                break;
        }
        return type;
    }

    public void moreMenu(View view) {
        if (drawerLayout.isDrawerOpen(Gravity.END)){
           drawerLayout.closeDrawers();
        }
        drawerLayout.openDrawer(Gravity.END);
    }

    public void back(View view) {
        finish();
    }
}
