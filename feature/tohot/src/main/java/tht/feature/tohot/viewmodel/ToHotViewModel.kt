package tht.feature.tohot.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import tht.feature.tohot.StringProvider
import tht.feature.tohot.userData
import javax.inject.Inject

@HiltViewModel
class ToHotViewModel @Inject constructor(
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _cardList = MutableStateFlow(
        listOf(
            userData,
            userData.copy(nickname = "name1"),
            userData.copy(nickname = "name2")
        )
    )
    val cardList = _cardList.asStateFlow()
}
