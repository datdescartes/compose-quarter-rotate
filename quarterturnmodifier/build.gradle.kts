plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.arya21.quarterturnmodifier"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.foundation.layout.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.arya21"
            artifactId = "quarterturnmodifier"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("Quarter Turn Modifier")
                description.set("A Jetpack Compose library for rotating UI elements by quarter turns")
                url.set("https://github.com/datdescartes/compose-quarter-rotate")

                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("dat.descartes")
                        name.set("dat")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/datdescartes/compose-quarter-rotate.git")
                    developerConnection.set("scm:git:ssh://github.com/datdescartes/compose-quarter-rotate.git")
                    url.set("https://github.com/yourusername/compose-quarter-rotate")
                }
            }
        }
    }
    repositories {
        maven {
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = project.findProperty("ossrhUsername") as String? ?: ""
                password = project.findProperty("ossrhToken") as String? ?: ""
            }
        }
    }
}