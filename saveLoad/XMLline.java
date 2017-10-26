/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saveLoad;

import javafx.scene.paint.Color;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import modifiedShapes.ModifiedLine;

/**
 *
 * @author MMERS
 */
@XmlRootElement
public class XMLline {
    private ModifiedLine line = new ModifiedLine();
	private double startX;
	private double startY;
	private double endX;
	private double endY;
	private String fillString;
        
        public XMLline(){
        }
        public XMLline(ModifiedLine line){
        this.line = line;
        this.startX = line.getStartX();
        this.startY = line.getStartY();
        this.endX = line.getEndX();
	this. endY = line.getEndY();
	this.fillString = line.getStroke().toString(); 
        
        }
@XmlElement
    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }
@XmlElement
    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }
@XmlElement
    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }
@XmlElement
    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }
@XmlElement
    public String getFillString() {
        return fillString;
    }

    public void setFillString(String fillString) {
        this.fillString = fillString;
    }
}
