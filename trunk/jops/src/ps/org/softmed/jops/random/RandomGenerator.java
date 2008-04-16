// FrontEnd Plus GUI for JAD
// DeCompiled : RandomGenerator.class

package org.softmed.jops.random;

import java.util.Random;

public class RandomGenerator
{

    static int amount;
    static float floats[];
    static Random gen;
    int count;

    public RandomGenerator()
    {
        count = 0;
        count = gen.nextInt(amount);
    }

    public float nextFloat()
    {
        count++;
        if(count == amount)
            count = gen.nextInt(amount);
        return floats[count];
    }

    public int getInt(int x)
    {
        float y = nextFloat();
        y *= x;
        return (int)y;
    }

    public float getFloat(float x)
    {
        return nextFloat() * x;
    }

    public boolean nextBoolean()
    {
        return (double)(nextFloat() * 1.0F) > 0.5D;
    }

    static 
    {
        amount = 0xf4240;
        floats = new float[amount];
        gen = new Random();
        for(int i = 0; i < floats.length; i++)
            floats[i] = gen.nextFloat();

    }
}
