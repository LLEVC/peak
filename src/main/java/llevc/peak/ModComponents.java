package llevc.peak;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class ModComponents {
    protected static void initialize() {
        ThePeakExpansion.LOGGER.info("Registering {} components", ThePeakExpansion.MOD_ID);
        ModularComponent.initialize();
        // Technically this method can stay empty, but some developers like to notify
        // the console, that certain parts of the mod have been successfully initialized
    }

    public static final ComponentType<Boolean> StuddedComponent = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ThePeakExpansion.MOD_ID, "studded"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static class ModularComponent {
        public record Modular(String head, String base) {
            public static final Codec<Modular> CODEC = RecordCodecBuilder.create(builder -> {
                return builder.group(
                        Codec.STRING.optionalFieldOf("head","wood").forGetter(Modular::head),
                        Codec.STRING.optionalFieldOf("base","wood").forGetter(Modular::base)
                ).apply(builder, Modular::new);
            });
        }

        public static final ComponentType<Modular> ModularComponentType = Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Identifier.of(ThePeakExpansion.MOD_ID, "modular_tool"),
                ComponentType.<Modular>builder().codec(Modular.CODEC).build()
        );

        public static void initialize() {
        }
    }

    public static boolean isStudded(ItemStack itemStack) {
        return (itemStack.contains(ModComponents.StuddedComponent) && Objects.requireNonNull(itemStack.get(ModComponents.StuddedComponent)));
    }
}