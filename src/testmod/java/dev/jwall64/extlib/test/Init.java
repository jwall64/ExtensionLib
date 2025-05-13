package dev.jwall64.extlib.test;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import dev.jwall64.extlib.api.ExtensionRegistry;

public class Init implements ModInitializer {
    @Override
    public void onInitialize() {
        ExtensionRegistry.registerExtension(ResourceLocation.tryBuild("extlibtest", "test"), ExtensionRegistry.makeVersion(0,1,0));
    }
}
