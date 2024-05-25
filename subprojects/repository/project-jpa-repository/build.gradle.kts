val testContainersVersion: String by project

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("com.h2database:h2")
//    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

//    testImplementation("org.testcontainers:mariadb")
}


