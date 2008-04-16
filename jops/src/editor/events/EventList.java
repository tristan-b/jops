package events;

import java.util.ArrayList;
import java.util.List;
/*
 * EventList.java
 *
 * Created on 22 de Fevereiro de 2006, 23:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author eu
 */
public class EventList {
    
    List<Event> events = new ArrayList<Event>();
    
    
    public void addEvent(Event ev)
    {
        events.add(ev);
    }
    
    
    public void processEvents()
    {
        for (int i = 0; i < events.size(); i++) {
      //  for (Iterator it = events.iterator(); it.hasNext();) {
          events.get(i).execute();
            //elem.execute();
        }
        events.clear();
    }
    
}
