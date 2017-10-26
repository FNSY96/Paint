/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saveLoad;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MMERS
 */
@XmlRootElement
public class XMLtriangle {
    private Polygon triangle;
    private ArrayList<Double> triangleCoordinates = new ArrayList<>();	
    private String fillString;

    
    @XmlElement
    public ArrayList<Double> getTriangleCoordinates() {
        return triangleCoordinates;
    }

    public void setTriangleCoordinates(ArrayList<Double> triangleCoordinates) {
        this.triangleCoordinates = triangleCoordinates;
    }
    @XmlElement
    public String getFillString() {
        return fillString;
    }

    public void setFillString(String fillString) {
        this.fillString = fillString;
    }
    
    public XMLtriangle(){}
    
    public XMLtriangle(Polygon triangle){
       this.triangle = triangle;
       this.triangleCoordinates.addAll(this.triangle.getPoints());
       this.fillString = this.triangle.getFill().toString();
    }
}/*class*/
