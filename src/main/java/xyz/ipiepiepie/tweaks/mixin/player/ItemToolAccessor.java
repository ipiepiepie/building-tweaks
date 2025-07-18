package xyz.ipiepiepie.tweaks.mixin.player;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.tool.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ItemTool.class, remap = false)
public interface ItemToolAccessor {

	@Accessor
	Tag<Block<?>> getTagEffectiveAgainst();

}
