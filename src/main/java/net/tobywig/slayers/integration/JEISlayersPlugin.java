package net.tobywig.slayers.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.recipe.custom.RuneMolderRecipe;

import java.util.List;
import java.util.Objects;


@JeiPlugin
public class JEISlayersPlugin implements IModPlugin {
    public static RecipeType<RuneMolderRecipe> MOLDING_TYPE =
            new RecipeType<>(RuneMolderRecipeCategory.UID, RuneMolderRecipe.class);


    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Slayers.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                RuneMolderRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<RuneMolderRecipe> recipesMolding = rm.getAllRecipesFor(RuneMolderRecipe.Type.INSTANCE);
        registration.addRecipes(MOLDING_TYPE, recipesMolding);
    }
}
