plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

/**
apply plugin: 'kotlin-noarg'

noArg {annotation("ZeroConfig") // 解决data class没有默认构造函数的问题}*/

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.2"

    defaultConfig {
        applicationId = "top.ntutn.novelrecommend"
        minSdkVersion(19)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

kapt {
    arguments {
        arg("eventBusIndex", "top.ntutn.novelrecommend.AppEventBusAppIndex")
        arg("verbose", true)
        arg("zeroConfigHolder", "top.ntutn.novelrecommend.ZeroConfigHolder")
    }
}

dependencies {
    implementation(Deps.Kotlin.STD_LIB)
    implementation(Deps.Kotlin.COROUTINES_ANDROID)

    implementation(Deps.AndroidX.CORE)
    implementation(Deps.AndroidX.COMPAT)
    implementation(Deps.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Deps.AndroidX.VECTOR_DRAWABLE)
    implementation(Deps.AndroidX.NAVIGATION_FRAGMENT)
    implementation(Deps.AndroidX.NAVIGATION_UI)
    implementation(Deps.AndroidX.VIEW_MODEL)
    implementation(Deps.AndroidX.LIVE_DATA)

    implementation(Deps.MATERIAL)
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    implementation(Deps.GSON)
    implementation(Deps.TIMBER)
    implementation(Deps.EVENTBUS)
    kapt(Deps.EVENTBUS_PROCESSOR)

    kapt(project(":libzeroconfigcompiler"))
    implementation(project(":libzeroconfig"))
    implementation(project(":zeroconfigutil"))
}