package model.undoredo;

import model.Entity;
import repository.IRepository;

public abstract class UndoableRedoableOperation<T extends Entity> {

    protected IRepository<T> iRepository;

    public UndoableRedoableOperation(IRepository<T> inMemoryRepository) {
        this.iRepository = inMemoryRepository;
    }

    public abstract void undo();
    public abstract void redo();
}
