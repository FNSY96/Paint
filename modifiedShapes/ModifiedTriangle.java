/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modifiedShapes;

import javafx.scene.shape.Polygon;
import shapeEditors.DeleteNode;

/**
 *
 * @author MMERS
 */
public class ModifiedTriangle extends Polygon {
   
    public void makeDeletable (ModifiedTriangle Me, DeleteNode DeletableNode){
        this.setOnMouseClicked(e->{            
            DeletableNode.setDeletableNode(Me);            
        });
    }/*method*/
    
}/*Class*/
