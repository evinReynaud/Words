/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn how to create Gradle builds at https://guides.gradle.org/creating-new-gradle-builds
 */
allprojects {
    repositories {
        // Use Maven Central for resolving dependencies.
        mavenCentral()
    }
}

subprojects {
    tasks.register<Copy>("packageDistribution") {
        dependsOn("jar")
        from("${project.rootDir}/scripts/*.sh")
        from("${project.projectDir}/build/libs/${project.name}.jar") {
            into("lib")
        }
        into("${project.rootDir}/../../Scripts")
    }
}
