package me.shetj.share.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import java.io.ByteArrayOutputStream;

public final class ImageUtils {

    private ImageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * bitmap 转 bytes
     *
     * @param bitmap bitmap 对象
     * @param format 格式
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final CompressFormat format) {
        if (bitmap == null) {
	        return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }




}
