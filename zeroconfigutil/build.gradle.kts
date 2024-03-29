plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-noarg")
}

noArg { annotation("top.ntutn.libzeroconfig.ZeroConfig") } // 解决data class没有默认构造函数的问题}*/

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.2"

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
        arg("zeroConfigHolder", "top.ntutn.zeroconfigutil.ZeroConfigHolder")
    }
}

dependencies {

    implementation(Deps.Kotlin.STD_LIB)
    implementation(Deps.AndroidX.CORE)
    implementation(Deps.AndroidX.COMPAT)
    implementation(Deps.MATERIAL)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    implementation(Deps.GSON)
    implementation(project(":libzeroconfig"))
    kapt(project(":libzeroconfig"))
}