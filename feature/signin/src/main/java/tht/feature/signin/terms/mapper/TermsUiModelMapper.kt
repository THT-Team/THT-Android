package tht.feature.signin.terms.mapper

import com.tht.tht.domain.signup.model.TermsModel
import tht.feature.signin.terms.uimodel.TermsUiModel

fun TermsModel.toUiModel(): TermsUiModel {
    return TermsUiModel(
        title = title,
        key = key,
        description = description,
        require = require,
        link = link,
        isSelect = require
    )
}

fun TermsUiModel.toModel(): TermsModel {
    return TermsModel(
        title = title,
        key = key,
        description = description,
        require = require,
        link = link
    )
}
