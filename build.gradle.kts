import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.bundling.BootJar

val javaVersion = JavaVersion.VERSION_21
val springBootVersion: String by project
val springCloudVersion: String by project
val springCloudAwsVersion: String by project
val log4jVersion: String by project
val testContainersVersion: String by project
val apacheCommonsLang3Version: String by project
val guavaVersion: String by project
val nettyResolverDnsNativeMacOsVersion = "4.1.109.Final:osx-aarch_64"

plugins {
	java
	id("org.jetbrains.kotlin.jvm")
	id("io.spring.dependency-management")
	id("org.springframework.boot")
}

allprojects {
	group = "com.example.project"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	if (project.childProjects.isNotEmpty()) {
		return@subprojects
	}

	apply(plugin = "java")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.jvm")

	java {
		sourceCompatibility = JavaVersion.VERSION_21
	}

	configurations {
		compileOnly {
			extendsFrom(configurations.annotationProcessor.get())
		}
	}

	the<DependencyManagementExtension>().apply {
		imports {
			mavenBom("org.testcontainers:testcontainers-bom:${testContainersVersion}")
		}
	}

	dependencies {
		compileOnly("org.projectlombok:lombok")

		implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
		implementation(platform("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion"))
		implementation(platform("org.testcontainers:testcontainers-bom:${testContainersVersion}"))
//		implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:spring-cloud-aws-dependencies:$springCloudAwsVersion"))

//		implementation("org.apache.commons:commons-lang3:${apacheCommonsLang3Version}")
//		implementation("com.google.guava:guava:${guavaVersion}")
//		implementation("io.awspring.cloud:spring-cloud-aws-secrets-manager-config:${springCloudAwsVersion}")

//		runtimeOnly("io.netty:netty-resolver-dns-native-macos:${nettyResolverDnsNativeMacOsVersion}")

		annotationProcessor("org.projectlombok:lombok")
		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.testcontainers:testcontainers:${testContainersVersion}")
		testImplementation("org.testcontainers:junit-jupiter:${testContainersVersion}")
		testCompileOnly("org.projectlombok:lombok")
		testAnnotationProcessor("org.projectlombok:lombok")

		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
//		testRuntimeOnly("io.netty:netty-resolver-dns-native-macos:${nettyResolverDnsNativeMacOsVersion}")
	}

	tasks.withType<JavaCompile> {
		options.compilerArgs.add("-parameters")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
		jvmArgs = listOf(
			"-XX:+EnableDynamicAgentLoading",
			"-Djdk.instrument.traceUsage",
			"-Djdk.attach.allowAttachSelf=true",
			"-XX:+IgnoreUnrecognizedVMOptions"
		)
	}

	tasks.named<BootBuildImage>("bootBuildImage") {
		dependsOn(tasks.withType<BootJar>())
	}

	tasks.bootJar {
		enabled = false
	}
}

tasks.bootJar {
	enabled = false
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
