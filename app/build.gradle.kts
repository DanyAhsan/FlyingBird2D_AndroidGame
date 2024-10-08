import org.gradle.internal.impldep.bsh.commands.dir

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.dany.flyingbird2d"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dany.flyingbird2d"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation ("androidx.appcompat:appcompat:1.0.2")
    implementation ("androidx.constraintlayout:constraintlayout:1.1.3")
    testImplementation ("junit:junit:4.12")
    androidTestImplementation ("androidx.test:runner:1.2.0")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.2.0")


}