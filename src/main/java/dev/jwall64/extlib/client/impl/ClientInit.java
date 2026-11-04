package dev.jwall64.extlib.client.impl;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.networking.v1.FriendlyByteBufs;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.ApiStatus;
import dev.jwall64.extlib.api.ExtensionRegistry;
import dev.jwall64.extlib.impl.ClientboundEnabledExtensionsPacket;
import dev.jwall64.extlib.impl.ExtensionMap;
import dev.jwall64.extlib.impl.ExtensionMapHolder;
import dev.jwall64.extlib.impl.ServerboundExtensionPacket;
import dev.jwall64.extlib.mixin.AccessorClientHandshakePacketListener;

import java.util.concurrent.CompletableFuture;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ClientLoginNetworking.registerGlobalReceiver(ServerboundExtensionPacket.ID, (client, handler, buf, callbacksConsumer) -> {

            ExtensionMap map = new ExtensionMap(ExtensionRegistry.getAllExtensions());
            ServerData sd = ((AccessorClientHandshakePacketListener) handler).getServerData();
            if(sd != null) {
                map = map.intersection(((ExtensionListHolder) sd).getEnabledExtensions());
            }

            FriendlyByteBuf out = FriendlyByteBufs.create();
            new ServerboundExtensionPacket(map).write(out);

            return CompletableFuture.completedFuture(out);
        });

        ClientConfigurationNetworking.registerGlobalReceiver(ClientboundEnabledExtensionsPacket.TYPE, (payload, context) -> {

            ExtensionMapHolder holder = ((ExtensionMapHolder) context.client());
            holder.setExtensionMap(new ExtensionMap(ExtensionRegistry.getAllExtensions()).intersection(payload.ids()));
        });

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            ((ExtensionMapHolder) client).setExtensionMap(new ExtensionMap(ExtensionRegistry.getAllExtensions()));
        });

    }
}
