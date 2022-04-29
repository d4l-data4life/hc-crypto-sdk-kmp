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

import care.data4life.gradle.crypto.dependency.Dependency
import care.data4life.gradle.crypto.config.LibraryConfig

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.kapt")

    // Android
    id("com.android.library")

    // Publish
    id("care.data4life.gradle.crypto.script.publishing-config")
}

group = LibraryConfig.group

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }

    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.stdLib.common)

                implementation(Dependency.multiplatform.d4l.util.common)
                implementation(Dependency.multiplatform.d4l.result.error)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Dependency.multiplatformTest.kotlin.common)
                implementation(Dependency.multiplatformTest.kotlin.commonAnnotations)

                implementation(Dependency.multiplatformTest.mockK.common)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Dependency.android.androidX.ktx)
                implementation(Dependency.android.androidX.appCompat)

                implementation(Dependency.jvm.bouncyCastleJdk15)

                implementation(Dependency.jvm.moshi)
                configurations["kapt"].dependencies.add(
                    project.dependencies.create(Dependency.jvm.moshiCodeGen)
                )
            }
        }
        val androidAndroidTestRelease by getting
        val androidTestFixtures by getting
        val androidTestFixturesDebug by getting
        val androidTestFixturesRelease by getting
        val androidTest by getting {
            dependsOn(androidAndroidTestRelease)
            dependsOn(androidTestFixtures)
            dependsOn(androidTestFixturesDebug)
            dependsOn(androidTestFixturesRelease)
            dependencies {
                implementation(Dependency.multiplatformTest.kotlin.jvm)
                implementation(Dependency.multiplatformTest.kotlin.jvmJunit)

                implementation(Dependency.multiplatformTest.mockK.junit)

                configurations["kaptTest"].dependencies.add(
                    project.dependencies.create(Dependency.jvm.moshiCodeGen)
                )

                implementation(Dependency.androidTest.robolectric)
            }
        }
        val androidAndroidTest by getting {
            dependsOn(androidMain)
            dependencies {
                implementation(Dependency.multiplatformTest.kotlin.jvm)
                implementation(Dependency.multiplatformTest.kotlin.jvmJunit)

                implementation(Dependency.multiplatformTest.mockK.junit)

                implementation(Dependency.androidTest.robolectric)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.stdLib.jdk8)

                implementation(Dependency.jvm.bouncyCastleJdk15)

                implementation(Dependency.jvm.moshi)
                configurations["kapt"].dependencies.add(
                    project.dependencies.create(Dependency.jvm.moshiCodeGen)
                )
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(Dependency.multiplatformTest.kotlin.jvm)
                implementation(Dependency.multiplatformTest.kotlin.jvmJunit)

                implementation(Dependency.multiplatformTest.mockK.junit)

                implementation(Dependency.jvm.bouncyCastleJdk15)

                configurations["kaptTest"].dependencies.add(
                    project.dependencies.create(Dependency.jvm.moshiCodeGen)
                )
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

android {
    compileSdk = LibraryConfig.android.compileSdkVersion
    resourcePrefix = LibraryConfig.android.resourcePrefix

    defaultConfig {
        minSdk = LibraryConfig.android.minSdkVersion
        targetSdk = LibraryConfig.android.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments.putAll(
            mapOf("clearPackageData" to "true")
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            java.setSrcDirs(setOf("src/androidMain/kotlin"))
            res.setSrcDirs(setOf("src/androidMain/res"))
        }

        getByName("test") {
            java.setSrcDirs(setOf("src/androidTest/kotlin"))
            res.setSrcDirs(setOf("src/androidTest/res"))
        }

        getByName("androidTest") {
            java.setSrcDirs(setOf("src/androidAndroidTest/kotlin"))
            res.setSrcDirs(setOf("src/androidAndroidTest/res"))
        }
    }
}
