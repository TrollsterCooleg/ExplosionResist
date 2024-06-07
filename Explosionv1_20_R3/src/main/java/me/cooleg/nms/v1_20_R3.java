package me.cooleg.nms;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBase;

import java.lang.reflect.Field;
import java.util.HashMap;

public class v1_20_R3 implements NMSInterface {

    private final HashMap<String, Block> getBlock = new HashMap<>();
    private final HashMap<Block, Float> originalValues = new HashMap<>();
    private Field resistanceField;

    public v1_20_R3() {
        try {
            for (Field field : Blocks.class.getFields()) {
                field.setAccessible(true);
                net.minecraft.world.level.block.Block b = (Block) field.get(null);
                getBlock.put(b.k().toString().toUpperCase(), b);
            }
        } catch (IllegalAccessException ex) {}
        try {
            resistanceField = BlockBase.class.getDeclaredField("aH");
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
            Block b = getBlock.get(s.toUpperCase());
            originalValues.put(b, resistanceField.getFloat(b));
            resistanceField.set(getBlock.get(s.toUpperCase()), f);
        } catch (IllegalAccessException ex) {}
    }

    @Override
    public void resetResistances() {
        for (Block b : originalValues.keySet()) {
            try {
                resistanceField.set(b, originalValues.get(b));
            } catch (IllegalAccessException ex) {}
        }
    }
}
