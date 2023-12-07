package solutions.utils

fun <K, V> MutableMap<K, MutableList<V>>.addValue(key: K, newValue: V) {
    this.merge(key, mutableListOf(newValue)) { old, new -> old.apply { addAll(new) } }
}