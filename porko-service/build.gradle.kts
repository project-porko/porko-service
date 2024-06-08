val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = true
jar.enabled = false

val testSourceSet: SourceSetOutput = project(":porko-common").sourceSets["test"].output
val queryDslVersion = dependencyManagement.importedProperties["querydsl.version"]

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

    implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation("com.opencsv:opencsv:5.9")

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation(testSourceSet)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val qClassGeneratedPath: String = layout.projectDirectory.dir("src/main/generated").asFile.path

sourceSets {
    main {
        java.srcDirs(qClassGeneratedPath)
    }
}

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.file(qClassGeneratedPath)
    options.compilerArgs.add("-Aquerydsl.generatedAnnotationClass=javax.annotation.Generated")
}

tasks {
    getByName<Delete>("clean") {
        delete.add(qClassGeneratedPath)
    }
}
