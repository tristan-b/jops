/*
 * ParticleOperator.java
 *
 * Created on 28 de Fevereiro de 2006, 13:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops.operators;

import org.softmed.jops.Particle;
import org.softmed.jops.ParticleBehaviour;
import org.softmed.jops.valuelists.GeneralRandomValueList;


/**
 *
 * @author eu
 */
public abstract class ParticleOperator {
	protected GeneralRandomValueList valueList;
	protected ParticleBehaviour behaviour;
       float age ;
    public ParticleOperator(ParticleBehaviour b){
    	behaviour = b;
        selectValueList(b);
    }
    
    
    public void myupdate(Particle part){
    	if(part.age >= valueList.getBias()){
    		age = part.age;
    		part.age-=valueList.getBias();
    		update(part);
    		part.age=age;
    	}
    }
    
    public abstract void update(Particle part) ;
    public abstract void selectValueList(ParticleBehaviour pb) ;
    
}
