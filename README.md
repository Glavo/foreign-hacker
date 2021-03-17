# Foreign Linker Hacker

[![](https://jitpack.io/v/Glavo/foreign-hacker.svg)](https://jitpack.io/#Glavo/foreign-hacker)

Allows programmers to enable the [Foreign Linker API](https://openjdk.java.net/jeps/389) without adding JVM parameters `-Dforeign.restricted=permit`.

Java 16 or higher is required. 

This library can't avoid other limitations of the incubator module. 
You still need to add `--add-module jdk.incubator.foreign` and  `--enable-preview` options to JVM for use `jdk.incubator.foreign` module.

## Usage: 

```java 
ForeignHacker.enableForeignAccess();
```

The effect of calling this method is similar to starting the JVM with the ``-Dforeign.restricted=permit`` option,
will allow programmers to use the [Foreign Linker API](https://openjdk.java.net/jeps/389).

Node: `System.setProperty("foreign.restricted", "permit")` doesn't work,
Foreign Linker API only detects the value of property `foreign.restricted` passed in when the JVM is started.

## Add hacker to your build

Gradle:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

implementation group: 'org.glavo', name: 'foreign-hacker', version: "0.1.1"
```