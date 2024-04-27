package tht.feature.signin.religion

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
        companion object {
            fun from(religion: String): Religion? {
                return when (religion) {
                    None.name -> None
                    Christianity.name -> Christianity
                    Buddhism.name -> Buddhism
                    Catholic.name -> Catholic
                    WonBuddhism.name -> WonBuddhism
                    Extra.name -> Extra
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
