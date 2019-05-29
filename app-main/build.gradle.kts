plugins {
    java
    application
}

application {
    mainClassName = "greeter.Main"
}

dependencies {
    compile(project(":lexer"))
    compile(project(":parser"))
    compile(project(":interpreter"))
}
