package ru.boronin.core.api.navigator

interface NavigatorHandler {

  //Base
  fun open(deepLink: String)

  fun open(obj: Any?, tag: String = "")
  fun openForResult(obj: Any?, requestCode: Int, tag: String = "")

  /**
   * The call will be forwarded to its top.
   * If that controller doesn't handle it, then it will be popped.
   *
   * @return Whether or not a back action was handled by the Router
   */
  fun back()

  fun backWithDeliveryResult()

  /**
   * Pops all controllers until the Controller with the passed tag is at the top
   */
  fun backToTag(tag: String, deliveryResult: Boolean = false)

  /**
   * Pops all controllers until the Controller with the passed root
   */
  fun backToRoot(deliveryResult: Boolean = false)

  /**
   * Pops the passed controller from the backstack
   *
   * @param controller The controller that should be popped from this Router
   * @return Whether or not this Router still has controllers remaining on it after popping.
   */
  fun pop(obj: Any?)

  fun setRoot(obj: Any?)

  fun replace(obj: Any)
  fun getStackSize(): Int
}