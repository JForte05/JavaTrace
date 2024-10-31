package fileIO.savers.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import color.Color;
import rendering.targets.Image;
import utilities.fileIO.FileWrite;

public class BMPSaver extends ImageSaver{
    public BMPSaver(){
        super(".bmp");
    }

    // See: https://www.ece.ualberta.ca/~elliott/ee552/studentAppNotes/2003_w/misc/bmp_file_format/bmp_file_format.htm
    @Override
    public File saveImage(String fileName, Image image) throws IOException {
        File f = new File(fileName + fileExtension);
        
        f.createNewFile();
        try(FileOutputStream fos = new FileOutputStream(f);){
            ////////////
            // HEADER //
            ////////////
            byte[] buffer = new byte[4];
            buffer[2] = 'B';
            buffer[3] = 'M';
            fos.write(buffer, 2, 2); // Header
            
            int fileSizeBytes = (3 * image.getXSize() * image.getYSize()) + 0x36;
            FileWrite.intToBytesLittleEndian(fileSizeBytes, buffer);
            fos.write(buffer); // File size in bytes
            
            FileWrite.intToBytesLittleEndian(0, buffer);
            fos.write(buffer); // Reserved

            FileWrite.intToBytesLittleEndian(0x36, buffer);
            fos.write(buffer); // Data offset

            /////////////////
            // INFO HEADER //
            /////////////////
            FileWrite.intToBytesLittleEndian(40, buffer);
            fos.write(buffer); // Info header size in bytes

            FileWrite.intToBytesLittleEndian(image.getXSize(), buffer);
            fos.write(buffer); // Width
            FileWrite.intToBytesLittleEndian(image.getYSize(), buffer);
            fos.write(buffer); // Height

            FileWrite.intToBytesLittleEndian(1 + (24 << 16), buffer);
            fos.write(buffer); // Planes

            FileWrite.intToBytesBigEndian(0, buffer);
            fos.write(buffer); // Compression

            FileWrite.intToBytesLittleEndian(image.getXSize() * image.getYSize(), buffer);
            fos.write(buffer); // Image Size

            FileWrite.intToBytesLittleEndian(0, buffer);
            fos.write(buffer); // X pixels per meter
            fos.write(buffer); // Y pixels per meter

            fos.write(buffer); // Colors used

            fos.write(buffer); // Important colors

            ////////////////
            // Pixel Data //
            ////////////////
            Color[] pixels = image.getPixels();
            Color[] cBuffer = new Color[8];
            buffer = new byte[24];

            // Can experiment with different chunk sizes
            int numChunks = FileWrite.numberOfChunks(pixels.length, 8);
            for (int i = 0; i < numChunks - 1; i++){
                FileWrite.chunkArray(pixels, cBuffer, 8, i);

                buffer[0] = cBuffer[0].b;
                buffer[1] = cBuffer[0].g;
                buffer[2] = cBuffer[0].r;
                buffer[3] = cBuffer[1].b;
                buffer[4] = cBuffer[1].g;
                buffer[5] = cBuffer[1].r;
                buffer[6] = cBuffer[2].b;
                buffer[7] = cBuffer[2].g;
                buffer[8] = cBuffer[2].r;
                buffer[9] = cBuffer[3].b;
                buffer[10] = cBuffer[3].g;
                buffer[11] = cBuffer[3].r;
                buffer[12] = cBuffer[4].b;
                buffer[13] = cBuffer[4].g;
                buffer[14] = cBuffer[4].r;
                buffer[15] = cBuffer[5].b;
                buffer[16] = cBuffer[5].g;
                buffer[17] = cBuffer[5].r;
                buffer[18] = cBuffer[6].b;
                buffer[19] = cBuffer[6].g;
                buffer[20] = cBuffer[6].r;
                buffer[21] = cBuffer[7].b;
                buffer[22] = cBuffer[7].g;
                buffer[23] = cBuffer[7].r;

                fos.write(buffer);
            }

            FileWrite.chunkArray(pixels, cBuffer, 8, numChunks - 1);
            int nonNullColors = 0;
            for (int i = 0; i < 8; i++){
                Color c = cBuffer[i];
                if (c == null)
                    break;

                buffer[i * 3] = c.b;
                buffer[i * 3 + 1] = c.g;
                buffer[i * 3 + 2] = c.r;
                nonNullColors = i + 1;
            }
            fos.write(buffer, 0, nonNullColors * 3 - 1);

            fos.close();
        } catch(IOException e){
            throw e;
        }

        return f;
    }
}
