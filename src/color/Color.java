package color;

public class Color {
    public byte r;
    public byte g;
    public byte b;

    public Color(byte r, byte g, byte b){
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public Color(int r, int g, int b){
        this((byte)r, (byte)g, (byte)b);
    }

    public String toHexString(){
        StringBuilder builder = new StringBuilder();
        builder.append(Integer.toHexString(r));
        builder.append(Integer.toHexString(g));
        builder.append(Integer.toHexString(b));
        return builder.toString();
    }

    @Override
    public String toString(){
        return String.format("(%d, %d, %d)", r, g, b);
    }
}
