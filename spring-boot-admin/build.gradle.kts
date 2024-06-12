java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

extra["springBootAdminVersion"] = "3.3.2"

dependencies {
    implementation("de.codecentric:spring-boot-admin-starter-server")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("de.codecentric:spring-boot-admin-dependencies:${property("springBootAdminVersion")}")
    }
}
