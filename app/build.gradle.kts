plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")


}

android {
    namespace = "com.ecommerce.project.ciphercart"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ecommerce.project.ciphercart"
        minSdk = 24
        targetSdk = 33
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}
dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //circular image view
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //navigation host
    val nav_version = "2.7.2"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //otp view
    implementation ("io.github.chaosleung:pinview:1.4.4")

    //slider
    implementation ("me.relex:circleindicator:2.1.6")
    implementation ("org.imaginativeworld.whynotimagecarousel:whynotimagecarousel:2.1.0")

    //Animation
    implementation ("com.airbnb.android:lottie:3.4.0")


    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-common-ktx:20.4.0")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.android.gms:play-services-auth:20.7.0")


    //dagger-hilt
    implementation ("com.google.dagger:hilt-android:2.48")
    kapt ("com.google.dagger:hilt-compiler:2.48")


    //quantity
    implementation ("com.github.kojofosu:Quantitizer:1.6.8")

    //load image
    implementation ("com.github.bumptech.glide:glide:4.16.0")


    // places
    implementation ("com.google.android.libraries.places:places:3.3.0")

}
kapt {
    correctErrorTypes = true
}