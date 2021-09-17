
plugins {
    id("demo.kotlin-application-conventions")
}

dependencies {
    implementation(project(":utilities"))
}

application {
    mainClass.set("demo.app.AppKt") // <1>
}
