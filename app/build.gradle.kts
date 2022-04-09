import com.infinitepower.calculator.compose.buildsrc.ProjectConfig
import com.infinitepower.calculator.compose.buildsrc.AndroidX
import com.infinitepower.calculator.compose.buildsrc.Compose
import com.infinitepower.calculator.compose.buildsrc.Testing
import com.infinitepower.calculator.compose.buildsrc.Hilt
import com.infinitepower.calculator.compose.buildsrc.Material

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = ProjectConfig.namespace
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.applicationId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycleRuntimeKtx)

    testImplementation(Testing.junit)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.espressoCore)
    androidTestImplementation(Testing.composeUiTestJunit4)

    implementation(AndroidX.activityCompose)

    implementation(Compose.composeMaterial3)

    debugImplementation(Compose.uiTooling)
    debugImplementation(Compose.uiTestManifest)
    implementation(Compose.composeUi)
    implementation(Compose.composeUiToolingPreview)
    implementation(Compose.iconsExtended)

    implementation(Material.material)

    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)
    kapt(Hilt.androidXHiltCompiler)
    implementation(Hilt.navigationCompose)
    androidTestImplementation(Hilt.hiltAndroidTesting)
    kaptAndroidTest(Hilt.hiltAndroidCompiler)}