package textfiles;

import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

final class LazyTextFile implements TextFile {
    TextFile target;
    Pair<Integer, String> pendingInsertion;

    public LazyTextFile(TextFile target) {
        this.target = target;
        this.pendingInsertion = null;
    }

    private void applyPendingInsertion() {
        if (this.pendingInsertion != null) {
            this.target.insertText(this.pendingInsertion.getFirst(), this.pendingInsertion.getSecond());
        }
    }

    @Override
    public int getLength() {
        applyPendingInsertion();
        return target.getLength();
    }

    @Override
    public void insertText(int offset, @NotNull String toInsert) {
        if (this.pendingInsertion != null && this.pendingInsertion.getFirst() == offset) {
            this.pendingInsertion = new Pair<>(offset, toInsert + this.pendingInsertion.getSecond());
            return;
        }
        applyPendingInsertion();
        this.pendingInsertion = new Pair<>(offset, toInsert);
    }

    @Override
    public void deleteText(int offset, int size) {
        applyPendingInsertion();
        target.deleteText(offset, size);
    }

    @Override
    public String toString() {
        applyPendingInsertion();
        return target.toString();
    }

    @Override
    public int compareTo(@NotNull TextFile other) {
        return this.toString().compareTo(other.toString());
    }
}
