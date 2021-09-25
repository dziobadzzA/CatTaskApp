package com.example.cattaskapp.fragment

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference


class SaveImage(context: Context) : AsyncTask<String, Unit, Unit>() {

    private val mContext: WeakReference<Context> = WeakReference(context)

    override fun doInBackground(vararg params: String?) {

        var name = params[0]?.split('/')
        name = (name?.get(name.size - 1))?.split('.')

        val isName = name?.get(0)

        val requestOptions = RequestOptions().override(100)
                .downsample(DownsampleStrategy.CENTER_INSIDE)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)

        mContext.get()?.let {

            val bitmap = Glide.with(it)
                    .asBitmap()
                    .load(params[0])
                    .apply(requestOptions)
                    .submit()
                    .get()


            val any = try {

                val fileCash = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Cats$isName.jpg")
                val cache = FileOutputStream(fileCash)

                bitmap.compress(Bitmap.CompressFormat.JPEG , 100, cache)

                cache.flush()
                cache.close()

                Log.i("tag work", "Image saved.")

            } catch (e: Exception) {
                Log.i("tag work", "Failed to save image.")
            }
            any
        }

    }

}