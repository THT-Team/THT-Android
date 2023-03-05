package tht.core.ui.binding

import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KProperty

/**
 * reference : https://zhuinden.medium.com/simple-one-liner-viewbinding-in-fragments-and-activities-with-kotlin-961430c6c07c
 */

inline fun <A : ComponentActivity, T : ViewBinding> A.viewBinding(
    crossinline vbFactory: (LayoutInflater) -> T,
    crossinline inflaterProvider: (A) -> LayoutInflater = ComponentActivity::getLayoutInflater,
): ActivityViewBindingDelegate<A, T> = viewBinding { activity: A -> vbFactory(inflaterProvider(activity)) }

fun <A : ComponentActivity, T : ViewBinding> A.viewBinding(
    viewBinder: (A) -> T,
): ActivityViewBindingDelegate<A, T> = ActivityViewBindingDelegate(viewBinder)

class ActivityViewBindingDelegate<A : ComponentActivity, T : ViewBinding>(
    val viewBinder: (A) -> T,
) {
    /**
     * initiate variable for binding view
     */
    private var binding: T? = null

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: A, property: KProperty<*>): T {
        binding?.let { return it }

        /**
         * Checking the activity lifecycle
         */
        val currentState = thisRef.lifecycle.currentState
        if (!currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Unsupported Activity Lifecycle for ViewBinding Access. Current State: $currentState")
        }

        /**
         * Bind layout
         */
        binding = viewBinder(thisRef)

        /**
         * Check binding instance nonnull
         */
        binding.requireViewBinding()

        /**
         * Set the content view
         */
        thisRef.setContentView(binding!!.root)

        /**
         * Return binding layout
         */
        return binding as T
    }
}

private fun <T : ViewBinding> T?.requireViewBinding() {
    if (this == null) error("ViewBinding instance must be not null")
}
