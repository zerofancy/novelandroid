plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-noarg")
}


noArg { annotation("top.ntutn.libzeroconfig.ZeroConfig") } // 解决data class没有默认构造函数的问题}*/

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.2"

    defaultConfig {
        applicationId = "top.ntutn.novelrecommend"
        minSdkVersion(22)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
        // https://github.com/react-native-camera/react-native-camera/issues/2940
        multiDexEnabled = true
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
    implementation(Deps.AndroidX.VIEW_MODEL)
    implementation(Deps.AndroidX.LIVE_DATA)

    implementation(Deps.MATERIAL)
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.preference:preference:1.1.1")
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    implementation(Deps.GSON)
    implementation(Deps.TIMBER)
    implementation(Deps.EVENTBUS)
    kapt(Deps.EVENTBUS_PROCESSOR)

    implementation(Deps.ABOUT_PAGE)
//    implementation(Deps.ABOUT_TYPE)

//    implementation(Deps.AROUTER_API)
//    kapt(Deps.AROUTER_COMPILER)

    // https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
    implementation(Deps.RETROFIT)
    implementation(Deps.RETROFIT_CONVERTER_GSON)

    implementation(Deps.LIKE_VIEW)

    implementation(Deps.FRESCO)
    implementation(Deps.LAYOUT_MANAGER_GROUP)
    implementation(Deps.ANIM_LOGO_VIEW)

    kapt(project(":libzeroconfigcompiler"))
    implementation(project(":libzeroconfig"))
    implementation(project(":zeroconfigutil"))
    implementation(project(":app:readview"))
    implementation(project(":commonutil"))
}