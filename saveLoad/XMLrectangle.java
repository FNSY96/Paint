/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saveLoad;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import modifiedShapes.ModifiedRectangle;

/**
 *
 * @author MMERS
 */

@XmlRootElement
public class XMLrectangle {
    private ModifiedRectangle rectangle;   
    private double X;
    private double Y;
    private double Width;
    private double Height;
    private String fillString;
    
    public XMLrectangle(){}
    
    public XMLrectangle(ModifiedRectangle rectangle){
    this.rectangle = rectangle;   
    this.X = this.rectangle.getX();
    this.Y = this.rectangle.getY();
    this.Width = this.rectangle.getWidth();
    this.Height = this.rectangle.getHeight();
    this.fillString = this.rectangle.getFill().toString();
    
    }
@XmlElement
    public double getX() {
        return X;
    }

    public void setX(double X) {
        this.X = X;
    }
@XmlElement
    public double getY() {
        return Y;
    }

    public void setY(double Y) {
        this.Y = Y;
    }
@XmlElement
    public double getWidth() {
        return Width;
    }

    public void setWidth(double Width) {
        this.Width = Width;
    }
@XmlElement
    public double getHeight() {
        return Height;
    }

    public void setHeight(double Height) {
        this.Height = Height;
    }
@XmlElement
    public String getFillString() {
        return fillString;
    }

    public void setFillString(String fillString) {
        this.fillString = fillString;
    }
    
    
    
    
    
	
}/*class*/
