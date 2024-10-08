package rendering.targets;

import color.Color;

public interface RenderTarget {
    public void acceptDimensions(int x, int y);
    public void writePixel(int x, int y, Color c);
}
