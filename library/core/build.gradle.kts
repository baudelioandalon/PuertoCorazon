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
    namespace = "com.boreal.puertocorazon.core"

    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        val properties =
            org.jetbrains.kotlin.konan.properties.loadProperties(project.rootProject.file("local.properties").path)
        getByName("debug") {
            isMinifyEnabled = false
            buildConfigField(type = "String", name = "ENVIRONMENT_ABSOLUTE", value = "\"DEBUG\"")
            buildConfigField(type = "String", name = "ENVIRONMENT", value = "\"DEBUG/\"")
            buildConfigField(
                type = "String",
                name = "MAPS_API_KEY",
                value = "\"${properties.getProperty("GOOGLE_MAPS_KEY")}\""
            )
            buildConfigField(
                type = "String",
                name = "DEFAULT_EMAIL",
                value = "\"administrator@borealnetwork.org/\""
            )//obtener desde RemoteConfig
            buildConfigField(type = "String", name = "EVENTS", value = "\"/Events\"")
            buildConfigField(type = "String", name = "TICKETS", value = "\"Tickets\"")
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://us-central1-borealpuertocorazon.cloudfunctions.net/\""
            )//obtener desde RemoteConfig
            buildConfigField(
                type = "String",
                name = "VERSION_NAME",
                value = "\"AndroidConfig.versionName\""
            )
            buildConfigField(
                type = "String",
                name = "MERCADO_PAGO_PUBLIC",
                value = "\"TEST-337eebca-a95c-426f-b7c1-cc204dbf467d\""
            )
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = false
            buildConfigField(type = "String", name = "ENVIRONMENT", value = "\"RELEASE/\"")
            buildConfigField(
                type = "String",
                name = "MAPS_API_KEY",
                value = "\"${properties.getProperty("GOOGLE_MAPS_KEY")}\""
            )
            buildConfigField(
                type = "String",
                name = "DEFAULT_EMAIL",
                value = "\"administrator@borealnetwork.org/\""
            )//obtener desde RemoteConfig
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://us-central1-borealpuertocorazon.cloudfunctions.net/\""
            )//obtener desde RemoteConfig
            buildConfigField(type = "String", name = "EVENTS", value = "\"/Events\"")
            buildConfigField(type = "String", name = "TICKETS", value = "\"Tickets\"")
            buildConfigField(
                type = "String",
                name = "VERSION_NAME",
                value = "\"AndroidConfig.versionName\""
            )
            buildConfigField(
                type = "String",
                name = "MERCADO_PAGO_PUBLIC",
                value = "\"APP_USR-0619a755-7023-494f-a921-f9dfe236002b\""
            )
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
    implementation(Dependency.lottie)

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
    implementation(Dependency.imageConverterLibrary)

    implementation(Dependency.mercadoPago)
    implementation(Dependency.apacheOrg)

    //QR
    implementation(Dependency.journeyAppsZxing)
    implementation(Dependency.zxingCore)

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