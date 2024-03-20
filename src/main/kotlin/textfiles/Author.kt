package textfiles

class Author(private val strings: List<String>, private val target: TextFile, private val randomNumberGenerator: java.util.Random) : Runnable {
    override fun run() {
        for (string in strings) {
            target.insertText(randomNumberGenerator.nextInt(0, target.length + 1), string)
        }
    }
}