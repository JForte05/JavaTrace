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
            Color[] pixels = image.getPixels();
            int size = pixels.length;

            
            for (int i = 0; i < size; i+=8){
                // Not sure this is any faster than sending each pixel's data in a 3 byte buffer
                // but this does reduce the number of write() calls needed which I assume is a
                // pretty expensive operation

                buffer[0] = pixels[i].r;
                buffer[0] = pixels[i].g;
                buffer[0] = pixels[i].b;
                buffer[1] = pixels[i + 1].r;
                buffer[1] = pixels[i + 1].g;
                buffer[1] = pixels[i + 1].b;
                buffer[2] = pixels[i + 2].r;
                buffer[2] = pixels[i + 2].g;
                buffer[2] = pixels[i + 2].b;
                buffer[3] = pixels[i + 3].r;
                buffer[3] = pixels[i + 3].g;
                buffer[3] = pixels[i + 3].b;
                buffer[4] = pixels[i + 4].r;
                buffer[4] = pixels[i + 4].g;
                buffer[4] = pixels[i + 4].b;
                buffer[5] = pixels[i + 5].r;
                buffer[5] = pixels[i + 5].g;
                buffer[5] = pixels[i + 5].b;
                buffer[6] = pixels[i + 6].r;
                buffer[6] = pixels[i + 6].g;
                buffer[6] = pixels[i + 6].b;
                buffer[7] = pixels[i + 7].r;
                buffer[7] = pixels[i + 7].g;
                buffer[7] = pixels[i + 7].b;

                fos.write(buffer);
            }

            fos.close();
        } catch (IOException e){
            throw e;
        }

        return f;
    }   
}
