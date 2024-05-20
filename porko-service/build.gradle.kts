val testSourceSet: SourceSetOutput = project(":porko-common").sourceSets["test"].output

object DependencyVersions {
    const val JJWT_VERSION = "0.11.5"
}

dependencies {
    implementation(project(":porko-common"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("io.jsonwebtoken:jjwt-api:${DependencyVersions.JJWT_VERSION}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${DependencyVersions.JJWT_VERSION}")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${DependencyVersions.JJWT_VERSION}")

    testRuntimeOnly("com.h2database:h2")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation(testSourceSet)
}
