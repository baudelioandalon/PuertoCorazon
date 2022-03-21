plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
apply {
    plugin(Dependency.pluginKotlinApp)
    plugin(Dependency.pluginKotlinKapt)
    plugin(Dependency.pluginKotlinParcelize)
    plugin(Dependency.pluginRealm)
}
repositories {
    google()
    jcenter()
    mavenCentral()
    maven(url = "https://www.jitpack.io")
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            buildConfigField(type = "String", name = "ENVIRONMENT", value = "\"DEBUG/\"")
            buildConfigField(type = "String", name = "USERS", value = "\"Users\"")
            buildConfigField(type = "String", name = "LENDERS", value = "\"Lenders\"")
            buildConfigField(type = "String", name = "CLIENTS", value = "\"Clients\"")
            buildConfigField(type = "String", name = "PAYMENTS", value = "\"Payments\"")
            buildConfigField(
                type = "String",
                name = "VERSION_NAME",
                value = "\"AndroidConfig.versionName\""
            )
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = false
            buildConfigField(type = "String", name = "ENVIRONMENT", value = "\"RELEASE/\"")
            buildConfigField(type = "String", name = "USERS", value = "\"Users\"")
            buildConfigField(type = "String", name = "LENDERS", value = "\"Lenders\"")
            buildConfigField(type = "String", name = "CLIENTS", value = "\"Clients\"")
            buildConfigField(type = "String", name = "PAYMENTS", value = "\"Payments\"")
            buildConfigField(
                type = "String",
                name = "VERSION_NAME",
                value = "\"AndroidConfig.versionName\""
            )
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(project(":commonutils"))
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
    api(Dependency.gson)
    api(Dependency.kotlinxCoroutinesCore)
    api(Dependency.firebaseCrashlyticsKtx)
    api(Dependency.navigationUiKtx)
    api(Dependency.navigationFragmentKtx)
    api(Dependency.navigationRuntimeKtx)
    api(Dependency.recyclerview)
    api(Dependency.koinCore)
    api(Dependency.koinAndroid)

    implementation(Dependency.material)
    implementation(Dependency.materialAlpha)
    implementation(Dependency.viewmodelKtx)
    implementation(Dependency.workRuntime)
    implementation(Dependency.lifecycleRuntime)
    implementation(Dependency.lifecycleExtensions)
    implementation(Dependency.activityKtx)
    implementation(Dependency.lifecycleKtx)

    implementation(Dependency.circularProgress)
    implementation(Dependency.realtimeBlurView)
    implementation(Dependency.kProgressHud)
    implementation(Dependency.donutLibrary)
    implementation(Dependency.picassoLibrary)
    implementation(Dependency.glideLibrary)
    implementation(Dependency.glideCompilerLibrary)
    implementation(Dependency.circleImage)

    implementation(Dependency.firebaseAuth)
    implementation(Dependency.firestore)
    implementation(Dependency.firestoreKtx)
    implementation(Dependency.firestoreStorage)

    implementation(Dependency.keyboardVisibility)
    implementation(Dependency.placesLibrary)

    implementation(Dependency.squareupRetrofit)
    implementation(Dependency.squareupLogging)
    implementation(Dependency.squareupGson)

    implementation(Dependency.rxJava2Adapter)

}