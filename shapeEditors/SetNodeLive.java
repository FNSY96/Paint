package shapeEditors;

import static java.lang.Boolean.TRUE;
import java.util.ArrayList;
import java.util.Stack;

import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Line;
import javafx.scene.image.ImageView;

public class SetNodeLive {
	private double relativeCursorPositionX;
	private double relativeCursorPositionY;
	private double layoutDistanceX;
	private double layoutDistanceY;
	private double nodeWidth;
	private double nodeHeight;
	private Cursor state = Cursor.DEFAULT;
	private Node node;
	private double retainAbsForEllipseX;
	private double retainAbsForTriangleX;
	private boolean enter = true;
	private boolean enterForTriangle = true;
	private boolean enterTriangle;
	private ArrayList<Double> points;
	private double deltaX1;
	private double deltaY1;
	private double deltaX2;
	private double deltaY2;
	private double deltaX3;
	private double deltaY3;
	private int indexOfRightMostX = 0;
	private int indexOfLeftMostX = 0;
	private int indexOfCentralX = 0;
        private static Pane drawSpace;
        private static Stack<ObservableList> undo ;
        
        public static void setUndo (Stack<ObservableList> undoSent){
            undo = undoSent;
        }
	private SetNodeLive(Node node) { // This is the class constructor
		this.node = node;
		this.enterTriangle = true;
		this.points = new ArrayList<>();
		
	}
        public static void setPane(Pane pane){
            drawSpace = pane;
        }
	/**
	 * The main method.
	 * 
	 * @param node
	 * @param listener
	 */
	public static void makeResizable(Node node, DeleteNode DeletableNode, AfterMove Moved) {
		SetNodeLive resizer = new SetNodeLive(node);
		node.setOnMousePressed(e -> {
			resizer.mousePressed(e,DeletableNode);
		});
		node.setOnMouseDragged(e -> {
			resizer.mouseDragged(e,DeletableNode);
                        
		});
		node.setOnMouseMoved(e -> {
			node.setStyle("-fx-stroke:blue");
			resizer.mouseOver(e);

		});

		node.setOnMouseExited(e -> {
			node.setStyle(null);
		});
		node.setOnMouseReleased(e -> {

			resizer.mouseReleased(e,DeletableNode,Moved);
                     //   undo.pop();
                        
		});
                
              //  undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));
                
	}

	// first one called
	private void mousePressed(MouseEvent event,DeleteNode DeletableNode) {
		if (isInResizeZone(event)) {
			enter = true;
			enterForTriangle = true;
			absoluteCursorPositionX(event.getX());
			setNewInitialEventCoordinates(event);
			state = currentMouseState(event);
		} else if (isInDragZone(event)) {

			setNewInitialEventCoordinates(event);
			state = Cursor.CLOSED_HAND;
		} else {
			state = Cursor.DEFAULT;
		}
                undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));
                
	}
        

	private boolean isInResizeZone(MouseEvent event) {
		setRelativeCursorPositionX(event.getX());
		setRelativeCursorPositionY(event.getY());

		return isLeftResizeZone(relativeCursorPositionX) || isRightResizeZone(relativeCursorPositionX)
				|| isBottomResizeZone(relativeCursorPositionY) || isTopResizeZone(relativeCursorPositionY)
				|| isEllipseResizeZone() || isTriangleResizeZone(event);
		// || isLineResizeZone();
	}

	
	private boolean isTriangleResizeZone(MouseEvent event) {
		enterForTriangle = true;
		double absoluteCursorPositionX = absoluteCursorPositionX(event.getX());
		double absoluteCursorPositionY = absoluteCursorPositionY(event.getY());
		if (node instanceof Polygon) {

			double getRightMostPoint = Math.max(((Polygon) node).getPoints().get(0),
					((Polygon) node).getPoints().get(2));
			getRightMostPoint = Math.max(getRightMostPoint, ((Polygon) node).getPoints().get(4));

			if (((absoluteCursorPositionX >= getRightMostPoint - 2
					&& absoluteCursorPositionX <= getRightMostPoint + 2))) {
				return true;
			}
		}
		return false;

	}

	private boolean isEllipseResizeZone() {
		if (node instanceof Ellipse)
			if ((relativeCursorPositionX <= (2 * ((Ellipse) node).getRadiusX()) + 2
					&& relativeCursorPositionX >= (2 * ((Ellipse) node).getRadiusX()) - 2)) {

				return true;
			}
		return false;
	}

	private boolean isLeftResizeZone(double getX) {
		return intersect(0, getX);
	}

	private boolean isRightResizeZone(double getX) {
		return intersect(nodeWidth(), getX + 1);// this should be edited
	}

	private boolean isTopResizeZone(double getY) {

		return intersect(0, getY);
	}

	private boolean isBottomResizeZone(double getY) {

		return intersect(nodeHeight(), getY + 1);// this should be edited the
													// bug is relative coo is
													// less than height by 1
	}

	private boolean intersect(double side, double point) {// sees the
															// intesection
															// between the
															// cursor and
															// certain areas

		return side + 1 >= point && side - 1 <= point;
	}

	// first one ended

	private void mouseReleased(MouseEvent event,DeleteNode DeletableNode,AfterMove Moved) {
		state = Cursor.DEFAULT;
		node.setCursor(state);
		enter = true;
           //   undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));
                Moved.addAsMoved(TRUE);  
                System.out.println("Truez");

	}

	private void mouseOver(MouseEvent event) {
		node.setCursor(currentMouseState(event));

	}

	private Cursor currentMouseState(MouseEvent event) {
		Cursor state = Cursor.DEFAULT;
		setRelativeCursorPositionX(event.getX());
		setRelativeCursorPositionY(event.getY());
		boolean left = isLeftResizeZone(relativeCursorPositionX);
		boolean right = isRightResizeZone(relativeCursorPositionX);
		boolean top = isTopResizeZone(relativeCursorPositionY);
		boolean bottom = isBottomResizeZone(relativeCursorPositionY);
		boolean rad = isEllipseResizeZone();
		boolean tri = isTriangleResizeZone(event);
		// boolean line1 = isLineResizeZone();
		if (isInDragZone(event))
			state = Cursor.CLOSED_HAND;
		if (node instanceof Line || node instanceof Rectangle || node instanceof ImageView)
			if (left && top)
				state = Cursor.NW_RESIZE;
			else if (left && bottom)
				state = Cursor.SW_RESIZE;
			else if (right && top)
				state = Cursor.NE_RESIZE;
			else if (right && bottom)
				state = Cursor.SE_RESIZE;

		if (node instanceof Ellipse)
			if (rad) {
				state = Cursor.WAIT;
			}
		if (node instanceof Polygon)
			if (tri) {
				state = Cursor.WAIT;
			}

		//
		// if(node instanceof Line)
		// if (line1) {
		// state = Cursor.WAIT;
		//
		// }

		return state;
	}

	private void mouseDragged(MouseEvent event, DeleteNode DeletableNode) {
		node.setStyle("-fx-stroke:red");
		// enterForTriangle = true;
		double absoluteCursorPositionX = absoluteCursorPositionX(event.getX());
		double absoluteCursorPositionY = absoluteCursorPositionY(event.getY());
		double displacementX = absoluteCursorPositionX - relativeCursorPositionX;// upper
																					// left
																					// corner
		double displacementY = absoluteCursorPositionY - relativeCursorPositionY;
		if (state == Cursor.CLOSED_HAND && state != Cursor.WAIT) {

			if (node instanceof Rectangle) {
				((Rectangle) node).setX(displacementX);
				((Rectangle) node).setY(displacementY);
                                                      //  undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));

			}

			if (node instanceof ImageView) {
				((ImageView) node).setX(displacementX);
				((ImageView) node).setY(displacementY);
                                                     //   undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));

			}

			if (node instanceof Ellipse) {

				moveEllipse(displacementX, displacementY);
                                                       // undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));

			}

			if (node instanceof Line) {

				moveLine(node, displacementX, displacementY);
                                                    //    undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));


			}

			if (node instanceof Polygon) {
				moveTriangle(((Polygon) node).getPoints(), absoluteCursorPositionX, absoluteCursorPositionY);
				enterForTriangle = true;
				setRetainLeftMostX();
				enterForLeft = true;
				setRetainCentralY();
				enterForCentral = true;
                                System.out.print(drawSpace);
                                                    //    undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));

			}

		} else if (state != Cursor.DEFAULT && state != Cursor.WAIT) {
			node.setStyle("-fx-stroke:green");
			// resizing
			double newX = layoutDistanceX;
			double newY = layoutDistanceY;
			double newH = nodeHeight;
			double newW = nodeWidth;

			// Right Resize
			if (state == Cursor.SE_RESIZE) {
				System.out.println(state);
				newW = absoluteCursorPositionX - layoutDistanceX;
			}
			// Left Resize
			if (state == Cursor.NW_RESIZE || state == Cursor.SW_RESIZE) {
				newX = absoluteCursorPositionX;
				newW = nodeWidth + layoutDistanceX - newX;
			}

			// Bottom Resize
			if (state == Cursor.SE_RESIZE || state == Cursor.SW_RESIZE) {
				newH = absoluteCursorPositionY - layoutDistanceY;
			}
			// Top Resize
			if (state == Cursor.NW_RESIZE || state == Cursor.NE_RESIZE) {
				newY = absoluteCursorPositionY;
				newH = nodeHeight + layoutDistanceY - newY;
			}

			// min valid rect Size Check
			if (newW < 1) {
				if (state == Cursor.NW_RESIZE || state == Cursor.SW_RESIZE)
					newX = newX - 1 + newW;
				newW = 1; // added be me, was MIN_W
			}

			if (newH < 1) {
				if (state == Cursor.NW_RESIZE || state == Cursor.NE_RESIZE)
					newY = newY + newH - 1;
				newH = 1;
			}

			if (node instanceof Rectangle) {
				resizeRectangle(newX, newY, newW, newH);
                                                       // undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));

			}
			if (node instanceof ImageView) {
				resizeImage(newX, newY, newW, newH);
                                                     //   undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));


			}

			if (node instanceof Line) {
				resizeLine(newX, newY, newW, newH);
                                               // undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));

			}
		} else if (state == Cursor.WAIT) {

			node.setStyle("-fx-stroke:green");
			if (node instanceof Ellipse) {
				double realRatio = ((Ellipse) node).getRadiusX() / ((Ellipse) node).getRadiusY();
				double newRadX = (nodeWidth / 2) + absoluteCursorPositionX - retainAbsForEllipseX;
				double newRadY = newRadX / realRatio;
				resizeEllipse(newRadX, newRadY);
                                                    //    undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));

			} else if (node instanceof Line) {
				System.out.println("we are here");// be back here if time is
													// available
			} else if (node instanceof Polygon) {
				//
				double getRightMostPoint = Math.max(((Polygon) node).getPoints().get(0),
						((Polygon) node).getPoints().get(2));
				getRightMostPoint = Math.max(getRightMostPoint, ((Polygon) node).getPoints().get(4));

				double getLeftMostPoint = Math.min(((Polygon) node).getPoints().get(0),
						((Polygon) node).getPoints().get(2));
				getLeftMostPoint = Math.min(getLeftMostPoint, ((Polygon) node).getPoints().get(4));
				double getY;
				double deltaX = absoluteCursorPositionX - retainAbsForTriangleX;

				for (int i = 0; i < ((Polygon) node).getPoints().size(); i += 2) {
					if (getRightMostPoint == ((Polygon) node).getPoints().get(i)) {
						indexOfRightMostX = i;
					} else if (getLeftMostPoint == ((Polygon) node).getPoints().get(i)) {
						indexOfLeftMostX = i;
					} else {
						indexOfCentralX = i;
					}
				}
				int indexOfCentralY = indexOfCentralX + 1;

				setRetainLeftMostX();
				enterForLeft = false;
				setRetainCentralY();
				enterForCentral = false;

				resizeTriangle(deltaX, indexOfCentralY, absoluteCursorPositionX);
                                System.out.println(drawSpace);
                                       
                                                   //    undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren(),DeletableNode));


			}

		}
	    
        }

	private void resizeTriangle(double deltaX, int indexOfCentralY, double absoluteCursorPositionX) {

		if (deltaX > -30) {
			if (((Polygon) node).getPoints().get(indexOfCentralY) > ((Polygon) node).getPoints()
					.get(indexOfRightMostX + 1)
					&& ((Polygon) node).getPoints().get(indexOfCentralY) > ((Polygon) node).getPoints()
							.get(indexOfLeftMostX + 1)) {
				((Polygon) node).getPoints().set(indexOfRightMostX, absoluteCursorPositionX);
				((Polygon) node).getPoints().set(indexOfLeftMostX, retainleftMostX - deltaX);
				((Polygon) node).getPoints().set(indexOfCentralX + 1, retainCentralY + deltaX);
			}

			if (((Polygon) node).getPoints().get(indexOfCentralY) < ((Polygon) node).getPoints()
					.get(indexOfRightMostX + 1)
					&& ((Polygon) node).getPoints().get(indexOfCentralY) < ((Polygon) node).getPoints()
							.get(indexOfLeftMostX + 1)) {
				((Polygon) node).getPoints().set(indexOfRightMostX, absoluteCursorPositionX);
				((Polygon) node).getPoints().set(indexOfLeftMostX, retainleftMostX - deltaX);
				((Polygon) node).getPoints().set(indexOfCentralX + 1, retainCentralY - deltaX);
			}
			enterTriangle = true;

		}

	}

	private double retainleftMostX;
	private boolean enterForLeft = true;

	private void setRetainLeftMostX() {
		if (enterForLeft) {
			retainleftMostX = ((Polygon) node).getPoints().get(indexOfLeftMostX);
		}
	}

	private double retainCentralY;
	private boolean enterForCentral = true;

	private void setRetainCentralY() {
		if (enterForCentral) {
			retainCentralY = ((Polygon) node).getPoints().get(indexOfCentralX + 1);
		}
	}

	private void resizeEllipse(double newRadX, double newRadY) {

		((Ellipse) node).setRadiusX(newRadX);

		((Ellipse) node).setRadiusY(newRadY);
	}

	private void moveEllipse(double displacementX, double displacementY) {
		((Ellipse) node).setCenterX(((Ellipse) node).getRadiusX() + displacementX);
		((Ellipse) node).setCenterY(((Ellipse) node).getRadiusY() + displacementY);
	}

	private void moveTriangle(ObservableList<Double> trianglePoints, double absoluteCursorPositionX,
			double absoluteCursorPositionY) {

		if (enterTriangle == true) {
			deltaX1 = absoluteCursorPositionX - trianglePoints.get(0);
			deltaY1 = absoluteCursorPositionY - trianglePoints.get(1);
			deltaX2 = absoluteCursorPositionX - trianglePoints.get(2);
			deltaY2 = absoluteCursorPositionY - trianglePoints.get(3);
			deltaX3 = absoluteCursorPositionX - trianglePoints.get(4);
			deltaY3 = absoluteCursorPositionY - trianglePoints.get(5);
			enterTriangle = false;
		}

		double x1 = absoluteCursorPositionX - deltaX1;
		double y1 = absoluteCursorPositionY - deltaY1;
		double x2 = absoluteCursorPositionX - deltaX2;
		double y2 = absoluteCursorPositionY - deltaY2;
		double x3 = absoluteCursorPositionX - deltaX3;
		double y3 = absoluteCursorPositionY - deltaY3;

		points.add(x1);
		points.add(y1);
		points.add(x2);
		points.add(y2);
		points.add(x3);
		points.add(y3);

		((Polygon) node).getPoints().clear();
		((Polygon) node).getPoints().addAll(points);
		points.clear();

	}

	private void moveLine(Node node, double displacementX, double displacementY) {
		int initial = -1;

		double deltaX = 0;
		double deltaY = 0;
		if (initial == -1) {
			deltaX = ((Line) node).getEndX() - ((Line) node).getStartX();
			deltaY = ((Line) node).getEndY() - ((Line) node).getStartY();
		}
		initial--;
		if (((Line) node).getStartX() < ((Line) node).getEndX()) {

			if (((Line) node).getStartY() < ((Line) node).getEndY()) {
				//
				((Line) node).setStartX(displacementX);
				((Line) node).setStartY(displacementY);
				((Line) node).setEndX(displacementX + deltaX);
				((Line) node).setEndY(displacementY + deltaY);
			} else if (((Line) node).getStartY() > ((Line) node).getEndY()) {
				//
				((Line) node).setStartX(displacementX);
				((Line) node).setStartY(displacementY - deltaY);
				((Line) node).setEndX(displacementX + deltaX);
				((Line) node).setEndY(displacementY);

			}
		} else if (((Line) node).getStartX() > ((Line) node).getEndX()) {
			//
			//
			if (((Line) node).getStartY() < ((Line) node).getEndY()) {
				((Line) node).setEndX(displacementX);
				((Line) node).setEndY(displacementY + deltaY);
				((Line) node).setStartX(displacementX - deltaX);
				((Line) node).setStartY(displacementY);
				//
			} else if (((Line) node).getStartY() > ((Line) node).getEndY()) {
				((Line) node).setEndX(displacementX);
				((Line) node).setEndY(displacementY);
				((Line) node).setStartX(displacementX - deltaX);
				((Line) node).setStartY(displacementY - deltaY);

			}
		}

	}

	private void resizeLine(double newX, double newY, double newW, double newH) {
		if (((Line) node).getEndX() > ((Line) node).getStartX()) {
			double ratio = ((Line) node).getEndX() - ((Line) node).getStartX() / ((Line) node).getEndY()
					- ((Line) node).getStartY();

			// System.out.println(ratio);
			// newW = ratio * newH;
			double endX = newW + ((Line) node).getStartX();
			double endY = newH + ((Line) node).getStartY();
			// endY = endX / ratio;
			((Line) node).setEndX(endX);
			((Line) node).setEndY(endY);

		}

	}

	private void resizeImage(double newX, double newY, double newW, double newH) {

		((ImageView) node).setX(newX);
		((ImageView) node).setY(newY);
		((ImageView) node).setFitWidth(newW);
		((ImageView) node).setFitHeight(newH);
	}

	private void resizeRectangle(double newX, double newY, double newW, double newH) {
		double realRatio = ((Rectangle) node).getWidth() / ((Rectangle) node).getHeight();
		newW = realRatio * newH;
		((Rectangle) node).setX(newX);
		((Rectangle) node).setY(newY);
		((Rectangle) node).setWidth(newW);
		((Rectangle) node).setHeight(newH);
	}

	private void forEllipse(double newX, double newY, double newW, double newH) {
		double realRatio = ((Ellipse) node).getRadiusX() / ((Ellipse) node).getRadiusY();
		newW = realRatio * newH;
		((Ellipse) node).setCenterX(((Ellipse) node).getRadiusX() + newX);
		((Ellipse) node).setCenterY(((Ellipse) node).getRadiusY() + newY);
		((Ellipse) node).setRadiusX(newW / 2);
		((Ellipse) node).setRadiusY(newH / 2);
	}

	private void setNewInitialEventCoordinates(MouseEvent event) {
		layoutDistanceX = layoutDistanceX();
		layoutDistanceY = layoutDistanceY();
		nodeHeight = nodeHeight();
		nodeWidth = nodeWidth();
		setRelativeCursorPositionX(event.getX());
		setRelativeCursorPositionY(event.getY());

	}

	private boolean isInDragZone(MouseEvent event) {// Verified
		double absoluteCursorPositionX = absoluteCursorPositionX(event.getX());
		double absoluteCursorPositionY = absoluteCursorPositionY(event.getY());
		double layoutDistanceX = layoutDistanceX();
		double layoutDistanceY = layoutDistanceY();
		double fullSpanX = layoutDistanceX() + nodeWidth(); // المسافة من الحرف
															// لحد الجانب الايسر
															// + عرض المستطيل
		double fullSpanY = layoutDistanceY() + nodeHeight();

		return (absoluteCursorPositionX > layoutDistanceX && absoluteCursorPositionX < fullSpanX)
				&& (absoluteCursorPositionY > layoutDistanceY && absoluteCursorPositionY < fullSpanY);
	}

	private double absoluteCursorPositionX(double localX) { // Verified
		// adds the x distance between the node and the boundry + coordinates of
		// the cursor relative to the shape
		if (enter == true)
			retainAbsForEllipseX = localX;
		enter = false;
		if (enterForTriangle == true)
			retainAbsForTriangleX = localX;
		enterForTriangle = false;
		return localX;
	}

	private double absoluteCursorPositionY(double localY) { // Verified
		return localY;
	}

	private void setRelativeCursorPositionX(double localX) {

		this.relativeCursorPositionX = localX - layoutDistanceX();
	}

	private void setRelativeCursorPositionY(double localY) {
		this.relativeCursorPositionY = localY - layoutDistanceY();
	}

	// Verified
	private double layoutDistanceX() {// المسافة بين النود وال boundary

		return node.getBoundsInParent().getMinX();
	}

	private double layoutDistanceY() {
		return node.getBoundsInParent().getMinY();
	}

	private double nodeWidth() {
		return node.getBoundsInParent().getWidth();

	}

	private double nodeHeight() {
		return node.getBoundsInParent().getHeight();
	}

}