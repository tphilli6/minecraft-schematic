import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.*;
import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.ListTag;
import org.jnbt.NBTInputStream;
import org.jnbt.NBTOutputStream;
import org.jnbt.ShortTag;
import org.jnbt.Tag;
import org.jnbt.IntTag;

// This program reads a *.schematic file and prints the data to a file named ASCII-*.schematic
// Data is
// Width
// Height
// Length
// block data 1
// block data 2
// ...
// block data n
//
// Other data is usually included in the *.schematic file however the size and block data is sufficient for
// plotting the basic structure using the matlab file plot_text_schematic.m and for checking that WriteSchematic
// correctly wrote out to file.

public class ReadSchematic {

    private static Tag getChildTag(Map<String, Tag> items, String key, Class<? extends Tag> expected) {
        Tag tag = items.get(key);
        return tag;
    }

    public static void main(String[] args) {
        try {
            //File f = new File("mini-river-and-town.schematic");
            System.out.println("Opening " + args[0]);
            File f = new File(args[0]);
            FileInputStream fis = new FileInputStream(f);
            NBTInputStream nbt = new NBTInputStream(fis);
            CompoundTag backuptag = (CompoundTag) nbt.readTag();
            Map<String, Tag> tagCollection = backuptag.getValue();



            short width = (Short)getChildTag(tagCollection, "Width", ShortTag.class).getValue();
            short height = (Short) getChildTag(tagCollection, "Height", ShortTag.class).getValue();
            short length = (Short) getChildTag(tagCollection, "Length", ShortTag.class).getValue();

            byte[] blocks = (byte[]) getChildTag(tagCollection, "Blocks", ByteArrayTag.class).getValue();
            //byte[] data = (byte[]) getChildTag(tagCollection, "Data", ByteArrayTag.class).getValue();

            //List entities = (List) getChildTag(tagCollection, "Entities", ListTag.class).getValue();
            //List tileentities = (List) getChildTag(tagCollection, "TileEntities", ListTag.class).getValue();
            nbt.close();
            fis.close();

            System.out.println(backuptag);


            Writer writer = new BufferedWriter( new OutputStreamWriter(
                            new FileOutputStream("ASCII-"+args[0]) ) );
            //File output = new File(args[1]);
            //FileOutputStream fos = new FileOutputStream(output);

            writer.write(width);
            writer.write(height);
            writer.write(length);
            System.out.println(width);
            System.out.println(height);
            System.out.println(length);
            //System.out.println(entities);
            //System.out.println(tileentities);
            int[] iarray = new int[blocks.length];
            int i = 0;
            for (byte b: blocks)
            {
                iarray[i++] = b & 0xff;
                writer.write(iarray[i-1]);
                System.out.println(iarray[i-1]); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

