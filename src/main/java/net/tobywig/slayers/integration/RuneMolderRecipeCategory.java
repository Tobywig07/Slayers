package net.tobywig.slayers.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.block.ModBlocks;
import net.tobywig.slayers.recipe.RuneMolderRecipe;

import java.util.List;

public class RuneMolderRecipeCategory implements IRecipeCategory<RuneMolderRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(Slayers.MOD_ID, "rune_molding");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(Slayers.MOD_ID, "textures/gui/rune_molder_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public RuneMolderRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.RUNE_MOLDER.get()));
    }

    @Override
    public RecipeType<RuneMolderRecipe> getRecipeType() {
        return JEISlayersPlugin.MOLDING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Rune Molder");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RuneMolderRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 36, 15).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 69, 15).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 111, 15)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(recipe.getFluidStack()))
                .setFluidRenderer(64000, false, 16, 61);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 52, 58).addItemStack(recipe.getResultItem());
    }
}
