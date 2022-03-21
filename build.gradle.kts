buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven (url ="https://www.jitpack.io")
    }
    dependencies {
        classpath(Dependency.pluginKotlin)
        classpath(Dependency.pluginBuildTools)
        classpath(Dependency.pluginSageArgs)
        classpath(Dependency.pluginGmsGoogleServices)
        classpath(Dependency.pluginFirebaseCrashlyticsGradle)
        classpath(Dependency.realmGradle)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}