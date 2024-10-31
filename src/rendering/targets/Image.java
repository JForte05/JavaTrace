package rendering.targets;

import java.io.IOException;

import color.Color;
import fileIO.savers.image.ImageSaver;

public final class Image implements RenderTarget {
    private final Color[] pixels;
    private final int xSize;
    private final int ySize;

    public Image(int xSize, int ySize){
        this.xSize = xSize;
        this.ySize = ySize;
        this.pixels = new Color[xSize * ySize];
    }

    @Override
    public void writePixel(int x, int y, Color c) {
        pixels[(y * xSize) + x] = c;
    }

    public int getXSize(){
        return xSize;
    }
    public int getYSize(){
        return ySize;
    }

    public Color[] getPixels(){
        return pixels;
    }

    public Color getPixel(int x, int y){
        return pixels[(y * xSize) + x];
    }

    public void save(String fileName, ImageSaver saver) throws IOException{
        saver.saveImage(fileName, this);
    }
}
