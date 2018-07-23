package com.zhy.sample_okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by kevin on 16/4/20.
 */
public class ImageCompressUtil {

    /**
     * 根据图片路径进行压缩图片
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getimage(String srcPath, int size) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了,表示只返回宽高
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        //当前图片宽高
        float w = newOpts.outWidth;
        float h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
            //有时会出现be=3.2或5.2现象，如果不做处理压缩还会失败
            if ((newOpts.outWidth / ww) > be) {

                be += 1;
            }
            //be = Math.round((float) newOpts.outWidth / (float) ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
            if ((newOpts.outHeight / hh) > be) {

                be += 1;
            }
            //be = Math.round((float) newOpts.outHeight / (float) hh);
        }
        if (be <= 0) {

            be = 1;
        }
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap, size);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 压缩图片
     *
     * @param image
     * @param size
     * @return
     */
    public static Bitmap compressImage(Bitmap image, int size) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;

        while ((baos.toByteArray().length / 1024) >= size) {  //循环判断如果压缩后图片是否大于等于size,大于等于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 5;//每次都减少5
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * Bitmap转byte数组
     *
     * @param bitmap
     * @return
     */
    public static byte[] compressBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;//
        }
        /* 取得相片 */
        Bitmap tempBitmap = bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        tempBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);// 如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
        byte[] byteData = baos.toByteArray();
        return byteData;
    }
}

