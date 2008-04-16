package org.softmed.jops.cloner;

import org.softmed.jops.ParticleSystem;

public interface Cloner {
	public ParticleSystem getStandaloneCopy(ParticleSystem ps);

}
