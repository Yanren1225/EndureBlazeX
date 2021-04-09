mapOf(
    Pair("kotlin_version", "1.4.32")
).entries.forEach {
    project.extra.set(it.key, it.value)
}