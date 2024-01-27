package com.tht.tht.data.local.service

import com.tht.tht.data.local.entity.TermsEntity

interface TermsService {
    fun fetchTerms(): TermsEntity
}
