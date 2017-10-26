package modifiedShapes;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import shapeEditors.DeleteNode;

public class ModifiedEllipse extends Ellipse {
    
    
   
   
   
   
   public void makeDeletable (ModifiedEllipse Me, DeleteNode DeletableNode){
        this.setOnMouseClicked(e->{            
            DeletableNode.setDeletableNode(Me);
            System.out.println("Yes!!");
        });
    }/*method*/
}/*Class*/
