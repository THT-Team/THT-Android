package com.tht.tht.data.remote.service

import android.net.Uri
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * reference: https://firebase.google.com/docs/storage/android/upload-files?hl=ko
 * https://aries574.tistory.com/261
 */
class ImageServiceImpl @Inject constructor() : ImageService {

    override suspend fun uploadImage(uri: Uri, saveFileName: String): String {
        val storage = Firebase.storage.reference
        val fileRef = storage.child(saveFileName)
        return suspendCancellableCoroutine { continuation ->
            fileRef.putFile(uri)
                .addOnCompleteListener {
                    it.exception?.let { e ->
                        Log.e("TAG", "image upload => onFailureListener")
                        e.printStackTrace()
                        continuation.resumeWithException(e)
                    }
                    it.result.metadata?.name?.let { fileName ->
                        storage.child(fileName).downloadUrl
                            .addOnFailureListener { e ->
                                Log.e("TAG", "image downloadUrl => onFailureListener")
                                e.printStackTrace()
                                continuation.resumeWithException(e)
                            }.addOnSuccessListener { uri ->
                                continuation.resume(uri.toString())
                            }
                    }
                }
        }
    }
}
