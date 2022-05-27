object Libs {
    const val ktLintVersion = "0.43.2"
    const val gradlePluginVersion = "7.2.1"
    const val androidGradlePlugin = "com.android.tools.build:gradle:$gradlePluginVersion"
    const val ktLint = "com.pinterest:ktlint:$ktLintVersion"

    object Plugins {
        object Spotless {
            const val version = "6.6.1"
            const val pluginId = "com.diffplug.spotless"
        }
        object GradleVersions {
            const val version = "0.42.0"
            const val pluginId = "com.github.ben-manes.versions"
        }
    }

    object Kotlin {
        private const val version = "1.6.21"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"

        object Coroutines {
            private const val version = "1.6.0"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object Desugaring {
        private const val version = "1.1.5"
        const val desugarJdk = "com.android.tools:desugar_jdk_libs:$version"
    }

    object AndroidX {
        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.4.0"
        }

        object Navigation {
            const val version = "2.4.2"

            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
            const val composeNav = "androidx.navigation:navigation-compose:$version"
        }

        object Compose {
            const val version = "1.2.0-beta01"

            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val material = "androidx.compose.material:material:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val animation = "androidx.compose.animation:animation:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
        }

        object Lifecycle {
            private const val version = "2.5.0-rc01"

            const val viewModelCompose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
        }

        object Test {
            private const val version = "1.4.0"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.3"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }
        }

        object Room {
            private const val roomVersion = "2.4.2"
            private const val lifecycleVersion = "2.4.1"
            const val room = "androidx.room:room-runtime:$roomVersion"
            const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
            const val roomKtx = "androidx.room:room-ktx:$roomVersion"
            const val runtime = "androidx.lifecycle:lifecycle-runtime:$lifecycleVersion"
        }
    }

    object Hilt {
        private const val version = "2.42"
        private const val composeNavigationVersion = "1.0.0"

        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val compose = "androidx.hilt:hilt-navigation-compose:$composeNavigationVersion"
        const val test = "com.google.dagger:hilt-android-testing:$version"
        const val testCompiler = "com.google.dagger:hilt-android-compiler:$version"
    }

    object JUnit {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"
    }

    object Network {
        private const val retrofitVersion = "2.9.0"
        private const val okhttpVersion = "4.9.1"
        private const val moshiVersion = "1.13.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val moshi = "com.squareup.moshi:moshi:$moshiVersion"
        const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"
        const val moshiConverter =
            "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
    }

    object Coil {
        private const val version = "2.1.0"
        const val main = "io.coil-kt:coil:$version"
        const val compose = "io.coil-kt:coil-compose:$version"
        const val gifs = "io.coil-kt:coil-gif:$version"
    }

    object Accompanist {
        private const val version = "0.24.9-beta"
        const val systemUiController =
            "com.google.accompanist:accompanist-systemuicontroller:$version"
        const val webView =
            "com.google.accompanist:accompanist-webview:$version"
    }

    object App {
        const val minSdkVersion = 24
        const val targetSdkVersion = 31
        const val compileSdkVersion = 31
        const val applicationId = "dev.efantini.lofibuddy"
    }
}
