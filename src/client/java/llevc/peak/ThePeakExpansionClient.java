package llevc.peak;

import llevc.peak.blocks.CarrierBlockEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;

public class ThePeakExpansionClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BlockRenderLayerMap.putBlock(ModBlocks.Carrier, BlockRenderLayer.CUTOUT);
	}
}