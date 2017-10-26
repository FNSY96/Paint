/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapeEditors;

import static java.lang.Boolean.FALSE;
import java.util.LinkedList;


/**
 *
 * @author MMERS
 */
public class AfterMove {
   LinkedList <Boolean> Moved = new LinkedList<>();
   Boolean AllIsFalse = FALSE;
   

    public void addAsMoved(Boolean Moved){
         this.Moved.add(Moved);
    }/*method*/
    public void UndoIsMade(){
     this.Moved.removeLast();
    }
    public Boolean Last(){
      return this.Moved.getLast();
    }
    public int size(){
     return this.Moved.size();
    }
    
}/*Class*/
