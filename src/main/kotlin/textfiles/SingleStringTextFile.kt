package textfiles

class SingleStringTextFile(initialContents: String) : TextFile {
    private val contents = StringBuilder(initialContents)

    override val length: Int
        get() = contents.length

    override fun insertText(offset: Int, toInsert: String) {
        if (offset < 0 || offset > length) throw FileIndexOutOfBoundsException()
        contents.insert(offset, toInsert)
    }

    override fun deleteText(offset: Int, size: Int) {
        if (offset < 0 || offset > length || offset + size < 0 || offset + size > length) throw FileIndexOutOfBoundsException()
        contents.delete(offset, offset + size)
    }

    override fun toString(): String = contents.toString()
}