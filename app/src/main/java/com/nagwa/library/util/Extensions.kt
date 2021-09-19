package com.nagwa.library.util

import android.app.Activity
import android.app.Fragment
import android.graphics.Bitmap
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.nagwa.library.R
import java.lang.reflect.ParameterizedType
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}
fun Any?.isNull(): Boolean = this == null

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun ImageView.loadImageFromURL(url: String?, progressBar: ProgressBar? = null) {
    if (url.isNullOrBlank()) {
        setImageResource(R.drawable.ic_broken_image)
        progressBar?.gone()
        return
    }
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    progressBar?.visible()
    Glide.with(this)
        .asBitmap()
        .load(url)
        .apply(requestOptions)
        .error(R.drawable.ic_broken_image)
        .addListener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean,
            ): Boolean {
                Timber.tag("load_image").e("${e?.message} url: $url")
                setImageResource(R.drawable.ic_broken_image)
                progressBar?.gone()
                return true
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: com.bumptech.glide.load.DataSource?,
                isFirstResource: Boolean,
            ): Boolean {
                setImageBitmap(resource)
                progressBar?.gone()
                return true
            }

        })
        .into(this)
}


//Binding View (Activity - Fragment) on the fly
//usually used in base Fragment/Activity
@Suppress("UNCHECKED_CAST")
fun <B : ViewDataBinding> LifecycleOwner.bindView(container: ViewGroup? = null): B {
    return if (this is Activity) {
        val inflateMethod = getTClass<B>().getMethod("inflate", LayoutInflater::class.java)
        val invokeLayout = inflateMethod.invoke(null, this.layoutInflater) as B
        this.setContentView(invokeLayout.root)
        invokeLayout
    } else {
        val fragment = this as Fragment
        val inflateMethod = getTClass<B>().getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}!")
        }
        val invoke: B = inflateMethod.invoke(null, layoutInflater, container, false) as B
        invoke
    }
}

fun <T : Any?, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(if (this is Fragment) viewLifecycleOwner else this, {
        if (lifecycle.currentState == Lifecycle.State.RESUMED) {
            body(it)
        }
    })
}

//get Generic Class Type
fun <T : Any> Any.getTClass(): Class<T> {
    return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
}


fun FragmentActivity.showErrorDialog(message: String?) {
    MaterialAlertDialogBuilder(this)
        .setTitle(getString(R.string.error))
        .setMessage(message)
        .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
        .show()
}


fun Double?.roundTo(n : Int) : Double {
    return "%.${n}f".format(Locale.ENGLISH,this).toDouble()
}


fun String.isValidUrl(): Boolean {
    return try {
        URLUtil.isValidUrl(this) && Patterns.WEB_URL.matcher(this).matches()
    } catch (e: Exception) {
        Timber.e(e)
        false
    }
}

inline fun <reified T : AppCompatActivity> Fragment.castToActivity(
    callback: (T?) -> Unit,
): T? {
    return if (requireActivity() is T) {
        callback(requireActivity() as T)
        requireActivity() as T
    } else {
        Timber.e("class cast exception, check your fragment is in the correct activity")
        callback(null)
        null
    }

}
