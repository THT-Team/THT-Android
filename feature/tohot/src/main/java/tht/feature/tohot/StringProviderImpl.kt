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
        }
    }
}
