package net.tobywig.slayers.recipe.custom;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobywig.slayers.Slayers;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Slayers.MOD_ID);

    public static final RegistryObject<RecipeSerializer<RuneMolderRecipe>> GEM_INFUSING_SERIALIZER =
            SERIALIZERS.register("rune_molding", () -> RuneMolderRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
