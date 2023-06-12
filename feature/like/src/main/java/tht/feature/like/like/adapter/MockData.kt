package tht.feature.like.like.adapter

import tht.feature.like.like.LikeModel

object MockData {
    val data = mutableMapOf<String, List<LikeModel>>().apply {
        this["동물"] = listOf(
            LikeModel(
                "최광현", "1997.04.11", listOf(), listOf(), 27, "성남시 중원구 금광2동",
                listOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdaPJMD%2FbtqCinzhh9J%2FakDK6BMiG3QKH3XWXwobx1%2Fimg.jpg"),
                "테스트", false
            ),
            LikeModel(
                "남태우", "1997.06.12", listOf(), listOf(), 27, "성남시 중원구 도촌동",
                listOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F0OEoM%2FbtqCjSyW2Py%2FY6Ki7gqMv7JIvJCqsvkSkK%2Fimg.jpg"),
                "테스트", true
            ),
            LikeModel(
                "최웅재", "1996.04.11", listOf(), listOf(), 28, "성남시 수정구 상대원동",
                listOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb01wXD%2FbtqCoSj5yEx%2FvGkmlskMCLIm1XcYVFpkFK%2Fimg.jpg"),
                "테스트", false
            ),
        )
        this["음악"] = listOf(
            LikeModel(
                "최광현", "1997.04.11", listOf(), listOf(), 27, "성남시 중원구 금광2동",
                listOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdaPJMD%2FbtqCinzhh9J%2FakDK6BMiG3QKH3XWXwobx1%2Fimg.jpg"),
                "테스트", false
            ),
            LikeModel(
                "남태우", "1997.06.12", listOf(), listOf(), 27, "성남시 중원구 도촌동",
                listOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F0OEoM%2FbtqCjSyW2Py%2FY6Ki7gqMv7JIvJCqsvkSkK%2Fimg.jpg"),
                "테스트", true
            ),
            LikeModel(
                "최웅재", "1996.04.11", listOf(), listOf(), 28, "성남시 수정구 상대원동",
                listOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb01wXD%2FbtqCoSj5yEx%2FvGkmlskMCLIm1XcYVFpkFK%2Fimg.jpg"),
                "테스트", false
            ),
        )
        this["개발"] = listOf(
            LikeModel(
                "최광현", "1997.04.11", listOf(), listOf(), 27, "성남시 중원구 금광2동",
                listOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdaPJMD%2FbtqCinzhh9J%2FakDK6BMiG3QKH3XWXwobx1%2Fimg.jpg"),
                "테스트", false
            ),
            LikeModel(
                "남태우", "1997.06.12", listOf(), listOf(), 27, "성남시 중원구 도촌동",
                listOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F0OEoM%2FbtqCjSyW2Py%2FY6Ki7gqMv7JIvJCqsvkSkK%2Fimg.jpg"),
                "테스트", true
            ),
            LikeModel(
                "최웅재", "1996.04.11", listOf(), listOf(), 28, "성남시 수정구 상대원동",
                listOf("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb01wXD%2FbtqCoSj5yEx%2FvGkmlskMCLIm1XcYVFpkFK%2Fimg.jpg"),
                "테스트", false
            ),
        )
    }

}
