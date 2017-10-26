package projectAssembler;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Stack;
import javafx.collections.ObservableList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import modifiedShapes.ModifiedTriangle;
import shapeEditors.AfterMove;
import shapeEditors.DeleteNode;
import shapeEditors.TriangleEnable;
import shapeEditors.UndoHandler;

public class CreateTriangle {
	
	private ModifiedTriangle triangle;
	private ArrayList<Double> triangleCoordinates;
	private int clicksCount =1;
	private static Color fillColor = Color.WHITE;
	private static int count = 1;
        
        

	private CreateTriangle(){
		
	}
	
	public static void setFillColor(Color color){
		fillColor = color;
	}
	
	public static void create(Pane drawSpace, Stack<ObservableList> undo, DeleteNode DeletableNode, TriangleEnable bug,AfterMove Moved){
		LiveOff.makeNodeOff(drawSpace);

		CreateTriangle createTriangle = new CreateTriangle();
		createTriangle.triangleCoordinates = new ArrayList<>();
		
		drawSpace.setOnMouseClicked(e->{
			if(((createTriangle.clicksCount % 4) > 0) && (bug.getIsTriangle()).equals (TRUE)){
                                count++;
                                System.out.println(count);
				createTriangle.triangle = new ModifiedTriangle();
                                createTriangle.triangle.makeDeletable(createTriangle.triangle,DeletableNode);
				createTriangle.triangleCoordinates.add(e.getX());
				createTriangle.triangleCoordinates.add(e.getY());
				createTriangle.triangle.getPoints().addAll(createTriangle.triangleCoordinates);
				createTriangle.triangle.setFill(fillColor);
				createTriangle.clicksCount++;
				if((createTriangle.clicksCount % 4) == 0){
					drawSpace.getChildren().add(createTriangle.triangle);   
					createTriangle.triangleCoordinates = new ArrayList<>();
					createTriangle.	clicksCount = 5;
					LiveOff.makeNodeOff(drawSpace);
				}
			}

		});
                
                drawSpace.setOnMouseReleased(e->{
                    if(count % 3 == 0 && (bug.getIsTriangle()).equals (TRUE)){
                       undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode)); 
                       Moved.addAsMoved(FALSE);                       
                    }

                });

	}/*Create method*/
	
	
}
