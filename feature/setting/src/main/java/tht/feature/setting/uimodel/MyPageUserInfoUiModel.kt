package tht.feature.setting.uimodel

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class MyPageUserInfoUiModel(
    val userUuid: String,
    val username: String,
    val userProfilePhotos: ImmutableList<UserProfilePhoto>,
    val birth: String,
    val gender: Gender,
    val introduction: String,
    val preferredGender: Gender,
    val height: Int,
    val smoke: String,
    val drink: String,
    val religion: String,
    val idealTypeList: ImmutableList<IdealType>,
    val interestsList: ImmutableList<Interests>
) {
    enum class Gender {
        Male,
        FeMale,
        UnKnown
    }

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
        fun isPrimaryImage(): Boolean = priority <= 2
    }

    companion object {
        val EMPTY: MyPageUserInfoUiModel
            get() = MyPageUserInfoUiModel(
                userUuid = "",
                username = "",
                introduction = "",
                userProfilePhotos = persistentListOf(),
                birth = "",
                gender = Gender.Male,
                preferredGender = Gender.Male,
                height = 0,
                smoke = "",
                drink = "",
                religion = "",
                idealTypeList = persistentListOf(),
                interestsList = persistentListOf()
            )
    }
}
