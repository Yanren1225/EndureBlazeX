//// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.vanniktech.maven.publish.*
import com.vanniktech.maven.publish.nexus.*

buildscript {
    apply("version.gradle.kts")
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin_version"]}")
        classpath("com.github.RikkaW:gradle-maven-publish-plugin:87b40318c2")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.4.20")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

val Project.android get() = extensions.getByName<com.android.build.gradle.BaseExtension>("android")

subprojects {
    afterEvaluate {

        if (plugins.hasPlugin("com.android.library") ||
            plugins.hasPlugin("com.android.application")
        ) {
            android.apply {
                compileSdkVersion(30)
                buildToolsVersion = "30.0.3"

                defaultConfig {
                    if (minSdkVersion == null)
                        minSdkVersion(21)
                    targetSdkVersion(30)
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
            }
        }

        if (plugins.hasPlugin("com.vanniktech.maven.publish")) {
            group = if (this.parent == rootProject) {
                "${project.extra["groupIdBase"]}.${this.name}"
            } else {
                "${project.extra["groupIdBase"]}.${this.parent?.name}"
            }
            android.defaultConfig.versionName?.also {
                version = it
            }
            println("${this.displayName}: ${group}:${this.name}:${version}")
            val mavenPublish = MavenPublishPlugin()
            mavenPublish.apply {
                NexusOptions(
                    "https://s01.oss.sonatype.org",
                    "ren.imyan",
                    "${project.extra["mavenCentralUsername"]}",
                    "${project.extra["mavenCentralPassword"]}"
                )
            }
        }
    }
}
