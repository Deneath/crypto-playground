import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    jcenter()
    maven("https://jitpack.io")
}
val versionName = "1.0.98"
val versionCode = 1
val compileVersion = 28
val minVersion = 21
val targetVersion = 28
val kotlinVersion = "1.4.31"
val supportVersion = "1.0.0"
val constraintVersion = "1.1.3"
val rxJavaVersion = "2.2.7"
val coroutinesVersion = "1.4.2"
val gsonVersion = "2.8.2"
val zXingVersion = "3.5.0"
val spoungyCastleVersion = "1.58.0.0"
val ed25519Version = "0.3.0"
val bip39version = "2019.01.27"
val web3jVersion = "4.6.0-android"
val apacheXercesVersion = "2.9.0"
val junitVersion = "4.12"
val mockitoVersion = "2.23.0"
val wsVersion = "2.10"
val xxHashVersion = "1.7.1"
val svgVersion = "1.4"

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.madgag.spongycastle:bcpkix-jdk15on:$spoungyCastleVersion")
    implementation("com.madgag.spongycastle:bcpg-jdk15on:$spoungyCastleVersion")
    implementation("net.i2p.crypto:eddsa:$ed25519Version")
    implementation("org.web3j:crypto:$web3jVersion")
    implementation("org.eclipse.birt.runtime.3_7_1:org.apache.xerces:$apacheXercesVersion")
    implementation("io.github.novacrypto:BIP39:$bip39version")
    implementation("io.github.novacrypto:securestring:$bip39version@jar")

//    implementation("io.ktor:ktor-client-core:1.6.8")
//    implementation("io.ktor:ktor-client-cio:1.6.8")
//    implementation("io.ktor:ktor-client-websockets:1.6.8")
    implementation("com.neovisionaries:nv-websocket-client:2.14")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("com.google.code.gson:gson:2.9.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}