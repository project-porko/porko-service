val testSourceSet: SourceSetOutput = project(":porko-common").sourceSets["test"].output

dependencies {
    implementation(project(":porko-common"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    testRuntimeOnly("com.h2database:h2")
    testImplementation(testSourceSet)
}
