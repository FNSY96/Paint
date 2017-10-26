package dynamicLoadingClasses;


import modifiedShapes.ModifiedEllipse;
import projectAssembler.LiveOff;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CreateEllipse {
	
	private ModifiedEllipse ellipse;
	private double ellipseCenterX;
	private double ellipseCenterY;
	private double ellipseRadiusX;
	private double ellipseRadiusY;
	private static Color fillColor = Color.WHITE;

	

	private CreateEllipse(){
		this.ellipseCenterX = 0;
		this.ellipseCenterY = 0;
		this.ellipseRadiusX = 0;
		this.ellipseRadiusY = 0;
		
	}
	
	public static void setFillColor(Color color){
		fillColor = color;
	}
	
	public static void create(Pane drawSpace){
		CreateEllipse createEllipse = new CreateEllipse();
		drawSpace.setOnMousePressed(e->{
			createEllipse.ellipse = new ModifiedEllipse();
			drawSpace.getChildren().add(createEllipse.ellipse);
			createEllipse.ellipseCenterX = e.getX();
			createEllipse.ellipseCenterY = e.getY();
			createEllipse.ellipse.setCenterX(createEllipse.ellipseCenterX);
			createEllipse.ellipse.setCenterY(createEllipse.ellipseCenterY);

		});
		
		drawSpace.setOnMouseDragged(e->{
			double x = e.getX() - createEllipse.ellipseCenterX;
			double y = createEllipse.ellipseCenterY - e.getY();
			createEllipse.ellipseRadiusX = Math.abs(x);
			createEllipse.ellipseRadiusY = Math.abs(y);
			createEllipse.ellipse.setRadiusX(createEllipse.ellipseRadiusX);
			createEllipse.ellipse.setRadiusY(createEllipse.ellipseRadiusY);
			createEllipse.ellipse.setFill(fillColor);
			LiveOff.makeNodeOff(drawSpace);
			e.consume();
		});

	}
	
	
}
