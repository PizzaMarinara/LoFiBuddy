plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    // id("dagger.hilt.android.plugin")
}
android {
    compileSdk = Libs.App.compileSdkVersion
    defaultConfig {
        multiDexEnabled = true
        applicationId = Libs.App.applicationId
        minSdk = Libs.App.minSdkVersion
        targetSdk = Libs.App.targetSdkVersion
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "dev.efantini.lofibuddy.CustomTestRunner"
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
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    packagingOptions {
        resources.excludes.add("/META-INF/AL2.0")
        resources.excludes.add("/META-INF/LGPL2.1")
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.version
    }
}

dependencies {

    // Kotlin
    implementation(Libs.Kotlin.stdlib)

    // AndroidX/Compose/
    implementation(Libs.AndroidX.Activity.activityCompose)
    implementation(Libs.AndroidX.Compose.runtime)
    implementation(Libs.AndroidX.Compose.foundation)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.layout)
    // implementation(Libs.AndroidX.Compose.animation)
    // implementation(Libs.AndroidX.Lifecycle.viewModelCompose)

    // Accompanist
    implementation(Libs.Accompanist.systemUiController)
    // implementation(Libs.Accompanist.webView)

    // Navigation
    implementation(Libs.AndroidX.Navigation.uiKtx)
    implementation(Libs.AndroidX.Navigation.composeNav)

    // Hilt
    // implementation(Libs.Hilt.android)
    // implementation(Libs.Hilt.compose)
    // kapt(Libs.Hilt.compiler)

    // YoutubePlayer
    implementation(Libs.YoutubePlayer.player)

    // Coil
    implementation(Libs.Coil.main)
    implementation(Libs.Coil.compose)
    implementation(Libs.Coil.gifs)

    // Test
    androidTestImplementation(Libs.AndroidX.Compose.uiTest)
    debugImplementation(Libs.AndroidX.Compose.uiTestManifest)
    androidTestImplementation(Libs.AndroidX.Test.rules)
    androidTestImplementation(Libs.AndroidX.Test.runner)
    androidTestImplementation(Libs.AndroidX.Test.Ext.junit)
    testImplementation(Libs.Kotlin.Coroutines.test)
    testImplementation(Libs.JUnit.junit)
    // androidTestImplementation(Libs.Hilt.test)
    // kaptAndroidTest(Libs.Hilt.testCompiler)

    // Desugaring
    coreLibraryDesugaring(Libs.Desugaring.desugarJdk)
}
