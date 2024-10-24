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
            buffer = new byte[3];
            int i = 0;
            for (Color c : pixels) {
                if (c == null){
                    System.out.println(i);
                }
                // reverse byte order for little endian
                buffer[0] = c.b;
                buffer[1] = c.g;
                buffer[2] = c.r;
                fos.write(buffer); // Write pixel
                i++;
            }

        } catch(IOException e){
            throw e;
        }

        return f;
    }
}
