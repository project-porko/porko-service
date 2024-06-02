val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

bootJar.enabled = false

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter:1.0.14")
}

tasks.register("copyProperties") {
    doLast {
        copy {
            from("../vault/application")
            into("src/main/resources")
        }
        copy {
            from("../vault/docker")
            into("../")
        }
    }
}

tasks.withType<JavaCompile> {
    dependsOn("copyProperties")
}

tasks {
    getByName<Delete>("clean") {
        delete.add("src/main/resources")
        delete.add("../.env")
        delete.add("../docker-compose.yml")
    }
}
