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

package care.data4life.sdk.crypto.dependency

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

            object Util {
                const val common = "care.data4life.hc-util-sdk-kmp:util:${Version.multiplatform.d4l.utilSdk}"
                const val jvm = "care.data4life.hc-util-sdk-kmp:util-jvm:${Version.multiplatform.d4l.utilSdk}"
                const val android = "care.data4life.hc-util-sdk-kmp:util-android:${Version.multiplatform.d4l.utilSdk}"
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
            const val common = "io.mockk:mockk-common:${Version.multiplatformTest.mockK}"
            const val junit = "io.mockk:mockk:${Version.multiplatformTest.mockK}"
            const val androidTestInstrumentation = "io.mockk:mockk-android:${Version.multiplatformTest.mockK}"
        }
    }

    object Jvm {
        // Crypto
        const val bouncyCastleJdk15 = "org.bouncycastle:bcprov-jdk15on:${Version.jvm.bouncyCastle}"

        // Serialization
        const val moshi = "com.squareup.moshi:moshi:${Version.jvm.moshi}"
        const val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Version.jvm.moshi}"
    }

    object JvmTest {
        const val junit = "junit:junit:${Version.jvmTest.jUnit}"
    }

    object Android {

        val androidX = AndroidX

        // Kotlin
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"

        // Android
        const val desugar = "com.android.tools:desugar_jdk_libs:${Version.android.androidDesugar}"

        object AndroidX {
            const val ktx = "androidx.core:core-ktx:${Version.android.androidX.ktx}"
            const val appCompat = "androidx.appcompat:appcompat:${Version.android.androidX.appCompat}"
            const val browser = "androidx.browser:browser:${Version.android.androidX.browser}"

            // Layout
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Version.android.androidX.constraintLayout}"
        }

        // Material
        const val material = "com.google.android.material:material:${Version.android.material}"
    }

    object AndroidTest {
        const val core = "androidx.test:core:${Version.androidTest.androidXTestCore}"
        const val runner = "androidx.test:runner:${Version.androidTest.androidXTestCore}"
        const val rules = "androidx.test:rules:${Version.androidTest.androidXTestCore}"

        const val junit = "androidx.test.ext:junit:${Version.androidTest.androidXTestCore}"

        const val espressoCore = "androidx.test.espresso:espresso-core:${Version.androidTest.androidXEspresso}"
        const val espressoIntents = "androidx.test.espresso:espresso-intents:${Version.androidTest.androidXEspresso}"
        const val espressoWeb = "androidx.test.espresso:espresso-web:${Version.androidTest.androidXEspresso}"

        const val uiAutomator = "androidx.test.uiautomator:uiautomator:${Version.androidTest.androidXUiAutomator}"

        const val robolectric = "org.robolectric:robolectric:${Version.androidTest.robolectric}"
    }
}
