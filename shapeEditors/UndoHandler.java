/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapeEditors;

import java.io.StringReader;
import java.io.StringWriter;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javax.xml.bind.*;
import modifiedShapes.ModifiedEllipse;
import modifiedShapes.ModifiedLine;
import modifiedShapes.ModifiedRectangle;
import modifiedShapes.ModifiedTriangle;


/**
 *
 * @author MMERS
 */
public abstract class UndoHandler {
    
        
    public static ObservableList<Node> saveSnapshot (ObservableList<Node> Input, DeleteNode DeletableNode){
      ArrayList<Node> Output = new ArrayList();
      int InputSize = Input.size();
      for(int i=0; i<InputSize; i++){
         if(Input.get(i) instanceof ModifiedEllipse){
             
             ModifiedEllipse circle = new ModifiedEllipse();
             circle.setCenterX(((ModifiedEllipse)Input.get(i)).getCenterX());
             circle.setCenterY(((ModifiedEllipse)Input.get(i)).getCenterY());
             circle.setRadiusX(((ModifiedEllipse)Input.get(i)).getRadiusX());
             circle.setRadiusY(((ModifiedEllipse)Input.get(i)).getRadiusY());
             circle.setFill(((ModifiedEllipse)Input.get(i)).getFill());
             circle.makeDeletable(circle,DeletableNode);
             Output.add(circle);         
             
         }/*if*/
         else if(Input.get(i) instanceof ModifiedLine){
             ModifiedLine line = new ModifiedLine();
             line.setStartX(((ModifiedLine)Input.get(i)).getStartX());
             line.setStartY(((ModifiedLine)Input.get(i)).getStartY());
             line.setEndX(((ModifiedLine)Input.get(i)).getEndX());
             line.setEndY(((ModifiedLine)Input.get(i)).getEndY());
             line.setStroke(((ModifiedLine)Input.get(i)).getStroke());
             line.setStrokeWidth(((ModifiedLine)Input.get(i)).getStrokeWidth());
             line.makeDeletable(line,DeletableNode);
             Output.add(line);             
         }/*else if*/
         
         else if(Input.get(i) instanceof ModifiedRectangle){
             ModifiedRectangle rect = new ModifiedRectangle();
             rect.setX(((ModifiedRectangle)Input.get(i)).getX());
             rect.setY(((ModifiedRectangle)Input.get(i)).getY());
             rect.setWidth(((ModifiedRectangle)Input.get(i)).getWidth());
             rect.setHeight(((ModifiedRectangle)Input.get(i)).getHeight());
             rect.setFill(((ModifiedRectangle)Input.get(i)).getFill());
             rect.makeDeletable(rect,DeletableNode);
             Output.add(rect);             
         }/*else if*/
         
         else if (Input.get(i) instanceof ModifiedTriangle){
           ModifiedTriangle tri = new ModifiedTriangle();
           tri.getPoints().addAll(((ModifiedTriangle)Input.get(i)).getPoints());
           tri.setFill(((ModifiedTriangle)Input.get(i)).getFill());   
           tri.makeDeletable(tri,DeletableNode);
           Output.add(tri);     
         }
      }/*for*/
      System.out.println("Pushed");
      ObservableList<Node> result = FXCollections.observableArrayList(Output);
      return result;
    }/*method*/  
    
    public static Boolean containsSmallObjects(ObservableList Input){
      int InputSize = Input.size();
      for(int i=0; i<InputSize; i++){
         if(Input.get(i) instanceof ModifiedEllipse){
              ModifiedEllipse circle = new ModifiedEllipse();
              circle.setRadiusX(((ModifiedEllipse)Input.get(i)).getRadiusX());
              if(((ModifiedEllipse)Input.get(i)).getRadiusX()<1){
                 System.out.println(((ModifiedEllipse)Input.get(i)).getRadiusX());
                  return TRUE;                  
              }             
         }
      }/*for*/
      
      return FALSE;
    }/*method*/
    
    public static void FilterUndoStack (Stack<ObservableList> undo){
        int found=1;
        while(!undo.isEmpty() && found == 1){
          
          if(containsSmallObjects(undo.peek())){
             undo.pop();
          }
          else{
           found = 0;            
          }
                  
        }/*while*/
    }/*method*/    
}/*Class*/
