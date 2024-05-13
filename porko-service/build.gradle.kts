
dependencies {
    implementation(project(":porko-common"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testRuntimeOnly("com.h2database:h2")
}
