package tht.feature.like.like

import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel

data class LikeModel(
    val nickname: String,
    val birthday: String,
    val interests: List<InterestModel>,
    val idealTypes: List<IdealTypeModel>,
    val age: Int,
    val address: String,
    val profileImgUrl: List<String>,
    val introduce: String,
    val category: String,
    val isNew: Boolean
) : java.io.Serializable {

    companion object {
        fun getDefaultLikeModel() = LikeModel(
            "", "", listOf(), listOf(), 0, "",
            listOf(), "", "", false
        )
    }
}
