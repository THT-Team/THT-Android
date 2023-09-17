package tht.feature.tohot.model

import androidx.compose.runtime.Immutable
import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel

@Immutable
data class ToHotUserUiModel(
    val id: String,
    val idx: Int,
    val nickname: String,
    val isBirthday: Boolean,
    val interests: ImmutableListWrapper<InterestModel>,
    val idealTypes: ImmutableListWrapper<IdealTypeModel>,
    val age: Int,
    val address: String,
    val profileImgUrl: ImmutableListWrapper<String>,
    val introduce: String
)
