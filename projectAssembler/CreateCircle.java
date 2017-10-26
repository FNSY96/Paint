package projectAssembler;

import modifiedShapes.ModifiedEllipse;
import shapeEditors.SetNodeLive;

import com.sun.glass.ui.Cursor;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.Stack;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import saveLoad.AiderList;
import saveLoad.XMLcircle;
import shapeEditors.AfterMove;
import shapeEditors.CircleEnable;
import shapeEditors.DeleteNode;
import shapeEditors.UndoHandler;


public class CreateCircle {
	
	private ModifiedEllipse circle = new ModifiedEllipse();;
	private double circleCenterX;
	private double circleCenterY;
	private double circleRadius;
	private static Color fillColor = Color.WHITE;
        private String fillString; 
   
        
        

	public CreateCircle(){
		this.circleCenterX = 0;
		this.circleCenterY = 0;
		this.circleRadius = 0;
		
	}
	
	public static void setFillColor(Color color){
		fillColor = color;
                
	}
        
	
	public  void create(Pane drawSpace, Stack<ObservableList> undo, Stack<ObservableList> redo, DeleteNode DeletableNode, CircleEnable CircEn){
		
		drawSpace.setOnMousePressed(e->{
                 //   if(CircEn.getIsCircle().equals(TRUE)){
			this.circle = new ModifiedEllipse();     
                        drawSpace.getChildren().add(this.circle);
                        this.circle.makeDeletable(circle,DeletableNode);                         
                        System.out.println(drawSpace.getChildren().size()); 
                        
                            
                        undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(), DeletableNode));
                        
                
			this.circleCenterX = e.getX();
			this.circleCenterY = e.getY();
			this.circle.setCenterX(this.circleCenterX);
			this.circle.setCenterY(this.circleCenterY);
                        this.circle.setFill(fillColor);                       
                   // }/*if*/   
                       
		});
		
		drawSpace.setOnMouseDragged(e->{
                        if(CircEn.getIsCircle().equals(TRUE)){
			double x = e.getX() - this.circleCenterX;
			double y = this.circleCenterY - e.getY();
			this.circleRadius = Math.sqrt(x*x + y*y);
			this.circle.setRadiusX(this.circleRadius);
			this.circle.setRadiusY(this.circleRadius);
			this.circle.setFill(fillColor);
			LiveOff.makeNodeOff(drawSpace);
			e.consume();
                       }/*if*/
		});
                
                
           drawSpace.setOnMouseReleased(e->{
                   
                    if(this.circle.getRadiusX()<2){
                       drawSpace.getChildren().remove(this.circle); 
                       undo.pop();
                    }
                    if(drawSpace.getChildren().size()==1){
                        redo.clear();
                         System.out.println("Redo is clear");
                      }
                });
             
              
               
	}
	
	
}
