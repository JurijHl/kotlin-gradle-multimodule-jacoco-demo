
plugins {
    id("org.jetbrains.kotlin.jvm") // <1>
    jacoco
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

tasks.jacocoTestReport {
    enabled = true
}

tasks.test {
    useJUnitPlatform() // <7>
}

// Share sources folder with other projects for aggregated JaCoCo reports
configurations.create("transitiveSourcesElements") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    extendsFrom(configurations.implementation.get())

    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("source-folders"))
    }
    sourceSets.main.get().java.srcDirs.forEach {
        outgoing.artifact(it)
    }
}

// Share the coverage data to be aggregated for the whole product
configurations.create("coverageDataElements") {
    isVisible = false
    isCanBeResolved = false
    isCanBeConsumed = true
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("jacoco-coverage-data"))
    }
    // This will cause the test task to run if the coverage data is requested by the aggregation task
    outgoing.artifact(tasks.test.map { task ->
        task.extensions.getByType<JacocoTaskExtension>().destinationFile!!
    })
}
