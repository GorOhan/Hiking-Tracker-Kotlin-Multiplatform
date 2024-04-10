enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            // Do not change the username below. It should always be "mapbox" (not your username).
            credentials.username = "mapbox"
            // Use the secret token stored in gradle.properties as the password
            credentials.password = "sk.eyJ1IjoiZ3JvYW4iLCJhIjoiY2x1dGh3cHBlMGJpZTJqbGJycjFidHQ3ZiJ9.ww4gvMw_3KBvzSzdyjci4g"
          //  credentials.password = providers.gradleProperty("MAPBOX_DOWNLOADS_TOKEN").get()
            authentication.create<BasicAuthentication>("basic")
        }
    }
}

rootProject.name = "XHike"
include(":androidApp")
include(":shared")