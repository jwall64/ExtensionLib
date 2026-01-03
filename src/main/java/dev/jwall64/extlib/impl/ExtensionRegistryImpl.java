package dev.jwall64.extlib.impl;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.semver4j.Semver;

import java.util.HashMap;
import java.util.Map;

@ApiStatus.Internal
public class ExtensionRegistryImpl {

    public static final Map<Identifier, Semver> ALL_EXTENSIONS = new HashMap<>();

}
