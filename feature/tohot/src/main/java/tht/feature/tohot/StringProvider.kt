package tht.feature.tohot

interface StringProvider {

    fun getString(id: ResId): String

    fun getString(id: ResId, vararg formatArg: Any): String

    enum class ResId {
        Loading,
        ReportSuccess,
        BlockSuccess
    }
}
