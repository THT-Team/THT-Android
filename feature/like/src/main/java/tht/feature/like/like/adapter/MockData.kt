package tht.feature.like.like.adapter

import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel
import tht.feature.like.like.LikeModel

object MockData {
    val data = LinkedHashMap<String, List<LikeModel>>().apply {
        this["뉴진스"] = listOf(
            LikeModel(
                "해린",
                "2006.04.11",
                listOf(
                    InterestModel("지적인", 1L, "1F9E0"),
                    InterestModel("귀여운", 2L, "1F63B"),
                    InterestModel("피부가 좋은", 3L, "2728")
                ),
                listOf(
                    IdealTypeModel("게임", 1L, "1F3AE"),
                    IdealTypeModel("독서", 2L, "1F4DA"),
                    IdealTypeModel("영화/드라마", 3L, "1F3AC")
                ),
                20,
                "성남시 중원구 금광동",
                listOf(
                    "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog" +
                        ".kakaocdn.net%2Fdn%2Fbnhxb2%2FbtrNyEBYECa%2FnhgHKeKyGh3mTFFHY4MpD1%2Fimg.jpg",
                    "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog" +
                        ".kakaocdn.net%2Fdn%2Fmfcdv%2FbtrNwEWSXiP%2FknluCLOSCGz4HKSIGRXhqk%2Fimg.jpg",
                    "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog" +
                        ".kakaocdn.net%2Fdn%2FEhA0f%2FbtrNvPkubxN%2FLTiJCDKUxCcSDUXDP5tyq0%2Fimg.jpg"
                ),
                "무엇을 위하여서 끝까지 것이다. 새가 위하여서, 싹이 우리의 석가는 위하여 그와 유소년에게서 것이다. 그러므로 불어 방황하였으며, 반짝이는 천하를 동산에는 뿐이다.\n" +
                    "무엇을 위하여서 끝까지 것이다. 새가 위하여서, 싹이 우리의 석가는 위하여 그와 유소년에게서 것이다. 그러므로 불어 방황하였으며, 반짝이는 천하를 동산에는 뿐이다.",
                "뉴진스",
                false
            ),
            LikeModel(
                "민지", "2004.04.11",
                listOf(
                    InterestModel("지적인", 1L, "1F9E0"),
                    InterestModel("귀여운", 2L, "1F63B"),
                    InterestModel("피부가 좋은", 3L, "2728")
                ),
                listOf(
                    IdealTypeModel("게임", 1L, "1F3AE"),
                    IdealTypeModel("독서", 2L, "1F4DA"),
                    IdealTypeModel("영화/드라마", 3L, "1F3AC")
                ),
                20,
                "성남시 중원구 도촌동",
                listOf(
                    "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog" +
                        ".kakaocdn.net%2Fdn%2FdHTyJW%2FbtrNEvqPb7s%2FeoMMTURJuSnWvmzS91BM81%2Fimg.jpg",
                    "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog" +
                        ".kakaocdn.net%2Fdn%2FdBVMiu%2FbtrNw9CxhSq%2F5FhT6BGY5O2lN8ZFTD21mK%2Fimg.jpg",
                    "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog" +
                        ".kakaocdn.net%2Fdn%2Fbxh86A%2FbtrNzJwB3Oe%2FblmW9QIYJX5LQoY5BAqKeK%2Fimg.jpg"
                ),
                "테스트",
                "뉴진스",
                true
            ),
            LikeModel(
                "하니", "2004.04.11",
                listOf(
                    InterestModel("지적인", 1L, "1F9E0"),
                    InterestModel("귀여운", 2L, "1F63B"),
                    InterestModel("피부가 좋은", 3L, "2728")
                ),
                listOf(
                    IdealTypeModel("게임", 1L, "1F3AE"),
                    IdealTypeModel("독서", 2L, "1F4DA"),
                    IdealTypeModel("영화/드라마", 3L, "1F3AC")
                ),
                20,
                "성남시 수정구 상대원동",
                listOf(
                    "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog" +
                        ".kakaocdn.net%2Fdn%2FAUhEc%2FbtrH1BdDlDq%2Fs7bKm7tcN84IYvac8kTvoK%2Fimg.jpg",
                    "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog" +
                        ".kakaocdn.net%2Fdn%2FlPkjJ%2FbtrH1AToDdv%2FRcT7ifPRhk526t8QCLRN40%2Fimg.jpg",
                    "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog" +
                        ".kakaocdn.net%2Fdn%2FDNLi6%2FbtrH2hlFyBA%2FpWowefy4AXflSYckwfJad1%2Fimg.jpg"
                ),
                "테스트",
                "뉴진스",
                false
            )
        )
        this["개발"] = listOf(
            LikeModel(
                "남태우", "1997.04.11", listOf(), listOf(), 27, "성남시 중원구 금광2동",
                listOf(
                    "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog." +
                        "kakaocdn.net%2Fdn%2FdaPJMD%2FbtqCinzhh9J%2FakDK6BMiG3QKH3XWXwobx1%2Fimg.jpg"
                ),
                "테스트",
                "개발",
                false
            ),
            LikeModel(
                "최웅재", "1997.06.12", listOf(), listOf(), 28, "성남시 중원구 도촌동",
                listOf(
                    "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog." +
                        "kakaocdn.net%2Fdn%2F0OEoM%2FbtqCjSyW2Py%2FY6Ki7gqMv7JIvJCqsvkSkK%2Fimg.jpg"
                ),
                "테스트",
                "개발",
                true
            ),
            LikeModel(
                "최광현", "1997.04.11", listOf(), listOf(), 27, "성남시 수정구 상대원동",
                listOf(
                    "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog." +
                        "kakaocdn.net%2Fdn%2Fb01wXD%2FbtqCoSj5yEx%2FvGkmlskMCLIm1XcYVFpkFK%2Fimg.jpg"
                ),
                "테스트",
                "개발",
                false
            )
        )
    }
}
