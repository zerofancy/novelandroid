plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Deps.Kotlin.STD_LIB)
    compileOnly(Deps.AUTO_SERVICE)
    kapt(Deps.AUTO_SERVICE)
    implementation(Deps.GSON)
    implementation(Deps.KOTLIN_POET)
}