# fastutil

This is a fork of https://github.com/vigna/fastutil

`fastutil` extends the [Java™ Collections
Framework](http://download.oracle.com/javase/1.5.0/docs/guide/collections/)
by providing type-specific maps, sets, lists and queues with a small
memory footprint and fast access and insertion; it also provides big
(64-bit) arrays, sets and lists, and fast, practical I/O classes for
binary and text files. It is free software distributed under the [Apache
License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).

The classes implement their standard counterpart interface (e.g., `Map`
for maps) and can be plugged into existing code. Moreover, they provide
additional features (such as bidirectional iterators) that are not
available in the standard classes.

Besides objects and primitive types, `fastutil` classes provide support
for _references_, that is, objects that are compared using the equality
operator rather than the `equals()` method.

## Changes vs upstream

Split up the fat release jar into smaller type and data structure based modules. 

Each module is published as separate maven coordinates.

For example: `com.github.rfresh2.fastutil.maps:int-object-maps:8.5.19`

The `fastutil-bom` platform supplies a consistent version for every module, so
consumers only need to declare the version once:

```kotlin
dependencies {
    api(platform("com.github.rfresh2.fastutil:fastutil-bom:8.5.19"))
    api("com.github.rfresh2.fastutil.maps:object-object-maps")
}
```

Alternatively, `fastutil-all` brings in every module transitively:

```kotlin
dependencies {
    api("com.github.rfresh2.fastutil:fastutil-all:8.5.19")
}
```

These modules are compiled and published with gradle (instead of ant)

## Maven

releases are published to my personal maven: https://maven.2b2t.vc/releases

you can browse all available modules here: https://maven.2b2t.vc/#/releases/com/github/rfresh2/fastutil

## Build

The build requires Make, Bash, and a C compiler/preprocessor

```shell
./gradlew build
```
