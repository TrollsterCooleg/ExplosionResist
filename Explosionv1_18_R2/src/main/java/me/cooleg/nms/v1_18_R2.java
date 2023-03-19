package me.cooleg.nms;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.lang.reflect.Field;
import java.util.HashMap;

public class v1_18_R2 implements NMSInterface {
    private final HashMap<String, Block> getBlock = new HashMap<>();
    private Field resistanceField;

    public v1_18_R2() {
        try {
            for (Field field : Blocks.class.getFields()) {
                field.setAccessible(true);
                net.minecraft.world.level.block.Block b = (Block) field.get(null);
                getBlock.put(b.asItem().toString().toUpperCase(), b);
            }
        } catch (IllegalAccessException ex) {}
        try {
            resistanceField = BlockBehaviour.class.getDeclaredField("aH");
            resistanceField.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            System.out.println("Exception triggered :(");
        }
        if (resistanceField == null) {
            System.out.println("NO FIELD FOUND!");
        }
    }

    @Override
    public void setResistance(String s, float f) {
        if (!getBlock.containsKey(s.toUpperCase())) {return;}
        try {
            resistanceField.set(getBlock.get(s.toUpperCase()), f);
        } catch (IllegalAccessException ex) {}
    }
}
