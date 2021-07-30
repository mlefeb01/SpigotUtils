# SpigotUtils
A work in progress library containing code that I find useful. SpigotUtils is split into 
two parts: API and Plugin. The API is standalone and can be used independently. The plugin 
builds upon the API providing more utilities to make plugin development less verbose

### Motivation
When developing plugins, it is very common to reuse code like any type of development. Simply
copy-pasting your code between different projects creates maintainability issues. By creating a
library all this reusable code can be kept in one place and expanded upon.

### Shading the API into your Jar
If you plan on shading the API into your JAR you should relocate the API to avoid potential version
conflicts with other plugins doing the same. Below is an example of how to do so using gradle
```$xslt
plugins {
    id 'java'
    id 'io.freefair.lombok' version '5.1.1'
    id 'com.github.johnrengelman.shadow' version '4.0.2'
}

group 'com.github.mlefeb01.stubplugin'
version '1.0-SNAPSHOT'

sourceCompatibility = 1.8

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation 'org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT'
    compile ('com.github.mlefeb01.spigotutils-api:SpigotUtils-API:VERSION') {
        exclude group: 'org.spigotmc', module: 'spigot-api'
    }
}

shadowJar {
    relocate('com.github.mlefeb01.spigotutils', 'com.github.mlefeb01.changeme.spigotutils')
    configurations = [project.configurations.compile]
    destinationDir = file('build/libs/')
    classifier=null
    minimize()
    dependencies {
        manifest {
            attributes("Main-Class": "")
        }
    }
}
```

### References
- [Comparison between JSON and YAML for data serialization](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.1048.2508&rep=rep1&type=pdf)
- ["Effective Java Second Edition" by Joshua Bloch](https://www.amazon.com/Effective-Java-2nd-Joshua-Bloch/dp/0321356683)
- ["Darts, Dice, and Coins: Sampling from a Discrete Distribution"](https://www.keithschwarz.com/darts-dice-coins/)