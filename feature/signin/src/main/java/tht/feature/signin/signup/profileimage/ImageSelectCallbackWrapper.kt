package tht.feature.signin.signup.profileimage

import androidx.activity.result.ActivityResultCallback

class ImageSelectCallbackWrapper<T> : ActivityResultCallback<T> {

    var callback: ActivityResultCallback<T>? = null

    override fun onActivityResult(result: T) {
        callback?.onActivityResult(result)
    }
}
