package com.tht.tht.data.local.entity


import com.google.gson.annotations.SerializedName

data class TermsEntity(
    @SerializedName("body")
    val body: List<Body>
) {
    data class Body(
        @SerializedName("content")
        val content: List<Content>,
        @SerializedName("require")
        val require: Boolean,
        @SerializedName("title")
        val title: String
    ) {
        data class Content(
            @SerializedName("content")
            val content: String,
            @SerializedName("title")
            val title: String
        )
    }
}
