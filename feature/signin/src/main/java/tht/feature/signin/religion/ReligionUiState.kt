package tht.feature.signin.religion

import com.tht.tht.domain.signup.model.SignupUserModel
import tht.core.ui.base.UiState

data class ReligionUiState(
    val loading: Boolean,
    val religion: Religion?
) : UiState {
    enum class Religion {
        None,
        Christianity,
        Buddhism,
        Catholic,
        WonBuddhism,
        Extra;

        fun toDomain(): SignupUserModel.Religion {
            return when(this) {
                None -> SignupUserModel.Religion.NONE
                Christianity -> SignupUserModel.Religion.CHRISTIAN
                Buddhism -> SignupUserModel.Religion.BUDDHISM
                Catholic -> SignupUserModel.Religion.CATHOLICISM
                WonBuddhism -> SignupUserModel.Religion.WON_BUDDHISM
                Extra -> SignupUserModel.Religion.OTHER
            }
        }
        companion object {
            fun from(religion: SignupUserModel.Religion?): Religion? {
                return when (religion) {
                    SignupUserModel.Religion.NONE -> None
                    SignupUserModel.Religion.CHRISTIAN -> Christianity
                    SignupUserModel.Religion.BUDDHISM -> Buddhism
                    SignupUserModel.Religion.CATHOLICISM -> Catholic
                    SignupUserModel.Religion.WON_BUDDHISM -> WonBuddhism
                    SignupUserModel.Religion.OTHER -> Extra
                    else -> null
                }
            }
        }
    }
    companion object {
        val default: ReligionUiState get() = ReligionUiState(
            loading = false,
            religion = null
        )
    }
}
