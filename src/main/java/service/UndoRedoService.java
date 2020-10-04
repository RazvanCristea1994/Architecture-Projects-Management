package service;

import model.Entity;
import model.undoredo.UndoableRedoableOperation;

import java.util.Stack;

public class UndoRedoService {

    private Stack<UndoableRedoableOperation<? extends Entity>> undoStack;
    private Stack<UndoableRedoableOperation<? extends Entity>> redoStack;

    public UndoRedoService() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public void addToUndo(UndoableRedoableOperation<? extends Entity> undoableRedoableOperation){
        undoStack.push(undoableRedoableOperation);
        redoStack.clear();
    }

    public boolean undo(){
        if (!this.undoStack.isEmpty()){
            UndoableRedoableOperation<? extends Entity> undoableRedoableOperation = this.undoStack.pop();
            undoableRedoableOperation.undo();
            this.redoStack.push(undoableRedoableOperation);
            return true;
        }
        return false;
    }

    public boolean redo(){
        if(!this.redoStack.isEmpty()){
            UndoableRedoableOperation<? extends Entity> undoableRedoableOperation = this.redoStack.pop();
            undoableRedoableOperation.redo();
            this.undoStack.push(undoableRedoableOperation);
            return true;
        }
        return false;
    }
}
