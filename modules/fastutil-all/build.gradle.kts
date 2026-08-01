plugins {
    id("fastutil")
}

dependencies {
    api(project(":modules:core"))
    api(project(":modules:io"))

    for (type in Type.TYPES) {
        api(project(":modules:commons:$type-common"))
        api(project(":modules:sets:$type-sets"))
        api(project(":modules:big-lists:$type-big-lists"))

        if (type != Type.REFERENCE && type != Type.BOOLEAN) {
            api(project(":modules:queues:$type-queues"))
        }

        if (type != Type.BOOLEAN) {
            for (valueType in Type.TYPES) {
                api(project(":modules:maps:$type-$valueType-maps"))
            }
        }
    }
}

tasks.jar {
    manifest.attributes["Automatic-Module-Name"] = "fastutil.all"
}
