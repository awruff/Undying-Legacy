package com.github.awruff.totems.utils;

import net.minecraft.client.entity.particle.AnimatedParticle;
import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.entity.particle.ParticleFactory;
import net.minecraft.world.World;

public class ParticleTotem extends AnimatedParticle {
    public ParticleTotem(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, 176, 8, -0.05F);

        this.velocityX = (int) velocityX;
        this.velocityY = (int) velocityY;
        this.velocityZ = (float) velocityZ;

        this.size *= 0.75F;
        this.lifetime = 60 + random.nextInt(12);

        if (random.nextInt(4) == 0) {
            setColor(0.6F + random.nextFloat() * 0.2F, 0.6F + random.nextFloat() * 0.3F, random.nextFloat() * 0.2F);
        } else {
            setColor(0.1F + random.nextFloat() * 0.2F, 0.4F + random.nextFloat() * 0.3F, random.nextFloat() * 0.2F);
        }
    }

    public static class Factory implements ParticleFactory
    {
        @Override
        public Particle create(int type, World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
            return new ParticleTotem(world, x, y, z, velocityX, velocityY, velocityZ);
        }
    }
}
