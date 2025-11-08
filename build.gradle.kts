import org.jetbrains.changelog.Changelog
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.7.1"
//    id("org.jetbrains.intellij.platform.migration") version "2.1.0"
    id("org.jetbrains.changelog") version "2.4.0"
}

fun dateValue(pattern: String): String = LocalDate.now(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ofPattern(pattern))

group = "com.liuujun"
version = "1.1.8"

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public")
    }
    mavenCentral()
    intellijPlatform{
        defaultRepositories()
    }
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.17.0")
    implementation("org.apache.commons:commons-lang3:3.18.0")

    intellijPlatform{
        intellijIdeaUltimate("2025.2")
        bundledPlugins("com.intellij.java")
//        instrumentationTools()
    }
}

intellijPlatform {
    buildSearchableOptions = true
    instrumentCode = true
    projectName = project.name

    pluginConfiguration {
        id = "com.liuujun.class2dml"
        name = "Class2String"
        version = project.version.toString()

        ideaVersion{
            sinceBuild = "232"
            untilBuild = "253.*"
        }

        vendor {
            name = "LiuJun"
            email = "liujun@liuujun.com"
            url = "https://liuujun.com"
        }
    }
    publishing {
        token = System.getenv("IDEA_TOKEN")
        channels = listOf("default")
        ideServices = false
        hidden = false
    }
}

// Read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    header.set(provider { "${version.get()} (${dateValue("yyyy/MM/dd")})" })
    groups.empty()
    repositoryUrl = "https://plugins.jetbrains.com/plugin/24215-class2string"
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
    }

    patchPluginXml {
        changeNotes.set(provider {
            changelog.renderItem(
                changelog
                    .getLatest()
                    .withHeader(false)
                    .withEmptySections(false),
                Changelog.OutputType.HTML
            )
        })
    }
}
