package projectAssembler;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
public class LiveOff {
	
	private LiveOff(){
		
	}
	public static void makeNodeOff(Pane drawSpace){
		for(int i = 0; i < drawSpace.getChildren().size(); i++){
			drawSpace.getChildren().get(i).setCursor(Cursor.CROSSHAIR);

				drawSpace.getChildren().get(i).setOnMousePressed(e->{
					
				});
				drawSpace.getChildren().get(i).setOnMouseDragged(e->{
					
				});
			

		}
				
	}

}
