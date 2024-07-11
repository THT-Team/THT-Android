package tht.feature.signin.signup.moreinfo

import com.tht.tht.domain.signup.model.SignupUserModel
import tht.core.ui.base.UiState

data class MoreInfoUiState(
    val loading: Boolean,
    val smoke: Smoke?,
    val drink: Drink?
) : UiState {
    enum class Smoke {
        None,
        SomeTime,
        Almost;
        fun toDomain(): SignupUserModel.Smoke {
            return when (this) {
                None -> SignupUserModel.Smoke.NONE
                SomeTime -> SignupUserModel.Smoke.SOMETIMES
                Almost -> SignupUserModel.Smoke.FREQUENTLY
            }
        }
        companion object {
            fun from(smoke: SignupUserModel.Smoke?): Smoke? {
                return when (smoke) {
                    SignupUserModel.Smoke.NONE -> None
                    SignupUserModel.Smoke.SOMETIMES -> SomeTime
                    SignupUserModel.Smoke.FREQUENTLY -> Almost
                    else -> null
                }
            }
        }
    }
    enum class Drink {
        None,
        SomeTime,
        Almost;
        fun toDomain(): SignupUserModel.Drink {
            return when (this) {
                None -> SignupUserModel.Drink.NONE
                SomeTime -> SignupUserModel.Drink.SOMETIMES
                Almost -> SignupUserModel.Drink.FREQUENTLY
            }
        }
        companion object {
            fun from(drink: SignupUserModel.Drink?): Drink? {
                return when (drink) {
                    SignupUserModel.Drink.NONE -> None
                    SignupUserModel.Drink.SOMETIMES -> SomeTime
                    SignupUserModel.Drink.FREQUENTLY -> Almost
                    else -> null
                }
            }
        }
    }

    companion object {
        val default: MoreInfoUiState get() = MoreInfoUiState(
            loading = false,
            smoke = null,
            drink = null
        )
    }
}
