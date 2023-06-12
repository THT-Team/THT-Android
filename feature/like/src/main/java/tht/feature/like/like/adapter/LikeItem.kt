package tht.feature.like.like.adapter

import tht.feature.like.like.LikeUserModel

sealed class LikeItem {
    abstract val id: String

    data class Header(val category: String) : LikeItem() {
        override val id: String = category
    }

    data class Content(val item: LikeUserModel) : LikeItem() {
        override val id: String = item.nickname
    }
}
