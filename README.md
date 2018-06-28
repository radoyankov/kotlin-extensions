# kotlin-extensions
These are some extension functions that I've found useful while developing Android app with Kotlin

## All Examples


Easier ValueAnimator with optional duration
```kotlin
0.animateTo(66) {
  println(it)
}

66.animateTo(end = 0, duration = 600) {
  println(it)
}
```

Easier animation of views
```kotlin
some_view.animation{
  scaleY(1.2f).duration = 200
  delay(200)
  scaleX(1f).duration = 400
  rotation(20f)
  delay(200)
  alpha(0)
}
```

Delaying actions
```kotlin
delay(1000){
  println("prints after 1 second")
}
```

Looping actions
```kotlin
{
  println("this prints every 2 seconds)
} every 2000
```

Another version of it
```kotlin
loop(1000){
  println("another variation of a loop. Uses coroutines")
}
```

Resizing a drawable. Keeps it's aspect ratio.
```kotlin
val newDrawable = oldDrawable.resize(255)
```

Finds a view inside another view. Useful when kotlinx is not available.
```kotlin
val textView = constraint_layout.find(R.id.text_view)
```

Sets a text view or hides it
```kotlin
val text = "order 66"
text_view.setTextOrHide(text)
```

Sets a font to a text view
```kotlin
text_view.setFont("GoogleProduct.ttf")
```

Sets the tint color of the text view
```kotlin
image_view.setTint(Color.RED)
```

Focuses an edit text and shows the keyboard
```kotlin
edit_text.focus()
```

Fades in a view
```kotlin
some_view.fadeIn()
```

Sets the background tint of a view
```kotlin
some_view.setBackgroundTint(Color.BLUE)
```

Opens a url in a browser
```kotlin
context.openUrl("www.github.com/RadoslavYankov")
```

Gets w/h of the screen
```kotlin
val w = activity.getScreenWidth()
val h = activity.getScreenHeight()
```

Converts DP to pixels and the other way around
```kotlin
200f.convertToDp()
200f.convertToPx()
```
