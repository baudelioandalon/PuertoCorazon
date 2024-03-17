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
    namespace = "com.boreal.puertocorazon.checking"

    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(project(":library:core"))
    implementation(project(":library:ui-system"))
    androidTestImplementation(Dependency.testAndroidJEspressoCore)
    androidTestImplementation(Dependency.testAndroidJunit)
    androidTestImplementation(Dependency.testAndroidRules)
    androidTestImplementation(Dependency.testAndroidRunner)
    androidTestImplementation(Dependency.testTruth)
    testImplementation(Dependency.testJunit)

    api(Dependency.appcompat)
    api(Dependency.constraintlayout)
    api(Dependency.coreKtx)
    api(Dependency.kotlinxCoroutinesCore)
    api(Dependency.navigationUiKtx)
    api(Dependency.navigationFragmentKtx)
    api(Dependency.navigationRuntimeKtx)
    api(Dependency.recyclerview)
    api(Dependency.koinCore)
    api(Dependency.koinAndroid)

    implementation(Dependency.material)
    implementation(Dependency.viewmodelKtx)
    implementation(Dependency.workRuntime)
    implementation(Dependency.lifecycleRuntime)
    implementation(Dependency.lifecycleExtensions)
    implementation(Dependency.activityKtx)
    implementation(Dependency.lifecycleKtx)

    implementation(Dependency.firestore)
    implementation(Dependency.firestoreKtx)
    implementation(Dependency.firestoreStorage)

    implementation(Dependency.circularProgress)
    implementation(Dependency.realtimeBlurView)
    implementation(Dependency.roundableImageView)

    implementation(Dependency.zxingCore)
    implementation(Dependency.journeyAppsZxing)

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