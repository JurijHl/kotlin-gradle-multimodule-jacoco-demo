
plugins {
    id("org.jetbrains.kotlin.jvm") // <1>
}

repositories {
    jcenter() // <2>
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom")) // <3>

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8") // <4>

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2") // <5>

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine") // <6>
}