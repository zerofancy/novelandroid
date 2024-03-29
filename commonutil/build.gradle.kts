plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        minSdkVersion(19)
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
}

kapt {
    arguments {
        arg("zeroConfigHolder", "top.ntutn.commonutil.ZeroConfigHolder")
    }
}

dependencies {
    implementation(Deps.Kotlin.STD_LIB)
    implementation(Deps.AndroidX.CORE)
    implementation(Deps.AndroidX.COMPAT)
    implementation(Deps.MATERIAL)
    implementation(Deps.RETROFIT)
    implementation(Deps.RETROFIT_CONVERTER_GSON)
    implementation(Deps.TIMBER)

    kapt(project(":libzeroconfig"))
    implementation(project(":libzeroconfig"))
    implementation(project(":zeroconfigutil"))
    implementation(project(":login_api"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}