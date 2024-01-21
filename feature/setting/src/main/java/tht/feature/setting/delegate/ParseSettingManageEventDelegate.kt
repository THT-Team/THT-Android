package tht.feature.setting.delegate

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tht.feature.setting.uimodel.event.SettingManageEvent
import javax.inject.Inject

interface ParseSettingManageEventDelegate {
    fun parseEvent(key: String): SettingManageEvent
}

class ParseSettingManageEventDelegateImpl @Inject constructor(
) : ParseSettingManageEventDelegate {
    override fun parseEvent(key: String): SettingManageEvent {
        return when (key) {
            SettingManageEvent.Phone.name -> SettingManageEvent.Phone
            SettingManageEvent.Email.name -> SettingManageEvent.Email
            SettingManageEvent.Sns.name -> SettingManageEvent.Sns
            SettingManageEvent.ContactBlock.name -> SettingManageEvent.ContactBlock
            SettingManageEvent.Location.name -> SettingManageEvent.Location
            SettingManageEvent.AlarmSetting.name -> SettingManageEvent.AlarmSetting
            SettingManageEvent.Question.name -> SettingManageEvent.Question
            SettingManageEvent.Feedback.name -> SettingManageEvent.Feedback
            SettingManageEvent.UseTerms.name -> SettingManageEvent.UseTerms
            SettingManageEvent.PrivacyTerms.name -> SettingManageEvent.PrivacyTerms
            SettingManageEvent.LocationTerms.name -> SettingManageEvent.LocationTerms
            SettingManageEvent.Licence.name -> SettingManageEvent.Licence
            SettingManageEvent.CompanyInfo.name -> SettingManageEvent.CompanyInfo
            SettingManageEvent.AccountManager.name -> SettingManageEvent.AccountManager
            else -> throw Exception("Unknown Event")
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ParseSettingManageEventDelegateModule {
    @Binds
    abstract fun bindParseSettingManageEventDelegate(
        impl: ParseSettingManageEventDelegateImpl
    ): ParseSettingManageEventDelegate
}
