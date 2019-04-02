package me.shetj.record.utils;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import me.shetj.base.tools.file.SDCardUtils;
import me.shetj.base.tools.time.TimeUtil;

public class Util {

    private Util() {
    }


    public static boolean isBrightColor(int color) {
        if(android.R.color.transparent == color) {
            return true;
        }
        int [] rgb = {Color.red(color), Color.green(color), Color.blue(color)};
        int brightness = (int) Math.sqrt(
                rgb[0] * rgb[0] * 0.241 +
                        rgb[1] * rgb[1] * 0.691 +
                        rgb[2] * rgb[2] * 0.068);
        return brightness >= 200;
    }

    public static int getDarkerColor(int color) {
        float factor = 0.8f;
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(a,
                Math.max((int) (r * factor), 0),
                Math.max((int) (g * factor), 0),
                Math.max((int) (b * factor), 0));
    }

    public static String formatSeconds(int seconds) {
        return getTwoDecimalsValue(seconds / 3600) + ":"
                + getTwoDecimalsValue(seconds / 60) + ":"
                + getTwoDecimalsValue(seconds % 60);
    }

    public static String formatSeconds2(int seconds) {
        return
                getTwoDecimalsValue(seconds / 60) + ":"
                        + getTwoDecimalsValue(seconds % 60);
    }

    private static String getTwoDecimalsValue(int value) {
        if (value >= 0 && value <= 9) {
            return "0" + value;
        } else {
            return value + "";
        }
    }

    /**
     * 返回合并后的文件的路径名,默认放在第一个文件的目录下
     */
    public static String heBingMp3(String path,String path1) {
        try {

            String fenLiData = fenLiData(path);
            String fenLiData2 = fenLiData(path1);
            File file = new File(fenLiData);
            File file1 = new File(fenLiData2);
            String hebing = SDCardUtils.getPath("testRecord") + "/" + TimeUtil.getTime() + ".mp3";
            File file2 = new File(hebing);
            FileInputStream in = new FileInputStream(file);
            FileOutputStream out = new FileOutputStream(file2);
            byte bs[] = new byte[1024 * 4];
            int len = 0;
            //先读第一个
            while ((len = in.read(bs)) != -1) {
                out.write(bs, 0, len);
            }
            in.close();
            out.close();
            //再读第二个
            in = new FileInputStream(file1);
            out = new FileOutputStream(file2, true);//在文件尾打开输出流
            len = 0;
            byte bs1[] = new byte[1024 * 4];
            while ((len = in.read(bs1)) != -1) {
                out.write(bs1, 0, len);
            }
            in.close();
            out.close();
            if (file.exists()) file.delete();
            if (file1.exists()) file1.delete();
            return file2.getAbsolutePath();
        }catch (Exception e){
            return null;
        }
    }
    /**
     * 返回分离出MP3文件中的数据帧的文件路径
     *
     * @作者 胡楠启
     *
     */
    public static String fenLiData(String path) throws IOException {
        File file = new File(path);// 原文件
        File file1 = new File(path + "01");// 分离ID3V2后的文件,这是个中间文件，最后要被删除
        File file2 = new File(path + "001");// 分离id3v1后的文件
        RandomAccessFile rf = new RandomAccessFile(file, "rw");// 随机读取文件
        FileOutputStream fos = new FileOutputStream(file1);
        byte ID3[] = new byte[3];
        rf.read(ID3);
        String ID3str = new String(ID3);
        // 分离ID3v2
        if (ID3str.equals("ID3")) {
            rf.seek(6);
            byte[] ID3size = new byte[4];
            rf.read(ID3size);
            int size1 = (ID3size[0] & 0x7f) << 21;
            int size2 = (ID3size[1] & 0x7f) << 14;
            int size3 = (ID3size[2] & 0x7f) << 7;
            int size4 = (ID3size[3] & 0x7f);
            int size = size1 + size2 + size3 + size4 + 10;
            rf.seek(size);
            int lens = 0;
            byte[] bs = new byte[1024*4];
            while ((lens = rf.read(bs)) != -1) {
                fos.write(bs, 0, lens);
            }
            fos.close();
            rf.close();
        } else {// 否则完全复制文件
            int lens = 0;
            rf.seek(0);
            byte[] bs = new byte[1024*4];
            while ((lens = rf.read(bs)) != -1) {
                fos.write(bs, 0, lens);
            }
            fos.close();
            rf.close();
        }
        RandomAccessFile raf = new RandomAccessFile(file1, "rw");
        byte TAG[] = new byte[3];
        raf.seek(raf.length() - 128);
        raf.read(TAG);
        String tagstr = new String(TAG);
        if (tagstr.equals("TAG")) {
            FileOutputStream fs = new FileOutputStream(file2);
            raf.seek(0);
            byte[] bs=new byte[(int)(raf.length()-128)];
            raf.read(bs);
            fs.write(bs);
            raf.close();
            fs.close();
        } else {// 否则完全复制内容至file2
            FileOutputStream fs = new FileOutputStream(file2);
            raf.seek(0);
            byte[] bs = new byte[1024*4];
            int len = 0;
            while ((len = raf.read(bs)) != -1) {
                fs.write(bs, 0, len);
            }
            raf.close();
            fs.close();
        }
        if (file1.exists())// 删除中间文件
        {
            file1.delete();

        }
        return file2.getAbsolutePath();
    }



    public static void showMPTime2( String mRecordFile) {
        MediaPlayer	mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if (!TextUtils.isEmpty(mRecordFile)) {
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(mRecordFile);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(mp ->{
                   Log.i("record","时间长度"+mp.getDuration());
                    mp.release();
                    mp = null;
                });
                mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                    mp.reset();
                    return true;
                });
            } catch (IllegalArgumentException | IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}