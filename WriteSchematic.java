import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Character;
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

// This program writes a schematic file from an ASCII file containing the data
//
// Call: 
// java WriteSchematic ASCII_input_file SCHEMATIC_output_file
//
// Dependencies: jnbt-1.1.jar library
//
// the ASCII_input_file is currently written using a Matlab program. The file should have the format
//
// width
// height
// length
// block data D(1)
// block data D(2)
// ...
// block data D(n)
//
//where n=width*height*length
//
// The basic algorithm for ordering the 3d block data to a vector D is given 
// width
// height
// length
//
// for k=1:height
//   for j=1:length
//     for i=1:width
//       D(cnt) = BlockData(i,j,k)
//     end
//   end
// end
//
// Comment on data types in D:
// D should be a 3D array with 1 for soil and 0 for air. Other bytes can be written for other block types
// however the integer->byte conversion is not working correctly and the expected block type is not what's actually written

public class WriteSchematic {

    private static Tag getChildTag(Map<String, Tag> items, String key, Class<? extends Tag> expected) {
        Tag tag = items.get(key);
        return tag;
    }

    public static void main(String[] args) {
       

        String line = null;
        File input = new File(args[0]);
        try {
          FileReader filereader = new FileReader(input);
          BufferedReader bufferedReader = new BufferedReader(filereader);

          Map<String, Tag> newTagCollection = new HashMap<String, Tag>();
 
          line = bufferedReader.readLine();
          System.out.println(line);
          short width = Short.parseShort(line);
          ShortTag widthtag = new ShortTag("Width",width);
          newTagCollection.put("Width", widthtag);


          line = bufferedReader.readLine();
          short height = Short.parseShort(line);
          ShortTag heighttag = new ShortTag("Height",height);
          newTagCollection.put("Height", heighttag);
 

          line = bufferedReader.readLine();
          short length = Short.parseShort(line);
          ShortTag lengthtag = new ShortTag("Length",length);
          newTagCollection.put("Length", lengthtag);

          byte[] blocks = new byte[width*height*length];
          byte[] data = new byte[width*height*length];


          int cnt = 0;
          while( (line = bufferedReader.readLine()) != null) {

            //if ( Character.getNumericValue(line.charAt(0)) == 3){
            //  blocks[cnt] = (byte) (32);
            //} else {
              blocks[cnt] = (byte) (Character.getNumericValue(line.charAt(0)));
            //}


            //if ( Character.getNumericValue(line.charAt(0)) == 32){
            //  data[cnt] = (byte) 3;
            //} else {
              data[cnt] = (byte) 0;
            //}
            //System.out.println("Block Data " + cnt + ": " + line + " byte: " + blocks[cnt]);
            cnt = cnt + 1;
          }

          ByteArrayTag blocktag = new ByteArrayTag("Blocks", blocks);
          newTagCollection.put("Blocks", blocktag);

          ByteArrayTag datatag = new ByteArrayTag("Data", data);
          newTagCollection.put("Data", datatag);

          System.out.println("Lines: " + cnt);

          System.out.println("Output " + args[0] + " file successfully read.");


          short widthtest = (Short)getChildTag(newTagCollection, "Width", ShortTag.class).getValue();
          short heighttest = (Short)getChildTag(newTagCollection, "Height", ShortTag.class).getValue();
          short lengthtest = (Short)getChildTag(newTagCollection, "Length", ShortTag.class).getValue();

          CompoundTag newTag = new CompoundTag("Schematic",newTagCollection);



          File output = new File(args[1]);
          FileOutputStream fos = new FileOutputStream(output);
          NBTOutputStream nbtout = new NBTOutputStream(fos);
          nbtout.writeTag(newTag);
          System.out.println("Output " + args[1] + " file successfully created.");
          nbtout.close();
          fos.close();

          }
          catch (Exception e) {
              e.printStackTrace();
          } finally {
          }

     }

}
