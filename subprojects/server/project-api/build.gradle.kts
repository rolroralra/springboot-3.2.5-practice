dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation(project(":common"))
    implementation(project(":repository:project-jpa-repository"))

    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.restdocs:spring-restdocs-restassured")
    testImplementation("io.rest-assured:rest-assured")
}

tasks.bootJar {
    enabled = true
}

tasks.bootBuildImage {
    val imageNameProperty = project.findProperty("imageName") as String? ?: "com.example.project-api"
    val imageTagProperty = project.findProperty("imageTag") as String? ?: project.version.toString()

    imageName.set("$imageNameProperty:$imageTagProperty")
}
