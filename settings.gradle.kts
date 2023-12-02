pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
        maven { url = uri("https://jitpack.io") }
    }
    versionCatalogs {
        create("libs") {
            from(files("gradle/libraries.version.toml"))
        }
    }
}

rootProject.name = "THT"
include(":app")
include(":data")
include(":domain")

include(":feature:tohot")
include(":feature:heart")
include(":feature:chat")
include(":feature:setting")
include(":feature:signin")

include(":core")
include(":core:ui")
include(":core:compose-ui")
include(":core:navigation")
