import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
    mavenCentral()
    maven (url ="https://www.jitpack.io")
}