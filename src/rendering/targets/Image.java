package rendering.targets;

import java.io.IOException;

import color.Color;
import fileIO.savers.image.ImageSaver;

public final class Image implements RenderTarget {
    private Color[] pixels;
    private int xSize;
    private int ySize;

    @Override
    public void writePixel(int x, int y, Color c) {
        pixels[(y * xSize) + x] = c;
    }

    @Override
    public void acceptDimensions(int x, int y) {
        pixels = new Color[x * y];
        this.xSize = x;
        this.ySize = y;
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

    public void save(String fileName, ImageSaver saver) throws IOException{
        saver.saveImage(fileName, this);
    }
}
