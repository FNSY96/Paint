package projectAssembler;


import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.Stack;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import modifiedShapes.ModifiedLine;
import shapeEditors.AfterMove;
import shapeEditors.DeleteNode;
import shapeEditors.LineEnable;
import shapeEditors.UndoHandler;

public class CreateLine {

	private ModifiedLine line;
	private double startX;
	private double startY;
	private double endX;
	private double endY;
	private static Color fillColor = Color.WHITE;
        private static Stack<ObservableList> undo;

	private CreateLine() {
		this.startX = 0;
		this.startY = 0;
		this.endX = 0;
		this.endY = 0;


	}
	public static void setFillColor(Color color){
		fillColor = color;
	}

	public static void create(Pane drawSpace, Stack<ObservableList> undo, DeleteNode DeletableNode,LineEnable LineEn,AfterMove Moved) {
		CreateLine createLine = new CreateLine();
		drawSpace.setOnMousePressed(e -> {
			if(LineEn.getIsLine().equals(TRUE)){
                        createLine.line = new ModifiedLine();
                        createLine.line.makeDeletable(createLine.line,DeletableNode);
			drawSpace.getChildren().add(createLine.line);
                        undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));
                        Moved.addAsMoved(FALSE);
			createLine.startX = e.getX();
			createLine.startY = e.getY();
                        }/*if*/
		});

		drawSpace.setOnMouseDragged(e -> {
			if(LineEn.getIsLine().equals(TRUE)){
                        createLine.endX = e.getX();
			createLine.endY = e.getY();
			createLine.line.setStartX(createLine.startX);
			createLine.line.setStartY(createLine.startY);
			createLine.line.setEndX(createLine.endX);
			createLine.line.setEndY(createLine.endY);
			createLine.line.setStroke(fillColor);
			createLine.line.setStrokeWidth(4);
			LiveOff.makeNodeOff(drawSpace);
			e.consume();
                        }/*if*/
		});
                
                drawSpace.setOnMouseReleased(e->{
                    Double Sx = createLine.line.getStartX();
                    Double Sy = createLine.line.getStartY();
                    Double Ex = createLine.line.getEndX();
                    Double Ey = createLine.line.getEndY();
                    if(LineLength(Sx, Sy, Ex, Ey)<10){
                       drawSpace.getChildren().remove(createLine.line); 
                       undo.pop();
                    }/*if*/
                });
          
	}
        
        private static Double LineLength(Double Sx,Double Sy,Double Ex, Double Ey){
            Double XDiff = Ex-Sx;
            Double YDiff = Ey-Sy;
            Double SumOfSquares = XDiff*XDiff + YDiff*YDiff;
            return Math.sqrt(SumOfSquares);
        }/*method*/

}
