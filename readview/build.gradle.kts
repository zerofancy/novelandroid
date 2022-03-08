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
    buildFeatures {
        viewBinding = true
    }
}

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
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
    implementation(Deps.AndroidX.VIEW_MODEL_KTX)
    implementation(Deps.AndroidX.LIVE_DATA)

    implementation(Deps.MATERIAL)

//    implementation(Deps.AROUTER_API)
//    kapt(Deps.AROUTER_COMPILER)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    implementation(project(":commonutil"))
}