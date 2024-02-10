package tht.feature.setting.delegate

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tht.feature.setting.uimodel.event.AccountManagerEvent
import javax.inject.Inject

interface ParseAccountManagerEventDelegate {
    fun parseEvent(key: String): AccountManagerEvent
}

class ParseAccountManagerEventDelegateImpl @Inject constructor() : ParseAccountManagerEventDelegate {
    override fun parseEvent(key: String): AccountManagerEvent {
        return when (key) {
            AccountManagerEvent.Logout.name -> AccountManagerEvent.Logout
            AccountManagerEvent.DisActive.name -> AccountManagerEvent.DisActive
            else -> throw Exception("Unknown Event => $key")
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ParseAccountManagerEventDelegateModule {
    @Binds
    abstract fun bindParseAccountManagerEventDelegate(
        impl: ParseAccountManagerEventDelegateImpl
    ): ParseAccountManagerEventDelegate
}
