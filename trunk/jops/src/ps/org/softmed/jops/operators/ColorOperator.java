/*
 * ColorOperator.java
 *
 * Created on 28 de Fevereiro de 2006, 13:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.operators;



import org.openmali.vecmath2.Colorf;
import org.softmed.jops.Particle;
import org.softmed.jops.ParticleBehaviour;

/**
 *
 * @author eu
 */
public class ColorOperator extends ParticleOperator{
    
     private Colorf c;

    public ColorOperator(ParticleBehaviour b)
    {
        super(b);
    }
    
    @Override
    public void update(Particle part) {
    	c = behaviour.getColor().getValueAt(part.age);
        //part.color.setColor();
        part.color = new Colorf(c);//.set(c.getRed(),c.getGreen(),c.getBlue());
    }
    
	@Override
	public void selectValueList(ParticleBehaviour pb) {
		valueList = pb.getColor();
		
	}
    
}
