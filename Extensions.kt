import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import java.util.*

//easier value animator
fun Int.animateTo(end: Int, duration: Long = 400, func: (value: Int)-> Unit){
    ValueAnimator.ofInt(this, end).apply {
        this.duration = duration
        addUpdateListener { valueAnimator ->
            func(valueAnimator.animatedValue as Int)
        }
        start()
    }
}

//delays an action by "delay" seconds
fun delay(delay: Long, func: () -> Unit) {
    val handler = Handler()
    handler.postDelayed({
        try {
            func()
        } catch (e: Exception) {
            println(e.toString())
        }
    }, delay)
}

//repeats an action every "delay" seconds
infix fun (() -> Any).every(delay: Long) {
    this()
    val handler = Handler()
    val runnable = object : Runnable {
        override fun run() {
            try {
                this@every()
            } catch (e: Exception) {
            }
            handler.postDelayed(this, delay)
        }
    }
    handler.postDelayed(runnable, delay)
}

//loops an action every "interval" seconds
fun loop(interval: Long, f: () -> Unit) {
    async(UI) {
        while (true) {
            f()
            delay(interval)
        }
    }
}

//easier try catch
fun <T> tryWith(catch: T, func: () -> T): T {
    return try {
        func()
    } catch (e: Exception) {
        catch
    }
}

//resizes a drawable given a height and keeps it's aspect ratio
fun Drawable.resize(height: Float = 20f): Drawable {
    (this as BitmapDrawable)
    val bitmapResized = Bitmap.createScaledBitmap(bitmap, ((intrinsicWidth*height)/intrinsicHeight).convertToPx().toInt(), height.convertToPx().toInt(), false)
    return BitmapDrawable(Resources.getSystem(), bitmapResized)
}

//inflates a layout in a view
fun ViewGroup.inflate(layout: Int) = LayoutInflater.from(context).inflate(layout,this, false)

//finds a view in a ViewGroup
fun <T : View> ViewGroup.find(layout: Int) = this.findViewById<T>(layout)

//sets a text to TextView or hides the TextView if the text is null or empty
fun TextView.setTextOrHide(s: String?) {
    return if (s.isNullOrEmpty() || s.isNullOrBlank()) {
        this.visibility = View.GONE
    } else {
        this.text = s
    }
}

//sets a fond to a TextView given the font name
fun TextView.setFont(name: String) {
    val typeface = Typeface.createFromAsset(context.applicationContext.assets,
            String.format(Locale.US, "fonts/%s", name))
    this.typeface = typeface
}

//sets the tint of an ImageView
fun ImageView.setTint(colorId: Int) {
    setColorFilter(ContextCompat.getColor(context, colorId), android.graphics.PorterDuff.Mode.SRC_ATOP)
}

//focuses an EditText and opens up the keyboard
fun EditText.focus() {
    isFocusableInTouchMode = true
    requestFocus()
    setSelection(text.toString().length)
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

}

//fades in a view
fun View.fadeIn() {
    alpha = 0f
    animate().alpha(1f)
}

//sets the background tint of a view
fun View.setBackgroundTint(colorId: Int) {
    background?.setColorFilter(ContextCompat.getColor(context, colorId), android.graphics.PorterDuff.Mode.SRC_ATOP)
}

//easier animation of views
fun View.animation(func:suspend  ViewPropertyAnimator.() -> Unit) {
    async(UI) {
        animate().func()
    }
}

//opens up a browser given a url
fun Context.openUrl(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) })
}

//gets the screen width
fun Activity.getScreenWidth(): Int {
    val size = Point()
    windowManager.defaultDisplay.getSize(size)
    return size.x
}


//gets the screen height
fun Activity.getScreenHeight(): Int {
    val size = Point()
    windowManager.defaultDisplay.getSize(size)
    return size.y
}

//converts pixels to DP
fun Float.convertToDp(): Float {
    val metrics = Resources.getSystem().displayMetrics
    val dp = this / (metrics.densityDpi / 160f)
    return Math.round(dp).toFloat()
}

fun Float.convertToPx(): Float {
    val metrics = Resources.getSystem().displayMetrics
    val px = this * (metrics.densityDpi / 160f)
    return Math.round(px).toFloat()
}
