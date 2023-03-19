package me.cooleg.nms;

import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.BlockBase;
import net.minecraft.server.v1_16_R3.Blocks;

import java.lang.reflect.Field;
import java.util.HashMap;

public class v1_16_R3 implements NMSInterface{

    private final HashMap<String, Block> getBlock = new HashMap<>();
    private Field resistanceField;

    public v1_16_R3() {
        try {
            for (Field field : Blocks.class.getFields()) {
                Block b = (Block) field.get(null);
                getBlock.put(field.getName().toUpperCase(), b);
            }
        } catch (IllegalAccessException ex) {}
        try {
            resistanceField = BlockBase.class.getDeclaredField("durability");
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
