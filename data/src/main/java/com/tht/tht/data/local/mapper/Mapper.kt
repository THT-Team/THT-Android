package com.tht.tht.data.local.mapper

import com.tht.tht.data.local.entity.TermsEntity
import com.tht.tht.domain.signup.model.TermsModel

// 확장 함수
fun TermsEntity.Body.toModel(): TermsModel {
    return TermsModel(
        title = title,
        content = content.map {
            TermsModel.TermsContent(
                title = it.title,
                content = it.content
            )
        },
        require = require
    )
}
