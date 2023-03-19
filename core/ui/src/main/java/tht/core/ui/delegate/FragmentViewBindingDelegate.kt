package tht.core.ui.delegate

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <A : Fragment, T : ViewBinding> A.viewBinding(
    crossinline vbFactory: (LayoutInflater) -> T,
    crossinline inflaterProvider: (A) -> LayoutInflater = Fragment::getLayoutInflater,
): FragmentViewBindingDelegate<A, T> = viewBinding { fragment: A -> vbFactory(inflaterProvider(fragment)) }

fun <A : Fragment, T : ViewBinding> A.viewBinding(
    viewBinder: (A) -> T,
): FragmentViewBindingDelegate<A, T> = FragmentViewBindingDelegate(this, viewBinder)

class FragmentViewBindingDelegate<A: Fragment, T : ViewBinding>(
    val fragment: A,
    val vbFactory: (A) -> T
) : ReadOnlyProperty<A, T> {
    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            /**
             * release binding when fragment lifecycle is on destroy
             */
            val viewLifecycleOwnerLiveDataObserver =
                Observer<LifecycleOwner?> {
                    val viewLifecycleOwner = it ?: return@Observer

                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }

            /**
             * add and remove viewLifecycleOwnerLiveDataObserver
             */
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
            }
        })
    }

    override fun getValue(thisRef: A, property: KProperty<*>): T {
        if (binding != null) {
            return binding as T
        }

        /**
         * Checking the fragment lifecycle
         */
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
        }

        /**
         * Bind layout
         */
        binding = vbFactory(thisRef)

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
