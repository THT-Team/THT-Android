import io.gitlab.arturbosch.detekt.Detekt

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath ("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
    }
}

apply (plugin = "io.gitlab.arturbosch.detekt")

subprojects {
    apply (plugin = "io.gitlab.arturbosch.detekt")

    tasks.withType(Detekt).configureEach {
        jvmTarget = "1.8"
    }
}

configurations {
    detekt
}

dependencies {
    detekt ("io.gitlab.arturbosch.detekt:detekt-cli:1.22.0")
    detektPlugins ("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
}


tasks.register("detektAll", Detekt) {

    description = "Custom DETEKT build for all modules"

    // Builds the AST in parallel. Rules are always executed in parallel.
    // Can lead to speedups in larger projects. `false` by default.
    parallel = true

    // Define the detekt configuration(s) you want to use.
    // Defaults to the default detekt configuration.
    config.setFrom(files("$rootDir/detekt-config.yml"))

    // Applies the config files on top of detekt's default config file. `false` by default.
    buildUponDefaultConfig = true

    autoCorrect = true

    // Turns on all the rules. `false` by default.
    allRules = false

    // Disables all default detekt rulesets and will only run detekt with custom rules
    // defined in plugins passed in with `detektPlugins` configuration. `false` by default.
    disableDefaultRuleSets = false

    // Adds debug output during task execution. `false` by default.
    debug = true

    // If set to `true` the build does not fail when the
    // maxIssues count was reached. Defaults to `false`.
    ignoreFailures = false

    // Specify the base path for file paths in the formatted reports.
    // If not set, all file paths reported will be absolute file path.
    setSource(projectDir)

    reports {
        xml {
            enabled = true
            destination = file("build/reports/detekt.xml")
        }

        html {
            enabled = true
            destination = file("build/reports/detekt.html")
        }

        txt {
            enabled = true
            destination = file("build/reports/detekt.txt")
        }

        sarif {
            enabled = true
            destination = file("build/reports/detekt.sarif")
        }
    }
}

