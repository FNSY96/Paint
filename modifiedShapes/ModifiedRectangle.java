package modifiedShapes;

import java.util.ArrayList;
/**
 * This is the class that extends Rectangle class.
 * Up till now it is empty.
 * But the "modified shapes" package may be usefull.
 * Notice that the classes "Modified ..." are all used to 
 * create the shapes in the package project assembler.
 */

import javafx.scene.shape.Rectangle;
import javafx.geometry.Point2D;
import shapeEditors.DeleteNode;

public class ModifiedRectangle extends Rectangle {
	
	private ArrayList<Point2D> coordinates ;
        
        public void makeDeletable (ModifiedRectangle Me, DeleteNode DeletableNode){
        this.setOnMouseClicked(e->{            
             DeletableNode.setDeletableNode(Me);            
           });
        }/*method*/

}
