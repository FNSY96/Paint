package projectAssembler;


import javafx.scene.paint.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Stack;
import javafx.collections.ObservableList;

import javafx.scene.layout.Pane;
import shapeEditors.UndoHandler;

public class DynamicLoading {
	private static Method ellipseColorMethod;
	private static Method squareColorMethod;
        private static Stack<ObservableList> undo;
	public static void loadSquare(Pane drawSpace, String jarPathString)
			throws MalformedURLException, NoSuchMethodException, SecurityException, ClassNotFoundException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class[] cArg = new Class[2];
		cArg[0] = javafx.scene.layout.Pane.class;
		cArg[1] = javafx.scene.paint.Color.class;
		URL url = new URL(jarPathString);
		URL[] urls = new URL[] { url };
		ClassLoader cl = new URLClassLoader(urls);
		Method triangleCreationMethod = cl.loadClass("dynamicLoadingClasses.CreateSquare").getMethod("create", cArg[0]);
		squareColorMethod = cl.loadClass("dynamicLoadingClasses.CreateSquare").getMethod("setFillColor",
				cArg[1]);
		triangleCreationMethod.invoke(drawSpace, drawSpace);
             // undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren()));


	}
		
	public static void loadEllipse(Pane drawSpace, String jarPathString)
			throws MalformedURLException, NoSuchMethodException, SecurityException, ClassNotFoundException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class[] cArg = new Class[2];
		cArg[0] = javafx.scene.layout.Pane.class;
		cArg[1] = javafx.scene.paint.Color.class;
		URL url = new URL(jarPathString);
		URL[] urls = new URL[] { url };
		ClassLoader cl = new URLClassLoader(urls);
		Method ellipseCreationMethod = cl.loadClass("dynamicLoadingClasses.CreateEllipse").getMethod("create", cArg[0]);
		ellipseColorMethod = cl.loadClass("dynamicLoadingClasses.CreateEllipse").getMethod("setFillColor",
				cArg[1]);
		ellipseCreationMethod.invoke(drawSpace, drawSpace);
             // undo.push(UndoHandler.saveSnapshot(drawSpace.getChildren()));


	}

	public static void changeColor(Color fillColor) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
			if(ellipseColorMethod != null)
			ellipseColorMethod.invoke(fillColor, fillColor);
			if(squareColorMethod != null)
			squareColorMethod.invoke(fillColor, fillColor);
	
	}

}
