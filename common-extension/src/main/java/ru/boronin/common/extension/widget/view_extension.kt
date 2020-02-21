package ru.boronin.common.extension.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.annotation.AnimRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import ru.boronin.common.extension.core.hideKeyboard
import ru.boronin.common.utils.DEFAULT_INT

fun View.hideKeyboard() {
  context?.hideKeyboard(this)
}

fun View?.setMargin(
  start: Int = marginStart,
  top: Int = topMargin,
  end: Int = marginEnd,
  bottom: Int = bottomMargin
) {
  reapplyLayoutParams {
    with(it as? ViewGroup.MarginLayoutParams) {
      this?.marginStart = start
      this?.topMargin = top
      this?.marginEnd = end
      this?.bottomMargin = bottom
    }
  }
}

val View?.marginStart: Int
  get() = (this?.layoutParams as? ViewGroup.MarginLayoutParams)?.marginStart ?: DEFAULT_INT

val View?.topMargin: Int
  get() = (this?.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin ?: DEFAULT_INT

val View?.marginEnd: Int
  get() = (this?.layoutParams as? ViewGroup.MarginLayoutParams)?.marginEnd ?: DEFAULT_INT

val View?.bottomMargin: Int
  get() = (this?.layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: DEFAULT_INT

fun View?.reapplyLayoutParams(layoutParamsFun: (ViewGroup.LayoutParams) -> Unit) {
  this?.layoutParams
    ?.also { layoutParamsFun.invoke(it) }

  this?.requestLayout()
}

fun View.findViewTraversal(predicate: (View) -> Boolean): View? {
  if (predicate(this)) {
    return this
  }

  var result: View? = null
  if (this is ViewGroup) {
    traversalView {
      if (predicate(it)) {
        result = it
      }
    }
  }

  return result
}

fun ViewGroup.traversalView(action: (View) -> Unit) {
  for (i in 0 until childCount) {
    val child = getChildAt(i)
    action.invoke(child)

    if (child is ViewGroup) {
      child.traversalView(action)
    }
  }
}

fun TextView.addTextWatcher(
  beforeTextChangedFun: ((text: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null,
  onTextChangedFun: ((text: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null,
  afterTextChangedFun: ((text: Editable?) -> Unit)? = null
) = object : TextWatcher {
  override fun afterTextChanged(s: Editable?) {
    afterTextChangedFun?.invoke(s)
  }

  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    beforeTextChangedFun?.invoke(s, start, count, after)
  }

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    onTextChangedFun?.invoke(s, start, before, count)
  }
}.apply { addTextChangedListener(this) }

fun View.focusChangeListener(focusFun: (Boolean) -> Unit) {
  setOnFocusChangeListener { _, hasFocus -> focusFun.invoke(hasFocus) }
}

fun View.enterKeyListener(keyFun: () -> Unit) {
  onKeyListener(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER, keyFun)
}

fun View.onKeyListener(keyEvent: Int, keyCode: Int, keyFun: () -> Unit) {
  setOnKeyListener { _, code, event ->
    if (keyEvent == event.action && code == keyCode) {
      keyFun.invoke()
      true
    } else {
      false
    }
  }
}

fun View.fadeOutIn(middleFun: () -> Unit) {
  val fadeOut = ObjectAnimator.ofFloat(this, "alpha", 1f, .0f)
  fadeOut.duration = 100L
  fadeOut.addListener(object : AnimatorListenerAdapter() {
    override fun onAnimationEnd(animation: Animator) {
      super.onAnimationEnd(animation)
      middleFun.invoke()
    }
  })

  val fadeIn = ObjectAnimator.ofFloat(this, "alpha", .0f, 1f)
  fadeIn.duration = 100L

  val mAnimationSet = AnimatorSet()
  mAnimationSet.play(fadeIn).after(fadeOut)
  mAnimationSet.start()
}

fun View.getDimensionPixelSize(@DimenRes res: Int) = context?.resources
  ?.getDimensionPixelSize(res) ?: DEFAULT_INT

fun View.getFont(@FontRes res: Int) = ResourcesCompat.getFont(context, res)
fun View.getColor(@ColorRes res: Int) = ContextCompat.getColor(context, res)
fun View.getColorStateList(@ColorRes res: Int) = ContextCompat.getColorStateList(context, res)

fun View.startAnimation(@AnimRes anim: Int) {
  val animation = AnimationUtils.loadAnimation(context, anim)
  startAnimation(animation)
}