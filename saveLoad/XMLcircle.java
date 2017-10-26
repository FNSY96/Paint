/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saveLoad;

import javafx.scene.paint.Color;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import modifiedShapes.ModifiedEllipse;

/**
 *
 * @author MMERS
 */
@XmlRootElement
public class XMLcircle {
        private ModifiedEllipse circle = new ModifiedEllipse();
	private double circleCenterX = this.circle.getCenterX();
	private double circleCenterY =this.circle.getCenterY();
	private double circleRadius = this.circle.getRadiusX();	
        private String fillString = this.circle.getFill().toString(); 
        
        public XMLcircle(){}
        
        public XMLcircle(ModifiedEllipse circle){
        
        this.circle = circle;
        circleCenterX = this.circle.getCenterX();
	circleCenterY =this.circle.getCenterY();
	circleRadius = this.circle.getRadiusX();
	//private static Color fillColor = Color.WHITE;
        fillString = this.circle.getFill().toString();
			
        }
    @XmlElement
    public double getCircleCenterX() {
        return circleCenterX;
    }

    public void setCircleCenterX(double circleCenterX) {
        this.circleCenterX = circleCenterX;
    }
    @XmlElement
    public double getCircleCenterY() {
        return circleCenterY;
    }
     
    public ModifiedEllipse getShape() {
        return circle;
    }
    
    public void setCircleCenterY(double circleCenterY) {
        this.circleCenterY = circleCenterY;
    }
    @XmlElement
    public double getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(double circleRadius) {
        this.circleRadius = circleRadius;
    }
    @XmlElement
    public String getFillString() {
        return fillString;
    }

    public void setFillString(String fillString) {
        this.fillString = fillString;
        
    }  
}
