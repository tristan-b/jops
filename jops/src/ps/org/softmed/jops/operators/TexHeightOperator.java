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
public class TexHeightOperator  extends ParticleOperator{
    
    public TexHeightOperator(ParticleBehaviour b)
    {
        super(b);
    }
    
    @Override
    public void update(Particle part) {
        part.texHeight = behaviour.getTexHeight().getValueAt(part.age);
    }
    
    @Override
	public void selectValueList(ParticleBehaviour pb) {
		valueList = pb.getTexHeight();
		
	}
}
    
