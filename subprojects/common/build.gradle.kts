val guavaVersion: String by project

dependencies {
    api("com.google.guava:guava:$guavaVersion")
    api("org.apache.commons:commons-lang3")
}
