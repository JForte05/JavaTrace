package fileIO.savers.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import color.Color;
import rendering.targets.Image;
import utilities.fileIO.FileWrite;

public class BMPSaver extends ImageSaver{
    private static final int SAVE_CHUNK_SIZE = 8;

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

            FileWrite.intToBytesLittleEndian(144, buffer);
            fos.write(buffer); // X pixels per meter
            fos.write(buffer); // Y pixels per meter

            FileWrite.intToBytesLittleEndian(0, buffer);
            fos.write(buffer); // Colors used

            fos.write(buffer); // Important colors

            ////////////////
            // Pixel Data //
            ////////////////
            Color[] pixels = image.getPixels();
            Color[] cBuffer = new Color[SAVE_CHUNK_SIZE];
            buffer = new byte[24];

            // Can experiment with different chunk sizes
            int numChunks = FileWrite.numberOfChunks(pixels.length, SAVE_CHUNK_SIZE);
            for (int i = 0; i < numChunks - 1; i++){
                FileWrite.chunkArray(pixels, cBuffer, SAVE_CHUNK_SIZE, i);

                for (int b = 0; b < SAVE_CHUNK_SIZE; b++){
                    Color c = cBuffer[b];
    
                    buffer[b * 3] = c.b;
                    buffer[b * 3 + 1] = c.g;
                    buffer[b * 3 + 2] = c.r;
                }

                fos.write(buffer);
            }

            FileWrite.chunkArray(pixels, cBuffer, SAVE_CHUNK_SIZE, numChunks - 1);
            int safeIndex = 0;
            for (int i = 0; i < 8; i++){
                Color c = cBuffer[i];
                if (c == null)
                    break;

                buffer[i * 3] = c.b;
                buffer[i * 3 + 1] = c.g;
                buffer[i * 3 + 2] = c.r;
                safeIndex = i;
            }
            fos.write(buffer, 0, (safeIndex + 1) * 3);

            fos.close();
        } catch(IOException e){
            throw e;
        }

        return f;
    }
}
