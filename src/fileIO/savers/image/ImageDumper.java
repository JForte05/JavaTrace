package fileIO.savers.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import color.Color;
import rendering.targets.Image;
import utilities.fileIO.FileWrite;

public class ImageDumper extends ImageSaver {
    public ImageDumper() {
        super(".bif");
    }

    @Override
    public File saveImage(String fileName, Image image) throws IOException{
        File f = new File(fileName + fileExtension);
        
        f.createNewFile();
        try (FileOutputStream fos = new FileOutputStream(f);){
            byte[] buffer = new byte[] {'B', 'I', 'N', '_', 'I', 'M', 'G', 0};
            
            fos.write(buffer);

            FileWrite.intToBytesBigEndian(image.getXSize(), buffer);
            FileWrite.intToBytesBigEndian(image.getYSize(), buffer, 4);
            fos.write(buffer);

            buffer = new byte[24];
            Color[] cBuffer = new Color[8];
            Color[] pixels = image.getPixels();
            
            int numChunks = FileWrite.numberOfChunks(pixels.length, 8);
            for (int i = 0; i < numChunks - 1; i++){
                FileWrite.chunkArray(pixels, cBuffer, 8, i);

                buffer[0] = cBuffer[0].r;
                buffer[1] = cBuffer[0].g;
                buffer[2] = cBuffer[0].b;
                buffer[3] = cBuffer[1].r;
                buffer[4] = cBuffer[1].g;
                buffer[5] = cBuffer[1].b;
                buffer[6] = cBuffer[2].r;
                buffer[7] = cBuffer[2].g;
                buffer[8] = cBuffer[2].b;
                buffer[9] = cBuffer[3].r;
                buffer[10] = cBuffer[3].g;
                buffer[11] = cBuffer[3].b;
                buffer[12] = cBuffer[4].r;
                buffer[13] = cBuffer[4].g;
                buffer[14] = cBuffer[4].b;
                buffer[15] = cBuffer[5].r;
                buffer[16] = cBuffer[5].g;
                buffer[17] = cBuffer[5].b;
                buffer[18] = cBuffer[6].r;
                buffer[19] = cBuffer[6].g;
                buffer[20] = cBuffer[6].b;
                buffer[21] = cBuffer[7].r;
                buffer[22] = cBuffer[7].g;
                buffer[23] = cBuffer[7].b;

                fos.write(buffer);
            }

            // Get final chunk and check each pixel to see if it is null, save where the last safe 
            // pixel is and write only the appropriate bytes

            // Is slightly in efficient for cases where the number of pixels is a multiple of 8
            // as we would know that the final chunk is fully filled and contains no nulls
            // but I can come back and add conditionals later if tiny loop is taking too long
            FileWrite.chunkArray(pixels, cBuffer, 8, numChunks - 1);
            int nonNullColors = 0;
            for (int i = 0; i < 8; i++){
                Color c = cBuffer[i];
                if (c == null)
                    break;

                buffer[i * 3] = c.r;
                buffer[i * 3 + 1] = c.g;
                buffer[i * 3 + 2] = c.b;
                nonNullColors = i + 1;
            }
            fos.write(buffer, 0, nonNullColors * 3 - 1);

            fos.close();
        } catch (IOException e){
            throw e;
        }

        return f;
    }   
}
