rootProject.name = "project"

include(
    "server:project-api",
    "repository:project-jpa-repository",
    "common",
)


pluginManagement {
    val dockerVersion : String by settings
    val springBootVersion : String by settings
    val springDependencyManagementVersion : String by settings
    val jetbrainsKotlinJvmVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when(requested.id.id){
                "com.palantir.docker" -> useVersion(dockerVersion)
                "org.jetbrains.kotlin.jvm" -> useVersion(jetbrainsKotlinJvmVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
                "org.springframework.boot" -> useVersion(springBootVersion)
            }
        }
    }

    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

makeProjectDir(rootProject, "subprojects")

fun makeProjectDir(project: ProjectDescriptor, group: String) {
    project.children.forEach {
        println("$group -> ${it.name}")

        it.projectDir = file("$group/${it.name}")
        makeProjectDir(it, "$group/${it.name}")
    }
}
