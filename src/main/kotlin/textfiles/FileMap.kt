package textfiles

import java.util.*

class FileMap {
    private var buckets: List<LinkedList<Pair<String, TextFile>>> = List(4) { LinkedList() }

    var size: Int = 0

    private fun hashingFunction(index: String, bucketsSize: Int = buckets.size) = index.hashCode() and (bucketsSize - 1)

    private fun resizeBuckets() {
        val newSize = buckets.size * 2
        val newBuckets : List<LinkedList<Pair<String, TextFile>>> = List(newSize) { LinkedList() }
        buckets.forEach {
            currentBucket -> currentBucket.forEach {
                newBuckets[hashingFunction(it.first, newSize)].add(Pair(it.first, it.second))
            }
        }
        buckets = newBuckets
    }

    operator fun set(name: String, file: TextFile) {
        val bucket = buckets[hashingFunction(name)]
        bucket.removeIf { it.first == name }
        bucket.add(Pair(name, file))
        size++
        if (buckets.size * 0.75 < size) {
            resizeBuckets()
        }
    }

    operator fun get(name: String): TextFile? = buckets[hashingFunction(name)].find { it.first == name }?.second

    operator fun iterator(): Iterator<Pair<String, TextFile>> {
        class FileMapIterator : Iterator<Pair<String, TextFile>> {
            var currentBucket = 0
            var currentPosition = 0

            override fun hasNext(): Boolean {
                return currentBucket < buckets.size && currentPosition < buckets[currentBucket].size
            }

            override fun next(): Pair<String, TextFile> {
                val toReturn = buckets[currentBucket][currentPosition]
                if (currentPosition < buckets[currentBucket].size - 1) currentPosition++
                else {
                    currentPosition = 0
                    currentBucket++
                    while (currentBucket < buckets.size && buckets[currentBucket].isEmpty()) { currentBucket++ }
                }
                return toReturn
            }

        }
        return FileMapIterator()
    }
}