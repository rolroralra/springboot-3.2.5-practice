val guavaVersion: String by project
val gsonVersion: String by project

dependencies {
    api("com.google.guava:guava:$guavaVersion")
    api("org.apache.commons:commons-lang3")
    api("com.google.code.gson:gson:$gsonVersion")

}
