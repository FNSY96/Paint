package modifiedShapes;
import javafx.scene.shape.Line;
import shapeEditors.DeleteNode;

public class ModifiedLine extends Line {

    public void makeDeletable (ModifiedLine Me, DeleteNode DeletableNode){
        this.setOnMouseClicked(e->{            
            DeletableNode.setDeletableNode(Me);            
        });
    }/*method*/
}
