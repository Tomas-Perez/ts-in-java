plugins {
    java
}

dependencies {
    compile(project(":lexer"))
    compile("org.functionaljava:functionaljava:4.8.1")

    testCompile("junit:junit:4.12")
}

