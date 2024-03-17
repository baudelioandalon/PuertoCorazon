buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven (url ="https://www.jitpack.io")
        maven {
            url = uri("https://storage.googleapis.com/r8-releases/raw")
        }
    }
    allprojects {
        repositories {
            google()
            jcenter()
            mavenCentral()
            maven (url ="https://www.jitpack.io")
            maven {
                url = uri("https://storage.googleapis.com/r8-releases/raw")
            }
        }
    }
    dependencies {
        classpath(Dependency.pluginKotlin)
        classpath(Dependency.pluginBuildTools)
        classpath(Dependency.pluginSageArgs)
        classpath(Dependency.pluginGmsGoogleServices)
        classpath(Dependency.pluginFirebaseCrashlyticsGradle)
        classpath(Dependency.realmGradle)
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.46.1")
        classpath("com.android.tools:r8:8.2.24")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}