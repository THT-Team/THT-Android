package tht.feature.tohot.model

import javax.annotation.concurrent.Immutable

@Immutable
data class ImmutableListWrapper<T>(val list: List<T>)
