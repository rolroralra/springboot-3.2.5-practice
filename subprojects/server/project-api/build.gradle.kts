dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation(project(":repository:project-jpa-repository"))

    runtimeOnly("com.h2database:h2")
}
