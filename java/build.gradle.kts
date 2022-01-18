import org.gradle.internal.os.OperatingSystem

plugins {
    `java-library`
    `maven-publish`
    signing
}

group = "org.kr"
version = "1.5.8"
val NAME = project.name

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scijava:native-lib-loader:2.4.0")
}

java {
    modularity.inferModulePath.set(true)
    withJavadocJar()
    withSourcesJar()
}

tasks {
    compileJava {
        options.encoding = "utf-8"
        options.release.set(11)
        doFirst {
            val os = OperatingSystem.current()
            val lineBreak = when {
                os.isWindows -> "\r\n"
                else -> "\n"
            }
            file("src/main/java/org/kr/jctp/jctpJNI.java").run {
                writeText(readText(Charsets.UTF_8).replace(
                                "  static {$lineBreak" +
                                "    swig_module_init();$lineBreak" +
                                "  }$lineBreak", ""), Charsets.UTF_8)
            }
        }
    }
    jar {
        manifest.attributes(mapOf(
            "Implementation-Title" to NAME,
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to "kr"
        ))
        from("../lib") {
            include("*.dll")
            into("natives/windows_64")
        }
        from("../lib") {
            include("*.so")
            into("natives/linux_64")
        }
    }
    withType(Javadoc::class.java) {
        options {
            this as StandardJavadocDocletOptions
            addStringOption("Xdoclint:none", "-quiet")
            encoding = "UTF-8"
        }
    }
}
