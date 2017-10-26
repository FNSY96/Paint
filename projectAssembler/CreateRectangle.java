package projectAssembler;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.Stack;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import modifiedShapes.ModifiedRectangle;
import shapeEditors.AfterMove;
import shapeEditors.DeleteNode;
import shapeEditors.RectangleEnable;
import shapeEditors.SetNodeLive;
import shapeEditors.UndoHandler;
/**
 * This class creates the rectangle on the pane.
 * @author FNSY
 *
 */
public class CreateRectangle {
private double startX;
	private double startY;
	private double endX;
	private double endY;
	private ModifiedRectangle rectangle;
	private static Color fillColor = Color.WHITE;
	/**
 * The constructor receives the fill color from the class mainSpace.
 * it also resives the ref of the drawing pane from the mainSpace class. 
 * @param drawSpace
 * @param fillColor
 */
	private CreateRectangle() {


	}
	
	public static void setFillColor(Color color){
		fillColor = color;
	}

	public static void create(Pane drawSpace, Stack<ObservableList> undo, DeleteNode DeletableNode, RectangleEnable RecEn,AfterMove Moved) {
		CreateRectangle createRectangle = new CreateRectangle();
		createRectangle.startX = 0;
		createRectangle.startY = 0;
		createRectangle.endX = 0;
		createRectangle.endY = 0;
		drawSpace.setOnMousePressed(e -> {
			if(RecEn.getIsRectangle().equals(TRUE)){
                        createRectangle.rectangle = new ModifiedRectangle();
                        createRectangle.rectangle.makeDeletable(createRectangle.rectangle,DeletableNode);
			drawSpace.getChildren().add(createRectangle.rectangle);
                        undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));
                        Moved.addAsMoved(FALSE);
			createRectangle.startX = e.getX();
			createRectangle.startY = e.getY();
                        }/*if*/
		});

		drawSpace.setOnMouseDragged(e -> {
			if(RecEn.getIsRectangle().equals(TRUE)){
                        createRectangle.endX = e.getX();
			createRectangle.endY = e.getY();
			double lowerRightX = Math.max(createRectangle.startX, createRectangle.endX);
			double upperLeftX = Math.min(createRectangle.startX, createRectangle.endX);
			double lowerRightY = Math.max(createRectangle.startY, createRectangle.endY);
			double upperLeftY = Math.min(createRectangle.startY, createRectangle.endY);
			createRectangle.rectangle.setX(upperLeftX);
			createRectangle.rectangle.setY(upperLeftY);
			createRectangle.rectangle.setWidth(lowerRightX - upperLeftX); 
			createRectangle.rectangle.setHeight(lowerRightY - upperLeftY); 
			createRectangle.rectangle.setFill(fillColor);
			LiveOff.makeNodeOff(drawSpace);
			e.consume();
                        }/*if*/
		});
                
             drawSpace.setOnMouseReleased(e->{
                    Double Height =  createRectangle.rectangle.getHeight();
                    Double Width  =  createRectangle.rectangle.getWidth(); 
                    
                    if(Height<10 && Width <10){
                       drawSpace.getChildren().remove(createRectangle.rectangle); 
                       undo.pop();
                    }
                });   
           

	}/*Method*/


	
}
