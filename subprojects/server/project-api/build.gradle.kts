dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation(project(":common"))
    implementation(project(":repository:project-jpa-repository"))

    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.restdocs:spring-restdocs-restassured")
    testImplementation("io.rest-assured:rest-assured")
}
