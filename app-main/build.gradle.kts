import org.gradle.jvm.tasks.Jar

plugins {
    java
    application
}

val mainName = "com.wawey.tsinjava.Main"

application {
    mainClassName = mainName
}

dependencies {
    compile(project(":lexer"))
    compile(project(":parser"))
    compile(project(":interpreter"))
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes["Implementation-Title"] = "Gradle Jar File Example"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = mainName
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}
