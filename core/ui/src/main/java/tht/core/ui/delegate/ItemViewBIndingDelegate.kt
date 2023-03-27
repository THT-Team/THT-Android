package tht.core.ui.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import tht.core.ui.extension.getLayoutInflater
import kotlin.reflect.KProperty

/**
 * reference : https://zhuinden.medium.com/simple-one-liner-viewbinding-in-fragments-and-activities-with-kotlin-961430c6c07c
 */

inline fun <A : ViewGroup, T : ViewBinding> A.viewBinding(
    crossinline vbFactory: (LayoutInflater, ViewGroup, Boolean) -> T,
    crossinline inflaterProvider: (A) -> LayoutInflater = View::getLayoutInflater
): ItemViewBindingDelegate<A, T> = viewBinding { view: A, parent: ViewGroup, attachToParent: Boolean ->
    vbFactory(inflaterProvider(view), parent, attachToParent)
}

fun <A : ViewGroup, T : ViewBinding> A.viewBinding(
    viewBinder: (A, ViewGroup, Boolean) -> T,
): ItemViewBindingDelegate<A, T> = ItemViewBindingDelegate(this, viewBinder)

class ItemViewBindingDelegate<A : ViewGroup, T : ViewBinding>(
    val view: A,
    val viewBinder: (A, ViewGroup, Boolean) -> T,
) {
    /**
     * initiate variable for binding view
     */
    private var binding: T? = null

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: A, property: KProperty<*>): T {
        binding?.let { return it }

        /**
         * Bind layout
         */
        binding = viewBinder(thisRef, view, true)

        /**
         * Check binding instance nonnull
         */
        binding.requireViewBinding()

        /**
         * Return binding layout
         */
        return binding as T
    }
}

private fun <T : ViewBinding> T?.requireViewBinding() {
    if (this == null) error("ViewBinding instance must be not null")
}
