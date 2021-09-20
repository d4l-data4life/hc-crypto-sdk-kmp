/*
 * Copyright (c) 2021 D4L data4life gGmbH / All rights reserved.
 *
 * D4L owns all legal rights, title and interest in and to the Software Development Kit ("SDK"),
 * including any intellectual property rights that subsist in the SDK.
 *
 * The SDK and its documentation may be accessed and used for viewing/review purposes only.
 * Any usage of the SDK for other purposes, including usage for the development of
 * applications/third-party applications shall require the conclusion of a license agreement
 * between you and D4L.
 *
 * If you are interested in licensing the SDK for your own applications/third-party
 * applications and/or if you’d like to contribute to the development of the SDK, please
 * contact D4L by email to help@data4life.care.
 */

package care.data4life.gradle.crypto.dependency

object Dependency {

    val kotlin = Kotlin
    val multiplatform = Multiplatform
    val multiplatformTest = MultiplatformTest
    val jvm = Jvm
    val jvmTest = JvmTest
    val android = Android
    val androidTest = AndroidTest

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Version.kotlin}"
        const val serialization = "org.jetbrains.kotlin:kotlin-serialization:${Version.kotlin}"
    }

    object Multiplatform {

        val kotlin = Kotlin
        val d4l = D4L

        object Kotlin {

            val stdLib = StdLib

            object StdLib {
                const val common = "org.jetbrains.kotlin:kotlin-stdlib-common:${Version.kotlin}"
                const val jdk = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
                const val jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}"
                const val js = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
                const val native = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
                const val android = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
            }
        }

        object D4L {

            val util = Util
            val result = Result

            object Util {
                const val common = "care.data4life.hc-util-sdk-kmp:util:${Version.Multiplatform.D4L.utilSdk}"
                const val android = "care.data4life.hc-util-sdk-kmp:util-android:${Version.Multiplatform.D4L.utilSdk}"
                const val jvm = "care.data4life.hc-util-sdk-kmp:util-jvm:${Version.Multiplatform.D4L.utilSdk}"
            }

            object Result {
                const val error = "care.data4life.hc-result-sdk-kmp:error:${Version.Multiplatform.D4L.resultSdk}"
            }
        }
    }

    object MultiplatformTest {

        val kotlin = Kotlin
        val mockK = MockK

        object Kotlin {
            const val common = "org.jetbrains.kotlin:kotlin-test-common:${Version.kotlin}"
            const val commonAnnotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Version.kotlin}"
            const val jvm = "org.jetbrains.kotlin:kotlin-test:${Version.kotlin}"
            const val jvmJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Version.kotlin}"
        }

        object MockK {
            const val common = "io.mockk:mockk-common:${Version.MultiplatformTest.mockK}"
            const val junit = "io.mockk:mockk:${Version.MultiplatformTest.mockK}"
            const val androidTestInstrumentation = "io.mockk:mockk-android:${Version.MultiplatformTest.mockK}"
        }
    }

    object Jvm {
        // Crypto
        const val bouncyCastleJdk15 = "org.bouncycastle:bcprov-jdk15on:${Version.Jvm.bouncyCastle}"

        // Serialization
        const val moshi = "com.squareup.moshi:moshi:${Version.Jvm.moshi}"
        const val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Version.Jvm.moshi}"
    }

    object JvmTest {
        const val junit = "junit:junit:${Version.JvmTest.jUnit}"
    }

    object Android {

        val androidX = AndroidX

        // Kotlin
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"

        // Android
        const val desugar = "com.android.tools:desugar_jdk_libs:${Version.Android.androidDesugar}"

        object AndroidX {
            const val ktx = "androidx.core:core-ktx:${Version.Android.AndroidX.ktx}"
            const val appCompat = "androidx.appcompat:appcompat:${Version.Android.AndroidX.appCompat}"
            const val browser = "androidx.browser:browser:${Version.Android.AndroidX.browser}"

            // Layout
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Version.Android.AndroidX.constraintLayout}"
        }

        // Material
        const val material = "com.google.android.material:material:${Version.Android.material}"
    }

    object AndroidTest {
        const val core = "androidx.test:core:${Version.AndroidTest.androidXTestCore}"
        const val runner = "androidx.test:runner:${Version.AndroidTest.androidXTestCore}"
        const val rules = "androidx.test:rules:${Version.AndroidTest.androidXTestCore}"

        const val junit = "androidx.test.ext:junit:${Version.AndroidTest.androidXTestCore}"

        const val espressoCore = "androidx.test.espresso:espresso-core:${Version.AndroidTest.androidXEspresso}"
        const val espressoIntents = "androidx.test.espresso:espresso-intents:${Version.AndroidTest.androidXEspresso}"
        const val espressoWeb = "androidx.test.espresso:espresso-web:${Version.AndroidTest.androidXEspresso}"

        const val uiAutomator = "androidx.test.uiautomator:uiautomator:${Version.AndroidTest.androidXUiAutomator}"

        const val robolectric = "org.robolectric:robolectric:${Version.AndroidTest.robolectric}"
    }
}
