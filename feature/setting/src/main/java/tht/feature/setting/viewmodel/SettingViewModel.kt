package tht.feature.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.setting.usecase.FetchSettingManageItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tht.feature.setting.delegate.ParseSettingManageEventDelegate
import tht.feature.setting.uimodel.SettingListItemUiModel
import tht.feature.setting.uimodel.event.SettingManageEvent
import tht.feature.setting.uimodel.SettingSectionUiModel
import tht.feature.setting.uimodel.mapper.toUiModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    parseSettingManageEventDelegate: ParseSettingManageEventDelegate,
    private val fetchSettingSectionModel: FetchSettingManageItemsUseCase
) : ViewModel(), ParseSettingManageEventDelegate by parseSettingManageEventDelegate {

    data class State(
        val loading: Boolean,
        val items: ImmutableList<SettingSectionUiModel>
    )

    sealed interface SideEffect {
        object NavigateAccountManage : SideEffect
    }

    private val _state = MutableStateFlow(
        State(
            loading = false,
            items = persistentListOf()
        )
    )
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(loading = true) }
            fetchSettingSectionModel().collect { res ->
                res.onSuccess { items ->
                    _state.update { state ->
                        state.copy(
                            items = items.map { it.toUiModel() }.toImmutableList()
                        )
                    }
                }.onFailure {
                    it.printStackTrace()
                }
                _state.update { it.copy(loading = false) }
            }
        }
    }

    fun onSettingItemClick(item: SettingListItemUiModel) {
        kotlin.runCatching {
            parseEvent(item.key)
        }.onSuccess {
            when (it) {
                SettingManageEvent.Sns -> onSnsEvent()
                SettingManageEvent.ContactBlock -> onContactBlockEvent()
                SettingManageEvent.Location -> onLocationEvent()
                SettingManageEvent.AlarmSetting -> onAlarmSettingEvent()
                SettingManageEvent.Question -> onQuestionEvent()
                SettingManageEvent.Feedback -> onFeedbackEvent()
                SettingManageEvent.UseTerms -> onUseTermsEvent()
                SettingManageEvent.PrivacyTerms -> onPrivacyTermsEvent()
                SettingManageEvent.LocationTerms -> onLocationTermsEvent()
                SettingManageEvent.Licence -> onLicenceEvent()
                SettingManageEvent.CompanyInfo -> onCompanyInfoEvent()
                SettingManageEvent.AccountManager -> onAccountManagerEvent()
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    private fun onSnsEvent() {}

    private fun onContactBlockEvent() {}

    private fun onLocationEvent() {}

    private fun onAlarmSettingEvent() {}

    private fun onQuestionEvent() {}

    private fun onFeedbackEvent() {}

    private fun onUseTermsEvent() {}

    private fun onPrivacyTermsEvent() {}

    private fun onLocationTermsEvent() {}

    private fun onLicenceEvent() {}

    private fun onCompanyInfoEvent() {}

    private fun onAccountManagerEvent() {
        viewModelScope.launch {
            _sideEffect.emit(SideEffect.NavigateAccountManage)
        }
    }
}
