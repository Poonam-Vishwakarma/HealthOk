plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.vcreate.healthok"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vcreate.healthok"
        minSdk = 25
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Firebase auth
    implementation("com.google.firebase:firebase-auth:22.3.1")

    // Firebase realtime database
    implementation("com.google.firebase:firebase-database:20.3.1")

    // Circle image view
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // oktthpt
    implementation("com.squareup.okhttp3:okhttp:4.10.0")


    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")

    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // zego cloud
    implementation("com.github.ZEGOCLOUD:zego_uikit_prebuilt_call_android:+")

    // gson
    implementation("com.google.code.gson:gson:2.10.1")

    // web rtc
    implementation("com.mesibo.api:webrtc:1.0.5")

    // easy permission
    implementation("com.guolindev.permissionx:permissionx:1.7.1")

    // picasso
    implementation("com.squareup.picasso:picasso:2.8")



}