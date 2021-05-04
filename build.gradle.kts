// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.4.21")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath(Deps.Kotlin.GRADLE_PLUGIN)
        classpath(Deps.Kotlin.NO_ARGS)
        classpath(Deps.Kotlin.ALL_OPEN)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
