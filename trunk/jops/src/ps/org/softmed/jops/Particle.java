/*
 * Particle.java
 *
 * Created on 26 de Fevereiro de 2006, 0:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.softmed.jops;

import org.openmali.vecmath2.Colorf;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Vector3f;




/**
 *
 * @author eu
 */
public class Particle {
    
	public float speed = 1.0f;
    public Vector3f direction= new Vector3f();
    public Point3f position = new Point3f();
    public Colorf color;
    public float alpha;
    public float life=1.0f;
    public float age=0.0f;
    public float mass;
    public float spin;
    
    public float spinV;
    public float spinH;
    
    public float width=1.0f;
    public float height=1.0f;
    
    public float texWidth=1.0f;
    public float texHeight=1.0f;
    
    public float angle;
    public float size;
    public float angleV=0f;
    public float angleH=0f;
   // public float height;
   // public float width;
    public float elasticity;
    public Point3f oldPosition;
    
    /** Creates a new instance of Particle */
    public Particle() {
    }
    
    public void calculate(float dt)
    {
        position.addX( direction.getX() * speed * dt );
        position.addY( direction.getY() * speed * dt );
        position.addZ( direction.getZ() * speed * dt );
        
        angle+=spin*dt;
        
        angleV+=spinV*dt;
        angleH+=spinH*dt;
        life -= dt;
        age+=dt;
    }
}
