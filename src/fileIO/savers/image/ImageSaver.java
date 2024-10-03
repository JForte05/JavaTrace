package fileIO.savers.image;

import java.io.File;

import rendering.targets.Image;

public interface ImageSaver {
    File saveImage(Image image);
}
