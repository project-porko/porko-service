val testSourceSet: SourceSetOutput = project(":porko-common").sourceSets["test"].output

dependencies {
    implementation(project(":porko-common"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testRuntimeOnly("com.h2database:h2")
    testImplementation(testSourceSet)
}
