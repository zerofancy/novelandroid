plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        minSdkVersion(16)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
        consumerProguardFiles("consumer-rules.pro")
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

dependencies {

    implementation(Deps.AndroidX.ACTIVITY)
    implementation(Deps.AndroidX.COMPAT)
    implementation(Deps.AndroidX.CORE)
    implementation(Deps.AndroidX.FRAGMENT)
    implementation(Deps.AndroidX.PREFERENCE)
    implementation(Deps.Kotlin.STD_LIB)
    implementation(Deps.MATERIAL)

    implementation(project(":setting_api"))
    implementation(project(":commonui"))

    compileOnly(Deps.AUTO_SERVICE)
    kapt(Deps.AUTO_SERVICE)
}