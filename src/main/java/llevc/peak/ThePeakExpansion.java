package llevc.peak;

import llevc.peak.blocks.ModBlockEntities;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThePeakExpansion implements ModInitializer {
	public static final String MOD_ID = "peak";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModComponents.initialize();
		ModBlocks.initialize();
		ModBlockEntities.initialize();
		ModItems.initialize();

		LOGGER.info("epstein didnt kill himself :)");
	}
}