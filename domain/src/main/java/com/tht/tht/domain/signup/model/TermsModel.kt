package com.tht.tht.domain.signup.model

data class TermsModel(
    val title: String,
    val key: String,
    val description: String?,
    val require: Boolean,
    val link: String?
)
