package dev.jwall64.extlib.client.impl;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import dev.jwall64.extlib.api.ExtensionRegistry;
import dev.jwall64.extlib.impl.Util;
import dev.jwall64.mdcfg.ConfigList;
import dev.jwall64.mdcfg.ConfigObject;
import dev.jwall64.mdcfg.codec.FileWrapper;
import dev.jwall64.mdcfg.serializer.ConfigContext;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public interface ExtensionListHolder {

    List<ResourceLocation> getEnabledExtensions();

    void setEnabledExtensions(List<ResourceLocation> extensions);


    record FileExtensionListHolder(FileWrapper<ConfigObject> file) implements ExtensionListHolder {

        @Override
        public List<ResourceLocation> getEnabledExtensions() {
            file.load();
            if(file.getRoot() == null || !file.getRoot().isList()) {
                file.setRoot(new ConfigList());
                return new ArrayList<>(ExtensionRegistry.getAllExtensions().keySet());
            }

            ConfigList list = file.getRoot().asList();
            return Util.ID_SERIALIZER.listOf().mapToList().deserialize(ConfigContext.INSTANCE, list).getOrThrow();
        }

        @Override
        public void setEnabledExtensions(List<ResourceLocation> extensions) {
            file.setRoot(Util.ID_SERIALIZER.listOf().mapToList().serialize(ConfigContext.INSTANCE, extensions).getOrThrow());
        }
    }

}
