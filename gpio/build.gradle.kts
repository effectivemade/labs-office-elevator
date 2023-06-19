plugins {
    kotlin("multiplatform")
}

defaultTasks("clean", "build")

repositories {
    mavenCentral()
}

kotlin {
    // the ktgpio library only supports ARM targets; we're cross-compiling to ARM
    val nativeTarget = linuxArm64("native")

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
            // ktgpio needs linker to have access to the gpiod and i2c .so libraries
            // which are located in this folder.
            all { linkerOpts.add("-Llibs/") }
        }
    }
    sourceSets {
        val nativeMain by getting {
            dependencies {
                // include the ktgpio library: https://github.com/ktgpio/ktgpio
                implementation("io.ktgp:core:0.0.8")
                implementation(kotlin("stdlib"))
            }
        }
        val nativeTest by getting
    }
}

