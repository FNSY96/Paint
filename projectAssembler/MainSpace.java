package projectAssembler;

import java.io.File;
import java.text.ParseException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.xml.bind.JAXBException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import modifiedShapes.ModifiedEllipse;
import modifiedShapes.ModifiedTriangle;
import saveLoad.AiderList;
import saveLoad.SaveLoad;
import shapeEditors.AfterMove;
import shapeEditors.CircleEnable;
import shapeEditors.DeleteNode;
import shapeEditors.LineEnable;
import shapeEditors.RectangleEnable;
import shapeEditors.TriangleEnable;
import shapeEditors.UndoHandler;

/**
 * The main assembler class.
 * 
 * @author FNSY
 *
 */
public class MainSpace extends Application {

	private Pane drawSpace = new Pane();
	private ColorPicker pickColor = new ColorPicker();
	private Color fillColor = Color.color(1, 1, 1);
	private Stage stage;
	private boolean ellVis = false;
	private boolean squareVis = false;
	private String jarPathString;
	private Stack<ObservableList> undo = new<ObservableList> Stack();
	private Stack<ObservableList> redo = new<ObservableList> Stack();
	private DeleteNode DeletableNode = new DeleteNode();
	private TriangleEnable TriEn = new TriangleEnable();/* default is false */
	private CircleEnable CircEn = new CircleEnable();
	private LineEnable LineEn = new LineEnable();
	private RectangleEnable RecEn = new RectangleEnable();
	private Boolean UndoBtnIsPressed = FALSE;
	private Boolean NodeIsLive = FALSE;
	private AfterMove Moved = new AfterMove();/* Default is False */

	public static void main(String[] args) {
		Application.launch(args);

	}

	// overrided start method, equivalent to main in console applications.
	@Override
	public void start(Stage primaryStage) throws Exception {
		prepareChoose();
		masterPane();
		setPickColorOn();
		stage = primaryStage;
		Scene scn = new Scene(this.masterPane(), 1000, 700);
		primaryStage.setScene(scn);
		primaryStage.show();

	}

	/**
	 * this method return the master pane that collects the other 3 panes.
	 * 
	 * @return masterPane.
	 */
	private BorderPane masterPane() {
		this.drawSpace.setStyle("-fx-background-color:grey");
		BorderPane masterPane = new BorderPane();
		masterPane.setCenter(this.drawSpace);
		masterPane.setLeft(prepareChoose());
		masterPane.setTop(upperToolBar());
		return masterPane;

	}

	/**
	 * This method returns the horizontal box pane. This pane contains -till
	 * now- the color picker.
	 * 
	 * @return
	 */
	private HBox upperToolBar() {
		HBox tools = new HBox();
		tools.setPrefHeight(50);
		tools.setStyle("-fx-color:black; -fx-background-color:black; -fx-text-fill:white");
		tools.getChildren().add(pickColor);
		tools.getChildren().add(move());
		tools.getChildren().add(dynLoader());
		tools.getChildren().add(saveJsonButton());
		// tools.getChildren().add(loadJsonButton());
		tools.getChildren().add(addImage());
		tools.getChildren().add(UndoButton());
		tools.getChildren().add(RedoButton());
		tools.getChildren().add(DeleteButton());
		tools.setAlignment(Pos.CENTER);

		return tools;

	}

	/**
	 * This returns the vertical box pane. That contains the 3 shapes buttons
	 * 
	 * @return
	 */
	private VBox prepareChoose() {
		VBox choose = new VBox();
		choose.setPadding(new Insets(5));
		choose.getChildren().add(circleButton());
		choose.getChildren().add(rectangleButton());
		choose.getChildren().add(lineButton());
		choose.getChildren().add(triangleButton());
		choose.getChildren().add(ellipseButton());
		choose.getChildren().add(squareButton());
		choose.setAlignment(Pos.TOP_CENTER);

		choose.setPrefWidth(25);
		choose.setStyle("-fx-background-color:black");
		return choose;

	}

	private Button addImage() {
		Button imageAdder = new Button("Add images!");
		imageAdder.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			File path = fileChooser.showOpenDialog(stage);

			if (path.exists()) {
				String imagePath = "file:" + path.toString();
				Image image = new Image(imagePath, 700, 600, true, true);

				ImageView imageReady = new ImageView(image);
				imageReady.setPreserveRatio(true);
				drawSpace.getChildren().add(imageReady);

			} else {
				Alert noImage = new Alert(AlertType.WARNING);
				noImage.setTitle("Broken path");
				noImage.show();
			}
		});

		return imageAdder;

	}

	private Button dynLoader() {
		Button loader = new Button("Add shapes");
		Alert done = new Alert(AlertType.INFORMATION);
		FileChooser fileChooser = new FileChooser();
		loader.setOnAction(e -> {
			fileChooser.setTitle("Choose shape Plug-in");
			try {
				File file = fileChooser.showOpenDialog(stage);
				if (file.toString().contains("CreateEllipse.jar")) {
					jarPathString = "file:" + file.toString();
					this.drawSpace.setCursor(Cursor.CROSSHAIR);
					this.ellVis = true;
					try {
						start(stage);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					done.setTitle("Ellipse loaded succssesfully");
					done.show();
				} else if (file.toString().contains("CreateSquare.jar")) {
					jarPathString = "file:" + file.toString();
					this.drawSpace.setCursor(Cursor.CROSSHAIR);
					this.squareVis = true;
					try {
						start(stage);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					done.setTitle("Square loaded succssesfully");
					done.show();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("NO SUCH CLASS");
					alert.show();
				}
			} catch (NullPointerException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("PLEASE CHOOSE A .jar FILE");
				alert.show();
			}

		});
		return loader;
	}

	private Button move() {

		Button moveBut = new Button();
		// moveBut.setStyle("-fx-color:black");
		Image image = new Image("projectAssembler/Icons/move.png", 35, 30, true, true);
		ImageView moveImg = new ImageView(image);
		moveBut.setGraphic(moveImg);
		moveBut.setOnMouseMoved(e -> {
			moveBut.setCursor(Cursor.HAND);
		});
		moveBut.setOnMouseClicked(e -> {
			if (NodeIsLive.equals(FALSE)) {
				NodeIsLive = LiveOn.makeNodeLive(drawSpace, undo, DeletableNode, Moved);
			}
		});
		return moveBut;
	}/* Move */

	private ImageView circleButton() {

		Image image = new Image("projectAssembler/Icons/circle.png", 45, 35, true, true);
		ImageView circleImg = new ImageView(image);
		circleImg.setOnMouseMoved(e -> {
			circleImg.setCursor(Cursor.HAND);
		});
		circleImg.setOnMouseClicked(e -> {
			TriEn.setIsTriangle(FALSE);
			CircEn.setIsCircle(TRUE);
			NodeIsLive = FALSE;
			this.drawSpace.setCursor(Cursor.CROSSHAIR);
			CreateCircle circle = new CreateCircle();
			CreateCircle.setFillColor(fillColor);
			// circle.setFillString(fillColor.toString());
			circle.create(this.drawSpace, this.undo, redo, DeletableNode, CircEn);
			/*
			 * if(!undo.isEmpty()){ ObservableList<Node> pop = undo.pop(); if
			 * (!UndoHandler.containsSmallObjects(pop)){ undo.push(pop); } }
			 */
		});
		return circleImg;
	}/* CircleButton */

	private ImageView triangleButton() {

		Image image = new Image("projectAssembler/Icons/triangle.png", 45, 35, true, true);
		ImageView triangleImg = new ImageView(image);
		triangleImg.setOnMouseMoved(e -> {
			triangleImg.setCursor(Cursor.HAND);
		});
		triangleImg.setOnMouseClicked(e -> {
			TriEn.setIsTriangle(TRUE);
			CircEn.setIsCircle(FALSE);
			RecEn.setIsRectangle(FALSE);
			LineEn.setIsLine(FALSE);
			NodeIsLive = FALSE;
			this.drawSpace.setCursor(Cursor.CROSSHAIR);
			CreateTriangle.create(this.drawSpace, this.undo, DeletableNode, TriEn, Moved);

		});
		return triangleImg;
	}/* triangleButton */

	private ImageView ellipseButton() {

		Image image = new Image("projectAssembler/Icons/ellipse.png", 45, 35, true, true);
		ImageView ellipseImg = new ImageView(image);
		ellipseImg.setOnMouseMoved(e -> {
			ellipseImg.setCursor(Cursor.HAND);
		});
		ellipseImg.setOnMouseClicked(e -> {
			TriEn.setIsTriangle(FALSE);
			this.drawSpace.setCursor(Cursor.CROSSHAIR);
			try {
				DynamicLoading.loadEllipse(this.drawSpace, jarPathString);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		ellipseImg.setVisible(this.ellVis);
		return ellipseImg;
	}/* EllipseButton */

	private ImageView squareButton() {

		Image image = new Image("projectAssembler/Icons/square.png", 45, 35, true, true);
		ImageView squareImg = new ImageView(image);
		squareImg.setOnMouseMoved(e -> {
			squareImg.setCursor(Cursor.HAND);
		});
		squareImg.setOnMouseClicked(e -> {
			TriEn.setIsTriangle(FALSE);
			this.drawSpace.setCursor(Cursor.CROSSHAIR);
			try {
				DynamicLoading.loadSquare(this.drawSpace, jarPathString);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
		squareImg.setVisible(this.squareVis);

		return squareImg;
	}/* SquareButton */

	private ImageView rectangleButton() {

		Image image = new Image("projectAssembler/Icons/rectangle.png", 45, 35, true, true);
		ImageView rectangleImg = new ImageView(image);

		rectangleImg.setOnMouseMoved(e -> {
			rectangleImg.setCursor(Cursor.HAND);
		});
		rectangleImg.setOnMouseClicked(e -> {
			TriEn.setIsTriangle(FALSE);
			RecEn.setIsRectangle(TRUE);
			NodeIsLive = FALSE;
			this.drawSpace.setCursor(Cursor.CROSSHAIR);
			CreateRectangle.create(this.drawSpace, this.undo, DeletableNode, RecEn, Moved);
		});
		return rectangleImg;
	}/* rectangleButton */

	private ImageView lineButton() {

		Image image = new Image("projectAssembler/Icons/line.png", 45, 35, true, true);
		ImageView lineImg = new ImageView(image);
		lineImg.setOnMouseMoved(e -> {
			lineImg.setCursor(Cursor.HAND);
		});
		lineImg.setOnMouseClicked(e -> {
			TriEn.setIsTriangle(FALSE);
			LineEn.setIsLine(TRUE);
			NodeIsLive = FALSE;
			this.drawSpace.setCursor(Cursor.CROSSHAIR);
			CreateLine.create(this.drawSpace, this.undo, DeletableNode, LineEn, Moved);
		});
		return lineImg;
	}/* LineButtton */

	private Button DeleteButton() {

		Button deleteBut = new Button();
		Image image = new Image("projectAssembler/Icons/delete.png", 35, 30, true, true);
		ImageView deleteImg = new ImageView(image);
		deleteBut.setGraphic(deleteImg);
		deleteBut.setOnMouseClicked((MouseEvent e) -> {
			NodeIsLive = FALSE;
			TriEn.setIsTriangle(FALSE);
			CircEn.setIsCircle(FALSE);
			RecEn.setIsRectangle(FALSE);
			LineEn.setIsLine(FALSE);
			this.drawSpace.setCursor(Cursor.CROSSHAIR);
			// Push a copy of the current Drawspace to redo stack before
			if (DeletableNode.getDeletableNode() != null) {
				Node DeleteContainer = DeletableNode.getDeletableNode();
				for (int i = 0; i < drawSpace.getChildren().size(); i++) {

					if (DeleteContainer.equals(drawSpace.getChildren().get(i))) {
						undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(), DeletableNode));

						Node temp = drawSpace.getChildren().get(i);
						drawSpace.getChildren().remove(temp);
						break;
					}
				} /* for */

			} /* if */

		});
		return deleteBut;
	}

	private Button UndoButton() {

		Button undoBut = new Button();
		Image image = new Image("projectAssembler/Icons/undo.png", 35, 30, true, true);
		ImageView undoImg = new ImageView(image);
		undoBut.setGraphic(undoImg);
		undoBut.setOnMouseClicked((MouseEvent e) -> {
			this.drawSpace.setCursor(Cursor.CROSSHAIR);
			// Push a copy of the current Drawspace to redo stack before
			NodeIsLive = FALSE;

			if (!undo.isEmpty()) {
				UndoBtnIsPressed = TRUE;
				redo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(), DeletableNode));
				ObservableList<Node> test = undo.pop();
				this.drawSpace.getChildren().clear();
				this.drawSpace.getChildren().setAll(test);
			}
		});
		return undoBut;
	}

	private Button RedoButton() {

		Button redoBut = new Button();
		Image image = new Image("projectAssembler/Icons/redo.png", 35, 30, true, true);
		ImageView redoImg = new ImageView(image);
		redoBut.setGraphic(redoImg);
		redoBut.setOnMouseClicked((MouseEvent e) -> {
			this.drawSpace.setCursor(Cursor.CROSSHAIR);
			// Push a copy of the current Drawspace to redo stack before
			NodeIsLive = FALSE;
			if (!redo.isEmpty()) {
				undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(), DeletableNode));
				ObservableList<Node> test = redo.pop();
				this.drawSpace.getChildren().clear();
				this.drawSpace.getChildren().setAll(test);

			}
		});
		return redoBut;
	}

	// private Button loadJsonButton() {
	// Button loadBut = new Button();
	// loadBut.setStyle("-fx-color:black");
	// Image image = new
	// Image("projectAssembler/Icons/load.png",35,30,true,true);
	// ImageView loadImg = new ImageView(image);
	// loadBut.setGraphic(loadImg);
	//
	//
	// loadBut.setOnAction(e-> {
	//
	// NodeIsLive = FALSE;
	// FileChooser fileChooser = new FileChooser();
	//
	// // Set extension filter
	// fileChooser.getExtensionFilters().addAll(
	// new ExtensionFilter("JSON Files(*.json)", "*.json"),
	// new ExtensionFilter("XML Files(*.xml)", "*.xml") );
	//
	//
	// // Show save file dialog
	// File file = fileChooser.showOpenDialog(null);
	//
	// if (file != null) {
	// if(((String)getFileExtension(file)).equals("json"))
	// {
	//
	// try {
	// drawSpace.getChildren().addAll(SaveLoad.loadJSON(file,DeletableNode));
	// } catch (ParseException ex) {
	// Logger.getLogger(MainSpace.class.getName()).log(Level.SEVERE, null, ex);
	// }
	//
	// }/*if*/
	// else if(((String)getFileExtension(file)).equals("xml"))
	// {
	//
	// try {
	// drawSpace.getChildren().addAll(SaveLoad.loadXML(file,DeletableNode));
	// } catch (JAXBException ex) {
	// Logger.getLogger(MainSpace.class.getName()).log(Level.SEVERE, null, ex);
	// }
	//
	// }/*elseIf*/
	//
	// }/*if*/
	//
	// });
	//
	// return loadBut;
	// }/*LoadBTN*/

	private Button saveJsonButton() {
		Button saveBut = new Button();
		saveBut.setStyle("-fx-color:black");
		Image image = new Image("projectAssembler/Icons/save.png", 35, 30, true, true);
		ImageView saveImg = new ImageView(image);
		saveBut.setGraphic(saveImg);

		saveBut.setOnAction(e -> {

			NodeIsLive = FALSE;
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files(*.json)", "*.json"),
					new ExtensionFilter("XML Files(*.xml)", "*.xml"));

			// Show save file dialog
			File file = fileChooser.showSaveDialog(null);

			if (file != null) {
				if (((String) getFileExtension(file)).equals("json")) {
					SaveLoad.saveJSON(file, drawSpace.getChildren());
				} else if (((String) getFileExtension(file)).equals("xml")) {
					try {
						SaveLoad.saveXML(file, drawSpace.getChildren());
					} catch (JAXBException ex) {
						Logger.getLogger(MainSpace.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			} /* if */

		});
		return saveBut;
	}/* SaveBTN */

	/**
	 * This method sets the color picker on action.
	 */
	private void setPickColorOn() {

		pickColor.setOnAction(p -> {
			fillColor = pickColor.getValue();
			CreateRectangle.setFillColor(fillColor);
			CreateCircle.setFillColor(fillColor);
			CreateTriangle.setFillColor(fillColor);
			CreateLine.setFillColor(fillColor);
			try {
				DynamicLoading.changeColor(fillColor);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}/* setPickColorOn */

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}

}/* MainSpace */
