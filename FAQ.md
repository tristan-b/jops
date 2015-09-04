#Frequently Asked Questions

**My Particle System keeps running forever and ever and doesn't stop. Whats up ?**

The Particle Manager by default is in GameMode - which removes PSs once they are dead - and the PS must be limited in order for it run only once.
I have setup all of the PSs bundled with the Editor as not being time limited. This is because most effects are persistent, like a fountain or a jet or a flame, few are explosions.

To make sure it is time limited, there are two solutions:

1- load the ps on the editor.
Click on the root node that says "Particle System" on the top left tree view.
Under the tree view there should appear a few choices such as : Alive, Renderable, Time Limited,  Repeat after Limit and Life Limit.

Please activate Time Limited.
Save and use it.

2 - You can load the ParticleSystem like this:

ParticleLibrary pl = particleManager.getParticleLibrary();
//load the PS
String name = ps.setOriginalFromFile(filepath);
//get the PS
ParticleSystem ps = pl.getStandaloneCopy(name);
//change it to make sure it is time limited
ps.setLimited(true);
//place the changed PS back in the library
ps.setOriginal(name,ps);


Now you can instance it normally by using the particle Manager:
ps = pm.getCopyAttached(name);

The returned ps will be a copy of the changed PS, now time limited, and should run only once.


**My "Explosion" Particle System is running twice ? Why ?**

About the PS running twice : it's not

It's actually running only once, the problem is that the PS's time to live can be several times that of the particles being generated. For example if the Ps's time to live is 2 seconds, and if the particles life is one second, this can happen.

If you check the Generator's "explosion" particle rate, it should be quite high, about 20k particles per second. That's to force it to generate all of the available particles at once - it should be a few hundred of a few thousand, at most -  simulating an explosion. But if you check the particle life time, it is only one second.

As the Generator is set to regenerate dead particles, and since all of them were spawned at the same time, all of them will die at roughly the same time too, and so they will be all regenerated, in what seems to be the PS running twice.

So what is happening is that while the PS is alive, there is enough time to respawn all of the particles twice, and so it seems like it is running twice.

The PS shouldn't have been set to regenerate dead particles. that is only to be used with persistent Pss like fountains, etc that are always regenerating dead particles.

What you need to do is this:
Open the Ps in the Editor.
Select the generator in the tree view at top left.
Below the Tree view you should now see a few checkboxes.
Deactivate the one that says: Regenerate.
You might also what to adjust the Ps's life time. Since all particles will die shortly after 1 second, there is no point in having the PS alive for one more second without anything to show.
Now click on the ParticleSystem node on the tree view.
There is a Life Limit control set at the value of 200 - this is in hundreds of a second, therefore, its 2 seconds.
Change it to slightly over one seconde - a little over 100.
Save and use the PS.

Now it should run only once.