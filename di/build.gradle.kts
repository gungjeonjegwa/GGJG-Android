plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "com.ggjg.di"
    compileSdk = Version.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = Version.MIN_SDK_VERSION
        targetSdk = Version.TARGET_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "BASE_URL", getApiKey("BASE_URL"))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Version.JAVA_VERSION
        targetCompatibility = Version.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = Version.JAVA_VERSION.toString()
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Dependency.AndroidX.CORE_KTX)
    implementation(Dependency.AndroidX.APP_COMPAT)
    implementation(Dependency.AndroidX.PREFERENCE_KTX)

    implementation(Dependency.Hilt.HILT_ANDROID)
    kapt(Dependency.Hilt.HILT_ANDROID_COMPILER)

    implementation(Dependency.Room.ROOM)
    kapt(Dependency.Room.ROOM_COMPILER)

    implementation(Dependency.Retrofit.RETROFIT)
    implementation(Dependency.Retrofit.RETROFIT_CONVERTER_GSON)
    implementation(Dependency.Retrofit.OKHTTP)
    implementation(Dependency.Retrofit.OKHTTP_LOGGING_INTERCEPTOR)

    testImplementation(Dependency.UnitTest.JUNIT)
    testImplementation(Dependency.UnitTest.MOCKITO)

    androidTestImplementation(Dependency.AndroidTest.ANDROID_JUNIT)
    androidTestImplementation(Dependency.AndroidTest.ESPRESSO_CORE)
}

fun getApiKey(propertyKey: String): String {
    return com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
        .getProperty(propertyKey)
}