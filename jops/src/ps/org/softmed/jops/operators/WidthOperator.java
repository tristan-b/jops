/*
 * AlphaOperator.java
 *
 * Created on 28 de Fevereiro de 2006, 13:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.operators;

import org.softmed.jops.Particle;
import org.softmed.jops.ParticleBehaviour;

/**
 *
 * @author eu
 */
public class WidthOperator  extends ParticleOperator{
    
    public WidthOperator(ParticleBehaviour b)
    {
        super(b);
        //width = behaviour.getWidth();
    }
    
    @Override
    public void update(Particle part) {
    	//if() //only if bias applies...
        part.width = behaviour.getWidth().getValueAt(part.age);
    }
    
    @Override
	public void selectValueList(ParticleBehaviour pb) {
		valueList = pb.getWidth();
	}
    
}
    
