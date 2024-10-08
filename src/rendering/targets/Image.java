package rendering.targets;

import color.Color;

public final class Image implements RenderTarget {
    private Color[] pixels;
    private int xSize;
    private int ySize;

    @Override
    public void writePixel(int x, int y, Color c) {
        pixels[(y * ySize) + x] = c;
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
}
