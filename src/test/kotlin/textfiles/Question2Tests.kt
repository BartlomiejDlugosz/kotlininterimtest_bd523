package textfiles

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class Question2Tests {

    @Test
    fun testLength1() {
        val textFile: TextFile = MultiStringTextFile("")
        assertEquals(0, textFile.length)
    }

    @Test
    fun testLength2() {
        val textFile: TextFile = MultiStringTextFile("Hello\nWorld")
        assertEquals(11, textFile.length)
    }

    @Test
    fun testLength3() {
        val textFile: TextFile = MultiStringTextFile(
            "The quick brown fox jumped over the lazy dog ten times today",
        )
        assertEquals(60, textFile.length)
    }

    @Test
    fun testInsert1() {
        val textFile: TextFile = MultiStringTextFile("")
        textFile.insertText(0, "First")
        textFile.insertText(0, "Second")
        textFile.insertText(0, "Third")
        assertEquals("ThirdSecondFirst", textFile.toString())
    }

    @Test
    fun testInsert2() {
        val textFile: TextFile = MultiStringTextFile("SomeContent")
        textFile.insertText(11, "MoreContent")
        textFile.insertText(22, "EvenMoreContent")
        assertEquals("SomeContentMoreContentEvenMoreContent", textFile.toString())
    }

    @Test
    fun testInsert3() {
        val textFile: TextFile = MultiStringTextFile("SomeContent")
        textFile.insertText(0, "!")
        textFile.insertText(2, "!")
        textFile.insertText(4, "!")
        textFile.insertText(6, "!")
        textFile.insertText(8, "!")
        textFile.insertText(10, "!")
        textFile.insertText(12, "!")
        textFile.insertText(14, "!")
        textFile.insertText(16, "!")
        textFile.insertText(18, "!")
        textFile.insertText(20, "!")
        textFile.insertText(22, "!")
        assertEquals("!S!o!m!e!C!o!n!t!e!n!t!", textFile.toString())
    }

    @Test
    fun testInsertException1() {
        try {
            val textFile: TextFile = MultiStringTextFile("SomeContent")
            textFile.insertText(-1, "DoNotAddMe")
        } catch (exception: FileIndexOutOfBoundsException) {
            // Good: an exception should have been thrown.
            return
        }
        fail()
    }

    @Test
    fun testInsertException2() {
        try {
            val textFile: TextFile = MultiStringTextFile("SomeContent")
            textFile.insertText(12, "DoNotAddMe")
        } catch (exception: FileIndexOutOfBoundsException) {
            // Good: an exception should have been thrown.
            return
        }
        fail()
    }

    @Test
    fun testInsertException3() {
        try {
            val textFile: TextFile = MultiStringTextFile("SomeContent")
            textFile.insertText(12, "")
        } catch (exception: FileIndexOutOfBoundsException) {
            // Good: an exception should have been thrown.
            return
        }
        fail()
    }

    @Test
    fun testDelete1() {
        val textFile: TextFile = MultiStringTextFile("")
        textFile.deleteText(0, 0)
        assertEquals("", textFile.toString())
    }

    @Test
    fun testDelete2() {
        val textFile: TextFile = MultiStringTextFile("Hello")
        textFile.deleteText(3, 0)
        assertEquals("Hello", textFile.toString())
    }

    @Test
    fun testDelete3() {
        val textFile: TextFile = MultiStringTextFile("Example text file")
        textFile.deleteText(0, 17)
        assertEquals("", textFile.toString())
    }

    @Test
    fun testDelete4() {
        val textFile: TextFile = MultiStringTextFile("Example text file")
        textFile.deleteText(8, 5)
        assertEquals("Example file", textFile.toString())
    }

    @Test
    fun testDelete5() {
        val textFile: TextFile = MultiStringTextFile("T")
        textFile.deleteText(1, 0)
        assertEquals("T", textFile.toString())
    }

    @Test
    fun testDelete6() {
        val textFile: TextFile = MultiStringTextFile(
            "The quick brown fox jumped over the lazy dog ten times today",
        )
        textFile.deleteText(3, 43)
        assertEquals("Theen times today", textFile.toString())
    }

    @Test
    fun testDeleteException1() {
        try {
            val textFile: TextFile = MultiStringTextFile("SomeContent")
            textFile.deleteText(-1, 3)
        } catch (exception: FileIndexOutOfBoundsException) {
            // Good: an exception should have been thrown.
            return
        }
        fail()
    }

    @Test
    fun testDeleteException2() {
        try {
            val textFile: TextFile = MultiStringTextFile("SomeContent")
            textFile.deleteText(0, 12)
        } catch (exception: FileIndexOutOfBoundsException) {
            // Good: an exception should have been thrown.
            return
        }
        fail()
    }

    @Test
    fun testDeleteException3() {
        try {
            val textFile: TextFile = MultiStringTextFile("SomeContent")
            textFile.deleteText(10, 2)
        } catch (exception: FileIndexOutOfBoundsException) {
            // Good: an exception should have been thrown.
            return
        }
        fail()
    }

    @Test
    fun testDeleteException4() {
        try {
            val textFile: TextFile = MultiStringTextFile("SomeContent")
            textFile.deleteText(100, 0)
        } catch (exception: FileIndexOutOfBoundsException) {
            // Good: an exception should have been thrown.
            return
        }
        fail()
    }

    @Test
    fun testDeleteException5() {
        try {
            val textFile: TextFile = MultiStringTextFile("SomeContent")
            textFile.deleteText(0, -1)
        } catch (exception: FileIndexOutOfBoundsException) {
            // Good: an exception should have been thrown.
            return
        }
        fail()
    }

    @Test
    fun testGetNumBlocksAndRebalance1() {
        val textFile = MultiStringTextFile("")
        assertEquals(0, textFile.numBlocks)
        textFile.rebalance()
        assertEquals(0, textFile.numBlocks)
    }

    @Test
    fun testGetNumBlocksAndRebalance2() {
        val textFile = MultiStringTextFile(
            "AKAI Professional MPK249 midi controller does not act as a stand-alone synthesizer, unfortunately.",
        )
        assertEquals(13, textFile.numBlocks)
        textFile.rebalance()
        assertEquals(13, textFile.numBlocks)
    }

    @Test
    fun testGetNumBlocksAndRebalance3() {
        val textFile = MultiStringTextFile("Harmonious")
        assertEquals(2, textFile.numBlocks)
        textFile.rebalance()
        assertEquals(2, textFile.numBlocks)
        textFile.insertText(2, "A")
        assertEquals(2, textFile.numBlocks)
        textFile.insertText(11, "B")
        assertEquals(3, textFile.numBlocks)
        textFile.insertText(12, "C")
        assertEquals(4, textFile.numBlocks)
        textFile.rebalance()
        assertEquals(2, textFile.numBlocks)
        assertEquals("HaArmoniousBC", textFile.toString())
    }

    @Test
    fun testGetNumBlocksAndRebalance4() {
        val textFile = MultiStringTextFile("Harmonious")
        assertEquals(2, textFile.numBlocks)
        textFile.rebalance()
        assertEquals(2, textFile.numBlocks)
        textFile.deleteText(2, 1)
        assertEquals(3, textFile.numBlocks)
        textFile.deleteText(7, 2)
        assertEquals(2, textFile.numBlocks)
        assertEquals("Hamonio", textFile.toString())
    }
}
