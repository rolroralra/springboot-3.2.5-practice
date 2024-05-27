val queryDslVersion = dependencyManagement.importedProperties["querydsl.version"]

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation(platform("com.querydsl:querydsl-bom:$queryDslVersion"))
    implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
    implementation("com.querydsl:querydsl-core:${queryDslVersion}")
    implementation("com.querydsl:querydsl-collections:${queryDslVersion}")

    annotationProcessor("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")

    runtimeOnly("com.h2database:h2")
}

val generatedQueryDsl = "build/generated/querydsl"
val generatedQueryDslDir = file(generatedQueryDsl)

sourceSets {
    main {
        java {
            srcDirs("src/main/java", generatedQueryDsl)
        }
    }
    test {
        java {
            srcDirs("src/test/java", generatedQueryDsl)
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.generatedSourceOutputDirectory.set(generatedQueryDslDir)
}

// JPA 엔티티 클래스만 포함하는 작업 설정
val jpaEntitySources = fileTree("src/main/java") {
    // JPA 어노테이션을 포함한 파일만 필터링
    include {
        val file = it.file
        if (file.isDirectory) {
            return@include false
        }

        val text = file.readText()
        text.contains("@Entity") || text.contains("@MappedSuperclass") || text.contains("@Embeddable")
    }
}

// compileQuerydsl 작업 설정
tasks.register<JavaCompile>("compileQuerydsl") {
    group = "querydsl"
    description = "Compile Querydsl sources"
    source = jpaEntitySources
    classpath = sourceSets.main.get().compileClasspath
    destinationDirectory.set(generatedQueryDslDir)
    options.annotationProcessorPath = configurations.annotationProcessor.get()
    options.generatedSourceOutputDirectory.set(generatedQueryDslDir)
}

// QueryDSL 생성 작업과 종속성 설정
tasks.compileJava {
    dependsOn("compileQuerydsl")
    options.annotationProcessorPath = configurations.annotationProcessor.get()
}

// QueryDSL 생성 작업과 종속성 설정
tasks.compileTestJava {
    dependsOn("compileQuerydsl")
    options.annotationProcessorPath = configurations.testAnnotationProcessor.get()
}


// IntelliJ IDEA 설정
idea {
    module {
        sourceDirs.add(generatedQueryDslDir)
    }
}

tasks.named("idea") {
    dependsOn("compileQuerydsl")
}

// IntelliJ IDEA에서 idea 태스크를 명시적으로 실행하도록 설정
tasks.register("setupIdea") {
    group = "ide"
    description = "Setup IntelliJ IDEA project settings"
    dependsOn("compileQuerydsl", "idea")
}
