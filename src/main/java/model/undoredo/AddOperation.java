package model.undoredo;

import model.Entity;
import repository.IRepository;

import java.security.KeyException;

public class AddOperation<T extends Entity> extends UndoableRedoableOperation<T> {

    private T addedEntity;

    public AddOperation(IRepository<T> repository, T addedEntity) {
        super(repository);
        this.addedEntity = addedEntity;
    }

    @Override
    public void undo() {
        try {
            this.iRepository.delete(this.addedEntity.getId());
        } catch (KeyException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void redo() {
        this.iRepository.create(this.addedEntity);
    }
}
