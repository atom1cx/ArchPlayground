pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Arch Playground"
include(":app")
include(":create-impl")
include(":create-api")
include(":arch-util")
include(":repository-api")
include(":repository-impl")
include(":list-api")
include(":list-impl")
include(":details-api")
include(":details-impl")
include(":arch-core")
