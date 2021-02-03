// 依赖管理参考https://medium.com/better-programming/gradle-dependency-management-with-buildsrc-and-kotlin-dsl-1de958eab166

@Suppress("unused", "SpellCheckingInspection")
object Vers {
    val AndroidX = AndroidXVersion

    const val kotlin_version = "1.4.21"
    const val kotlin_coroutines = "1.3.9"
    const val MATERIAL = "1.2.1"
    const val GSON = "2.8.6"
    const val TIMBER = "4.7.1"
    const val EVENTBUS = "3.2.0"
    const val AUTO_SERVICE = "1.0-rc7"
    const val KOTLIN_POET = "1.7.2"
}

@Suppress("unused", "SpellCheckingInspection")
object Deps {
    val Kotlin = KotlinDependency
    val AndroidX = AndroidXDependency

    const val MATERIAL = "com.google.android.material:material:${Vers.MATERIAL}"
    const val GSON = "com.google.code.gson:gson:${Vers.GSON}"
    const val TIMBER = "com.jakewharton.timber:timber:${Vers.TIMBER}"

    const val EVENTBUS = "org.greenrobot:eventbus:${Vers.EVENTBUS}"
    const val EVENTBUS_PROCESSOR = "org.greenrobot:eventbus-annotation-processor:${Vers.EVENTBUS}"

    const val AUTO_SERVICE = "com.google.auto.service:auto-service:${Vers.AUTO_SERVICE}"
    const val KOTLIN_POET = "com.squareup:kotlinpoet:${Vers.KOTLIN_POET}"
}

@Suppress("unused", "SpellCheckingInspection")
object KotlinDependency {
    const val ALL_OPEN = "org.jetbrains.kotlin:kotlin-allopen:${Vers.kotlin_version}"
    const val GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Vers.kotlin_version}"
    const val NO_ARGS = "org.jetbrains.kotlin:kotlin-noarg:${Vers.kotlin_version}"
    const val STD_LIB = "org.jetbrains.kotlin:kotlin-stdlib:${Vers.kotlin_version}"
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
}

@Suppress("unused")
object AndroidXVersion {
    const val COMPAT = "1.2.0"
    const val CORE = "1.3.2"
    const val CONSTRAINT_LAYOUT = "2.0.4"
    const val NAVIGATION_FRAGMENT = "2.3.3"
    const val NAVIGATION_UI = "2.3.3"
    const val VECTOR_DRAWABLE = "1.1.0"
    const val VIEW_MODEL = "2.2.0"
    const val LIVE_DATA = "2.2.0"
}

@Suppress("unused", "SpellCheckingInspection")
object AndroidXDependency {
    const val COMPAT = "androidx.appcompat:appcompat:${AndroidXVersion.COMPAT}"
    const val CORE = "androidx.core:core-ktx:${AndroidXVersion.CORE}"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${AndroidXVersion.CONSTRAINT_LAYOUT}"
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment-ktx:${AndroidXVersion.NAVIGATION_FRAGMENT}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:2.3.3"
    const val VECTOR_DRAWABLE =
        "androidx.vectordrawable:vectordrawable:${AndroidXVersion.VECTOR_DRAWABLE}"
    const val VIEW_MODEL =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${AndroidXVersion.VIEW_MODEL}"
    const val LIVE_DATA = "androidx.lifecycle:lifecycle-livedata-ktx:${AndroidXVersion.VIEW_MODEL}"
}