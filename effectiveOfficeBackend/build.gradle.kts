import org.jetbrains.kotlinx.serialization.compiler.resolve.CallingConventions.update

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project
val ktorm_version: String by project
val postgresql_driver_version: String by project

plugins {
    kotlin("jvm") version "1.8.22"
    id("io.ktor.plugin") version "2.3.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.22"
    id("org.liquibase.gradle") version "2.2.0"
}

group = "office.effective"
version = "0.0.1"
application {
    mainClass.set("office.effective.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
    fatJar {
        archiveFileName.set("backendApp.jar")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")
    implementation("org.ktorm:ktorm-core:$ktorm_version")
    implementation("org.ktorm:ktorm-support-postgresql:$ktorm_version")
    implementation("org.postgresql:postgresql:$postgresql_driver_version")

    implementation("org.liquibase:liquibase-core:4.20.0")
    liquibaseRuntime("org.liquibase:liquibase-core:4.20.0")
    liquibaseRuntime("org.postgresql:postgresql:$postgresql_driver_version")
    liquibaseRuntime("org.yaml:snakeyaml:2.0")
    liquibaseRuntime("ch.qos.logback:logback-core:1.2.3")
    liquibaseRuntime("ch.qos.logback:logback-classic:1.2.3")
    liquibaseRuntime("info.picocli:picocli:4.6.3")
    liquibaseRuntime("org.liquibase:liquibase-groovy-dsl:3.0.2")
    liquibaseRuntime("org.liquibase:liquibase-gradle-plugin:2.1.1")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

apply(plugin = "org.liquibase.gradle")

liquibase {
    activities.register("main") {
        this.arguments = mapOf(
            "logLevel" to "info",
            "changelogFile" to "src/main/resources/changelog/changelog.yaml",
            "url" to "jdbc:postgresql://localhost:15432/effectiveOfficeBackendDB",
            "username" to "postgres",
            "password" to "test1234567890",
            "driver" to "org.postgresql.Driver",
            "defaultSchemaName" to "public" //liquibase?
        )
    }
    runList = "main"
}
