plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.minlmedia"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.minlmedia"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    Ktor
    implementation("io.ktor:ktor-client-core:2.3.10")
    implementation("io.ktor:ktor-client-cio:2.3.10")
    implementation("io.ktor:ktor-client-android:2.3.10")
    implementation("io.ktor:ktor-client-serialization:2.3.10")
    implementation("io.ktor:ktor-client-logging:2.3.10")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("io.ktor:ktor-client-json-jvm:2.3.10")
    implementation("io.ktor:ktor-client-serialization-jvm:2.3.10")


//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-jso:1.3.0")

    implementation("com.google.code.gson:gson:2.8.8")

    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
//
//
    implementation("com.squareup.picasso:picasso:2.71828")


    implementation ("androidx.appcompat:appcompat:1.3.0-beta01")

    implementation ("androidx.cardview:cardview:1.0.0")

    implementation ("com.google.android.material:material:1.2.1")

    implementation ("androidx.compose.ui:ui:1.0.5")
    implementation ("androidx.activity:activity-compose:1.3.1")
    implementation ("androidx.compose.material:material:1.0.5")
    implementation ("androidx.activity:activity:1.3.1")
    implementation("io.ktor:ktor-client-json:2.3.10")





//    SFJ
    implementation ("org.slf4j:slf4j-api:1.7.32" )// SLF4J API
    implementation ("ch.qos.logback:logback-classic:1.2.6")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")

}