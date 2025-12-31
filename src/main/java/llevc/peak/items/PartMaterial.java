package llevc.peak.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.minecraft.util.Identifier;

public record PartMaterial(String partType , float swordDamage, float axeDamage, float pickaxeDamage, float durability) {
    public static <T> RegistryKey<Registry<T>> getRegKey() {
        return RegistryKey.ofRegistry(Identifier.of("parts"));
    }

    public static final Codec<PartMaterial> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.STRING.fieldOf("part_type").forGetter(PartMaterial::partType),
                            Codec.FLOAT.fieldOf("sword_damage").forGetter(PartMaterial::swordDamage),
                            Codec.FLOAT.fieldOf("axe_damage").forGetter(PartMaterial::axeDamage),
                            Codec.FLOAT.fieldOf("pickaxe_damage").forGetter(PartMaterial::pickaxeDamage),
                            Codec.FLOAT.fieldOf("durability").forGetter(PartMaterial::durability)
                    )
                    .apply(instance, PartMaterial::new)
    );
    public static final Codec<RegistryEntry<PartMaterial>> ENTRY_CODEC = RegistryFixedCodec.of(getRegKey());
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<PartMaterial>> ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(getRegKey());

    public PartMaterial(String partType, float durability) {
        this(partType,0f,0f,0f,durability);
    }

    public PartMaterial(String partType , float swordDamage, float axeDamage, float pickaxeDamage) {
        this(partType,swordDamage,axeDamage,pickaxeDamage,0f);
    }
}
