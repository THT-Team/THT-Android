package com.tht.tht.domain.signup.model

data class TermsModel(
    val title: String,
    val key: String,
    val content: List<TermsContent>,
    val description: String,
    val require: Boolean
) : java.io.Serializable {
    data class TermsContent(
        val title: String,
        val content: String
    ) : java.io.Serializable
}
