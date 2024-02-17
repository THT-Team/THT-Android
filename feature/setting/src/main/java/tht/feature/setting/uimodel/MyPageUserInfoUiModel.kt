package tht.feature.setting.uimodel

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class MyPageUserInfoUiModel(
    val address: String,
    val age: Int,
    val email: String,
    val idealTypeList: ImmutableList<IdealType>,
    val interestsList: ImmutableList<Interests>,
    val introduction: String,
    val phoneNumber: String,
    val userProfilePhotos: ImmutableList<UserProfilePhoto>,
    val userUuid: String,
    val username: String
) {
    @Immutable
    data class IdealType(
        val emojiCode: String,
        val idx: Int,
        val name: String
    )
    @Immutable
    data class Interests(
        val emojiCode: String,
        val idx: Int,
        val name: String
    )
    @Immutable
    data class UserProfilePhoto(
        val priority: Int,
        val url: String
    ) {
        fun isPrimaryImage(): Boolean = priority < 2
    }

     companion object {
         val EMPTY: MyPageUserInfoUiModel
             get() = MyPageUserInfoUiModel(
                 address = "",
                 age = -1,
                 email = "",
                 idealTypeList = persistentListOf(),
                 interestsList = persistentListOf(),
                 introduction = "",
                 phoneNumber = "",
                 userProfilePhotos = persistentListOf(),
                 userUuid = "",
                 username = "",
             )
     }
}

