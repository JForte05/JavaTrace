package fileIO.savers.image;

import java.io.File;
import java.io.IOException;

import rendering.targets.Image;

public abstract class ImageSaver {
    protected final String fileExtension;
    protected ImageSaver(String fileExtension){
        this.fileExtension = fileExtension;
    }

    public abstract File saveImage(String fileName, Image image) throws IOException;
}
