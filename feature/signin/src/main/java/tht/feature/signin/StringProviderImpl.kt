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
        }
    }
}
