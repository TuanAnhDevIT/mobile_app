pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {  setUrl("https://jitpack.io") }
        google()
        jcenter()
    }
}

rootProject.name = "Mobile App"
include(":app")
