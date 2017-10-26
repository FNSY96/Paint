package projectAssembler;
import static java.lang.Boolean.TRUE;
import java.util.Stack;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import shapeEditors.AfterMove;
import shapeEditors.DeleteNode;
import shapeEditors.SetNodeLive;
import shapeEditors.UndoHandler;
public class LiveOn {
	
                   // private static Stack<ObservableList> undo = new <ObservableList>Stack();               
 
	private LiveOn(){
		
	}
	public static Boolean makeNodeLive(Pane drawSpace,  Stack<ObservableList> undo, DeleteNode DeletableNode,AfterMove Moved){
		for(int i = 0; i < drawSpace.getChildren().size(); i++){
			SetNodeLive.makeResizable(drawSpace.getChildren().get(i),DeletableNode,Moved);                        
                        System.out.println("Niceeeee");
		}
                SetNodeLive.setPane(drawSpace);
                SetNodeLive.setUndo(undo);

		drawSpace.setOnMouseClicked(e->{
			
		});
		drawSpace.setOnMousePressed(e -> {
		});
		
		drawSpace.setOnMouseDragged(e -> {
		});
              return TRUE;
		
	}

}
