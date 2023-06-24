package tht.feature.tohot

import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotUserUiModel

internal val userData = ToHotUserUiModel(
    id = 0,
    nickname = "Harry",
    birthday = "2022.02.03",
    interests = ImmutableListWrapper(
        listOf(
            InterestModel("지적인", 1L, "1F9E0"),
            InterestModel("귀여운", 2L, "1F63B"),
            InterestModel("피부가 좋은", 3L, "2728")
        )
    ),
    idealTypes = ImmutableListWrapper(
        listOf(
            IdealTypeModel("게임", 1L, "1F3AE"),
            IdealTypeModel("독서", 2L, "1F4DA"),
            IdealTypeModel("영화/드라마", 3L, "1F3AC")
        )
    ),
    age = 24,
    address = "서울특별시 용산구 용산 11-1",
    profileImgUrl = ImmutableListWrapper(
        listOf(
            "https://firebasestorage.googleapis.com/v0/b/tht-android-a954a.appspot.com/o/1684673205398_0?" +
                "alt=media&token=93c6fbbf-20bd-4d43-953c-50c0722c31c2",
            "https://firebasestorage.googleapis.com/v0/b/tht-android-a954a.appspot.com/o/1684673205398_1?" +
                "alt=media&token=14048744-7aa1-4872-bbc4-aa013f4a6c33"
        )
    ),
    introduce = "무엇을 위하여서 끝까지 것이다. 새가 위하여서, 싹이 우리의 석가는 위하여 그와 유소년에게서 것이다. 그러므로 불어 방황하였으며, 반짝이는 천하를 동산에는 뿐이다.\n" +
        "무엇을 위하여서 끝까지 것이다. 새가 위하여서, 싹이 우리의 석가는 위하여 그와 유소년에게서 것이다. 그러므로 불어 방황하였으며, 반짝이는 천하를 동산에는 뿐이다."
)

internal val userData2 = userData.copy(
    id = 2,
    nickname = "Suzume",
    profileImgUrl = ImmutableListWrapper(
        listOf(
            "https://firebasestorage.googleapis.com/v0/b/tht-android-a954a.appspot.com/o/1683307361492_2?" +
                "alt=media&token=fd9c27e5-1983-46cf-bc5b-d3f0ee65cbeb",
            "https://firebasestorage.googleapis.com/v0/b/tht-android-a954a.appspot.com/o/1683549332394_0?" +
                "alt=media&token=6a4bb8b9-e5e7-403e-b1e7-dc0b9624c8e5",
            "https://firebasestorage.googleapis.com/v0/b/tht-android-a954a.appspot.com/o/1685205916506_1?" +
                "alt=media&token=f8b95319-b36d-4498-864d-b31dea78e448",
            "https://firebasestorage.googleapis.com/v0/b/tht-android-a954a.appspot.com/o/1685205916506_0?" +
                "alt=media&token=4b32f03a-bf43-4e54-8c67-a6de22b9cdb1"
        )
    )
)

internal val userData3 = userData.copy(
    id = 3,
    nickname = "ToToro",
    profileImgUrl = ImmutableListWrapper(
        listOf(
            "https://firebasestorage.googleapis.com/v0/b/tht-android-a954a.appspot.com/o/1683308012779_2?" +
                "alt=media&token=e0d7d0a2-26b2-48cb-99fd-9685af8a8589"
        )
    )
)

internal val userData4 = userData.copy(
    id = 4,
    nickname = "SpiderMan",
    profileImgUrl = ImmutableListWrapper(
        listOf(
            "https://firebasestorage.googleapis.com/v0/b/tht-android-a954a.appspot.com/o/1683549332395_1?" +
                "alt=media&token=b8f6d7a4-6cb3-4b79-85ea-1245304e4ed8"
        )
    )
)
