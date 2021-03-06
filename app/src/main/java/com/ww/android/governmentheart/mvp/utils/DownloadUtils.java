package com.ww.android.governmentheart.mvp.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.ww.android.governmentheart.BuildConfig;

import java.io.File;

public class DownloadUtils {
    //下载器
    private DownloadManager downloadManager;
    //上下文
    private Context mContext;
    //下载的ID
    private long downloadId;
    private String name;
    private File mFile;

    public DownloadUtils(Context context) {
        this.mContext = context;
    }

    //下载apk
    public void downloadAPK(String url, String name) {
        this.name = name;

        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false);

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("新版本Apk");
        request.setDescription("Apk Downloading");
        request.setVisibleInDownloadsUi(true);

        //设置下载的路径
        mFile = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), name);
        request.setDestinationUri(Uri.fromFile(mFile));


        //获取DownloadManager
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        downloadId = downloadManager.enqueue(request);

        //注册广播接收者，监听下载状态
        mContext.registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //广播监听下载的各个状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };


    //检查下载状态
    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //下载完成安装APK
                    installAPK();
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        c.close();
    }

    //下载到本地后执行安装
    private void installAPK() {
        //获取下载文件的Uri
        if (mFile != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //+ File.separator + name
                Log.e("file path", mFile.getAbsolutePath());
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri apkUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID+".fileprovider", mFile);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");
            }
            mContext.startActivity(intent);
            mContext.unregisterReceiver(receiver);
        }


//        if (Build.VERSION.SDK_INT >= 24) {
//
//            //参数1 上下文；参数2 Provider主机地址 authorities 和配置文件中保持一致 ；参数3  共享的文件
//            Uri apkUri = FileProvider.getUriForFile(mContext, "com.xxx.xxx.xxx", downloadFileUri);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//        } else {
//            intent.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
//
//        }


    }
}