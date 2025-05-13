package dev.jwall64.extlib.impl;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.semver4j.RangesList;
import org.semver4j.RangesListFactory;
import dev.jwall64.mdcfg.serializer.InlineSerializer;
import dev.jwall64.mdcfg.serializer.Serializer;

@ApiStatus.Internal
public class Util {

    public static final InlineSerializer<ResourceLocation> ID_SERIALIZER = InlineSerializer.of(ResourceLocation::toString, ResourceLocation::tryParse);

    public static final Serializer<RangesList> VERSION_PREDICATE_SERIALIZER = InlineSerializer.of(RangesList::toString, RangesListFactory::create);

}
