val guavaVersion: String by project
val apacheCommonsLang3Version: String by project

dependencies {
    api("com.google.guava:guava:$guavaVersion")
    api("org.apache.commons:commons-lang3:$apacheCommonsLang3Version")
}
