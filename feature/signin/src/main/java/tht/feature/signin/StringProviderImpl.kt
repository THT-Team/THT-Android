package tht.feature.signin

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : StringProvider {

    override fun getString(id: StringProvider.ResId): String {
        return context.resources.getString(getResId(id))
    }

    override fun getString(id: StringProvider.ResId, vararg formatArg: Any): String {
        return context.resources.getString(getResId(id), *formatArg)
    }

    private fun getResId(id: StringProvider.ResId): Int {
        return when (id) {
            StringProvider.ResId.CustomerService -> R.string.message_customer_service
            StringProvider.ResId.InvalidatePhone -> R.string.message_invalidate_phone
            StringProvider.ResId.InvalidateSignupProcess -> R.string.message_invalidate_signup_process
            StringProvider.ResId.SendAuthSuccess -> R.string.message_send_auth_success
            StringProvider.ResId.SendAuthFail -> R.string.message_send_auth_fail
            StringProvider.ResId.AuthTimeout -> R.string.message_auth_timeout
            StringProvider.ResId.ResendAuthSuccess -> R.string.message_resend_auth_success
            StringProvider.ResId.VerifyFail -> R.string.message_verify_error
            StringProvider.ResId.EmailPatchFail -> R.string.message_email_patch_fail
            StringProvider.ResId.TermsFetchFail -> R.string.message_terms_fetch_error
            StringProvider.ResId.TermsPatchFail -> R.string.message_terms_patch_error
            StringProvider.ResId.RequireTermsNeedSelect -> R.string.message_require_terms_need_select
            StringProvider.ResId.NickNamePatchFail -> R.string.message_nickname_patch_fail
            StringProvider.ResId.DuplicateNickname -> R.string.message_nickname_duplicate
            StringProvider.ResId.DuplicateCheckLoading -> R.string.message_nickname_duplicate_check_loading
            StringProvider.ResId.DuplicateCheckFail -> R.string.message_nickname_duplicate_check_fail
            StringProvider.ResId.IdealFetchFail -> R.string.message_ideal_fetch_fail
            StringProvider.ResId.IdealPatchFail -> R.string.message_ideal_patch_fail
        }
    }
}
