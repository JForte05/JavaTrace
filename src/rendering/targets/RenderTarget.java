package rendering.targets;

import color.Color;

public interface RenderTarget {
    public int getXSize();
    public int getYSize();
    public void writePixel(int x, int y, Color c);
}
