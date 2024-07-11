package com.tht.tht.data.remote.response.terms

import com.google.gson.annotations.SerializedName

class TermsResponse : ArrayList<TermsResponse.TermsResponseItem>() {
    data class TermsResponseItem(
        @SerializedName("name")
        val key: String,
        @SerializedName("subject")
        val title: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("isRequired")
        val isRequired: Boolean,
        @SerializedName("detailLink")
        val link: String?
    )
}
