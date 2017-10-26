package saveLoad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import modifiedShapes.ModifiedEllipse;
import modifiedShapes.ModifiedLine;
import modifiedShapes.ModifiedRectangle;
import modifiedShapes.ModifiedTriangle;
import org.json.simple.parser.JSONParser;

import projectAssembler.CreateCircle;
import projectAssembler.MainSpace;
import shapeEditors.DeleteNode;

public abstract class SaveLoad {
	
	public static void saveJSON(File file,ObservableList<Node> Input) {

		JSONObject countryObj = new JSONObject();

		JSONArray listOfStates = new JSONArray();
		String str = null;	
		
		for (int i = 0; i <Input.size(); i++) {
			         
                                                
                        str = "" + Input.get(i);

			listOfStates.add(str);
		}

		countryObj.put("Shapes", listOfStates);

		try {

			// Writing to a file
		//	File file = new File("E:\\CountryJSONFile.json");
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			System.out.println("Writing JSON object to file");
			System.out.println("-----------------------");
			System.out.print(countryObj + "save");

			fileWriter.write(countryObj.toJSONString());
			fileWriter.flush();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}/*Method*/
	
public static ObservableList<Node> loadJSON(File file, DeleteNode DeletableNode) throws org.json.simple.parser.ParseException {
		
		JSONParser parser = new JSONParser();
		
		//Added by Raafat
		ObservableList<Node> ReturnList =  FXCollections.observableArrayList();

		try {

			Object obj = parser.parse(new FileReader(file));

			JSONObject jsonObject = (JSONObject) obj;

			System.out.println("States are :");
			JSONArray listOfStates = (JSONArray) jsonObject.get("Shapes");
			Iterator<String> iterator = listOfStates.iterator();
			String str;
			while (iterator.hasNext()) {
				str = iterator.next();
				String strBeg;
                                System.out.println(str);
				if (str.startsWith("Rectangle")) {

					double x, y, width, height;
					String fill;
					strBeg = "Rectangle";
					String temp = "" + str.charAt(strBeg.length() + 1);
					for (int i = strBeg.length() + 2; i < str.length() - 1; i++) {
						temp += "" + str.charAt(i);
					}
					temp = temp.replaceAll("\\s+", "");
					String[] splitComma = temp.split(",");

					String[] splitEqual;

					splitEqual = null;
					splitEqual = splitComma[0].split("=");

					x = Double.parseDouble(splitEqual[1]);

					splitEqual = null;
					splitEqual = splitComma[1].split("=");

					y = Double.parseDouble(splitEqual[1]);
					splitEqual = null;
					splitEqual = splitComma[2].split("=");

					width = Double.parseDouble(splitEqual[1]);

					splitEqual = null;
					splitEqual = splitComma[3].split("=");

					height = Double.parseDouble(splitEqual[1]);
					splitEqual = null;
					splitEqual = splitComma[4].split("=");

					fill = splitEqual[1];
					//Added Part by Raafat
					ModifiedRectangle rect = new ModifiedRectangle();
                                        rect.setX(x);
                                        rect.setY(y);
                                        rect.setWidth(width);
                                        rect.setHeight(height);
                                        rect.setFill(Color.web(fill));
                                        rect.makeDeletable(rect,DeletableNode);
                                        ReturnList.add(rect); 			

				} else if (str.startsWith("Ellipse")) {
					
					double centerX, centerY, radiusX, radiusY;
					String fill;

					strBeg = "Ellipse";
					String temp = "" + str.charAt(strBeg.length() + 1);
					for (int i = strBeg.length() + 2; i < str.length() - 1; i++) {
						temp += "" + str.charAt(i);
					}
					temp = temp.replaceAll("\\s+", "");
					String[] splitComma = temp.split(",");

					String[] splitEqual;

					splitEqual = null;
					splitEqual = splitComma[0].split("=");

					centerX = Double.parseDouble(splitEqual[1]);

					splitEqual = null;
					splitEqual = splitComma[1].split("=");

					centerY = Double.parseDouble(splitEqual[1]);
					splitEqual = null;
					splitEqual = splitComma[2].split("=");

					radiusX = Double.parseDouble(splitEqual[1]);

					splitEqual = null;
					splitEqual = splitComma[3].split("=");

					radiusY = Double.parseDouble(splitEqual[1]);
					splitEqual = null;
					splitEqual = splitComma[4].split("=");

					fill = splitEqual[1];
					
					//Added by Raafat
					
					ModifiedEllipse circle = new ModifiedEllipse();
					circle.setCenterX(centerX);
                                        circle.setCenterY(centerY);
                                        circle.setRadiusX(radiusX);
                                        circle.setRadiusY(radiusY);
                                        circle.setFill(Color.web(fill));
                                        circle.makeDeletable(circle,DeletableNode);
                                        ReturnList.add(circle);
					

				} else if (str.startsWith("Polygon")) {
					ArrayList<Double> points = new ArrayList<>();
                                        String   fill;

					strBeg = "Polygon";
					String temp = "" + str.charAt(strBeg.length() + 1);
					for (int i = strBeg.length() + 2; i < str.length() - 1; i++) {
						temp += "" + str.charAt(i);
					}
					temp = temp.replaceAll("\\s+", "");
                                        temp = temp.replaceAll("]","");
					String[] splitComma = temp.split(",");

					

					String[] ss = splitComma[0].split("=");
					String s = "" + ss[1].charAt(1);
					for (int i = 2; i < ss[1].length(); i++) {
						s += "" + ss[1].charAt(i);
					}
                                        
                                        Double p1,p2,p3,p4,p5,p6;
                                        p1 = Double.parseDouble(s);					
					p2 = Double.parseDouble(splitComma[1]);
					p3 = Double.parseDouble(splitComma[2]);
					p4 = Double.parseDouble(splitComma[3]);
					p5 = Double.parseDouble(splitComma[4]);
					p6 = Double.parseDouble(splitComma[5].split("]")[0]);
					// 0,6
                                        
                                        points.add(p1);
                                        points.add(p2);
                                        points.add(p3);
                                        points.add(p4);
                                        points.add(p5);
                                        points.add(p6);

					String[] splitEqual = splitComma[6].split("=");

					fill = splitEqual[1];
                                        
                                        ModifiedTriangle tri = new ModifiedTriangle();
                                        tri.getPoints().addAll(points);
                                        tri.setFill(Color.web(fill));
                                        tri.makeDeletable(tri,DeletableNode);
                                        ReturnList.add(tri);	
                                        
					
				} else if (str.startsWith("Line")) {
					double startX, startY, endX, endY, strokeWidth;
					String stroke;

					strBeg = "Polygon";
					String temp = "" + str.charAt(strBeg.length() + 1);
					for (int i = strBeg.length() + 2; i < str.length() - 1; i++) {
						temp += "" + str.charAt(i);
					}
					temp = temp.replaceAll("\\s+", "");
					String[] splitComma = temp.split(",");

					String[] splitEqual;

					splitEqual = null;
					splitEqual = splitComma[0].split("=");

					startX = Double.parseDouble(splitEqual[1]);

					splitEqual = null;
					splitEqual = splitComma[1].split("=");

					startY = Double.parseDouble(splitEqual[1]);
					splitEqual = null;
					splitEqual = splitComma[2].split("=");

					endX = Double.parseDouble(splitEqual[1]);

					splitEqual = null;
					splitEqual = splitComma[3].split("=");

					endY = Double.parseDouble(splitEqual[1]);

					splitEqual = null;
					splitEqual = splitComma[4].split("=");

					stroke = splitEqual[1];

					splitEqual = null;
					splitEqual = splitComma[5].split("=");

					strokeWidth = Double.parseDouble(splitEqual[1]);
					
					//Added by Raafat
					
					 ModifiedLine line = new ModifiedLine();
                                        line.setStartX(startX);
                                        line.setStartY(startY);
                                        line.setEndX(endX);
                                        line.setEndY(endY);
                                        line.setStrokeWidth(strokeWidth);
                                        line.setStroke(Color.web(stroke));
                                        line.makeDeletable(line,DeletableNode);
                                        ReturnList.add(line);					
				}
				
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
      return ReturnList;
	}/*method*/

       public static void saveXML(File file,ObservableList<Node> Input) throws JAXBException{
             
             AiderList XMLlist = new AiderList(); 
             
             for(int i=0; i<Input.size(); i++){
                 if(Input.get(i) instanceof ModifiedEllipse){                 
                     XMLcircle current = new XMLcircle((ModifiedEllipse)Input.get(i)); 
                     XMLlist.add(current);
                 }
                 else if(Input.get(i) instanceof ModifiedLine){                 
                     XMLline current = new XMLline((ModifiedLine)Input.get(i)); 
                     XMLlist.add(current);
                 }
                 else if(Input.get(i) instanceof ModifiedRectangle){                 
                     XMLrectangle current = new XMLrectangle((ModifiedRectangle)Input.get(i)); 
                     XMLlist.add(current);
                 }
                 else if(Input.get(i) instanceof Polygon){                 
                     XMLtriangle current = new XMLtriangle((Polygon)Input.get(i)); 
                     XMLlist.add(current);
                 }
             }
           
             JAXBContext contextObj = JAXBContext.newInstance(AiderList.class,XMLcircle.class,XMLline.class,XMLrectangle.class,XMLtriangle.class);  
  
             Marshaller marshallerObj = contextObj.createMarshaller();  
      
             marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      
             marshallerObj.marshal(XMLlist, file);
             
          }/*method*/ 
          
          public static ObservableList<Node> loadXML(File file, DeleteNode DeletableNode) throws JAXBException{
              
                 ObservableList<Node> ReturnList =  FXCollections.observableArrayList();
                 JAXBContext jaxbContext = JAXBContext.newInstance(AiderList.class,XMLcircle.class,XMLline.class,XMLrectangle.class,XMLtriangle.class);  
                 Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
                 AiderList XMLlist = (AiderList) jaxbUnmarshaller.unmarshal(file);  
                 System.out.print(XMLlist.size());
               
                 for(int i=0; i<XMLlist.size(); i++){
             
               if(XMLlist.get(i) instanceof XMLcircle){
              
                    ModifiedEllipse circle = new ModifiedEllipse();
                    XMLcircle current = (XMLcircle)(XMLlist.get(i));
                    circle.setCenterX(current.getCircleCenterX());
                    circle.setCenterY(current.getCircleCenterY());
                    circle.setRadiusX(current.getCircleRadius());
                    circle.setRadiusY(current.getCircleRadius());
                    circle.setFill(Color.web(current.getFillString()));
                    circle.makeDeletable(circle,DeletableNode);
                    ReturnList.add(circle);
               }/*if*/           
               else if(XMLlist.get(i) instanceof XMLline){
                    ModifiedLine line = new ModifiedLine();
                    XMLline current = (XMLline)(XMLlist.get(i));
                    line.setStartX(current.getStartX());
                    line.setStartY(current.getStartY());
                    line.setEndX(current.getEndX());
                    line.setEndY(current.getEndY());
                    line.setStroke(Color.web(current.getFillString()));
                    line.setStrokeWidth(4);
                    line.makeDeletable(line,DeletableNode);
                    ReturnList.add(line);  
               }/*elseif*/
               
               else if(XMLlist.get(i) instanceof XMLrectangle){
                    ModifiedRectangle rect = new ModifiedRectangle();
                    XMLrectangle current = (XMLrectangle)(XMLlist.get(i));
                    rect.setX(current.getX());
                    rect.setY(current.getY());
                    rect.setWidth(current.getWidth());
                    rect.setHeight(current.getHeight());
                    rect.setFill(Color.web(current.getFillString()));
                    rect.makeDeletable(rect,DeletableNode);                    
                    ReturnList.add(rect);  
               }/*elseif*/
               
               else if(XMLlist.get(i) instanceof XMLtriangle){
                    ModifiedTriangle tri = new ModifiedTriangle();
                    XMLtriangle current = (XMLtriangle)(XMLlist.get(i));
                    tri.getPoints().addAll(current.getTriangleCoordinates());
                    tri.setFill(Color.web(current.getFillString()));
                    tri.makeDeletable(tri,DeletableNode);
                    ReturnList.add(tri);  
               }/*elseif*/
               
               

         
                   
                 }/*for*/
                 
                 return ReturnList;
          }/*method*/
          
          

}/*Class*/
