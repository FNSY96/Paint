/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saveLoad;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MMERS
 */
@XmlRootElement
          public class AiderList {

                 private List values = new ArrayList();

                 @XmlElement
                  public List getValues() {
                  return values;
                  }/*Method*/
                 
                  public void add(Object value){
                   values.add(value);
                  }
                  public int size(){
                    return values.size();
                  }
                  public Object get(int i){
                   return values.get(i);
                  }

           }/*lists Class*/  
