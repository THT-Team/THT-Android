package tht.feature.signin.signup.moreinfo

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
        companion object {
            fun from(smoke: String): Smoke? {
                return when (smoke) {
                    None.name -> None
                    SomeTime.name -> SomeTime
                    Almost.name -> Almost
                    else -> null
                }
            }
        }
    }
    enum class Drink {
        None,
        SomeTime,
        Almost;
        companion object {
            fun from(smoke: String): Drink?{
                return when (smoke) {
                    None.name -> None
                    SomeTime.name -> SomeTime
                    Almost.name -> Almost
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
