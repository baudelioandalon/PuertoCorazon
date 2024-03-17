plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
apply {
    plugin(Dependency.pluginKotlinApp)
    plugin(Dependency.pluginKotlinKapt)
    plugin(Dependency.pluginKotlinParcelize)
}

android {
    compileSdk = AndroidConfig.compileSdk
    namespace = "com.boreal.puertocorazon.uisystem"

    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        dataBinding = true
        compose = true
        buildConfig = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}
repositories {
    google()
    jcenter()
    mavenCentral()
    maven(url = "https://www.jitpack.io")
}
dependencies {
    implementation(project(":commonutils"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    api(Dependency.appcompat)
    api(Dependency.constraintlayout)
    api(Dependency.coreKtx)

    implementation(Dependency.material)
    implementation(Dependency.realtimeBlurView)
    implementation(Dependency.circularProgress)
    implementation(Dependency.roundableImageView)
    implementation(Dependency.lottie)

    //Compose
    implementation(Dependency.composeUiUi)
    implementation(Dependency.composeUiUtil)
    implementation(Dependency.composeUiTooling)
    implementation(Dependency.composeFoundation)
    implementation(Dependency.composeMaterial)
    implementation(Dependency.composeIconsCore)
    implementation(Dependency.composeIconsExtended)
    implementation(Dependency.composeRuntimeLiveData)
    implementation(Dependency.composeActivity)
    implementation(Dependency.composeLifecycleViewModel)
}
