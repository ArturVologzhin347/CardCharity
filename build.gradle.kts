// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        with(Dependencies.Plugins) {
            classpath(androidGradlePlugin)
            classpath(kotlinGradlePlugin)
            classpath(googleServices)
        }
    }

}
