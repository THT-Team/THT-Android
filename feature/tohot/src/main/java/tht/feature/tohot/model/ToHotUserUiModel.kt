package tht.feature.tohot.model

import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel
import javax.annotation.concurrent.Immutable

@Immutable
data class ToHotUserUiModel(
    val nickname: String,
    val birthday: String,
    val interests: ImmutableListWrapper<InterestModel>,
    val idealTypes: ImmutableListWrapper<IdealTypeModel>,
    val age: Int,
    val address: String,
    val profileImgUrl: ImmutableListWrapper<String>,
    val introduce: String
)
