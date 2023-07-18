package tht.feature.tohot

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
            StringProvider.ResId.Loading -> R.string.loading
            StringProvider.ResId.ReportSuccess -> R.string.to_hot_report_success
            StringProvider.ResId.ReportFail -> R.string.to_hot_report_fail
            StringProvider.ResId.BlockSuccess -> R.string.to_hot_block_success
            StringProvider.ResId.BlockFail -> R.string.to_hot_block_fail
        }
    }
}
