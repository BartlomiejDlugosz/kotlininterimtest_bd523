package textfiles

import java.util.concurrent.locks.Lock
import kotlin.concurrent.withLock

class ThreadSafeTextFile(private val target: TextFile, private val lock: Lock) : TextFile {
    override val length: Int
        get() = lock.withLock{ target.length }

    override fun insertText(offset: Int, toInsert: String) = lock.withLock { target.insertText(offset, toInsert) }

    override fun deleteText(offset: Int, size: Int) = lock.withLock { target.deleteText(offset, size) }

    override fun toString(): String = lock.withLock { target.toString() }
}