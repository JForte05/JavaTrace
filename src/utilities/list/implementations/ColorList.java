package utilities.list.implementations;

import color.Color;
import utilities.list.ListDL;
import utilities.list.ListNode;

public class ColorList extends ListDL<Color> {
    @Override
    protected ListNode<Color> createNewNode(Color data) {
        return new ColorNode(data.r, data.g, data.b);
    }
    
    private class ColorNode extends ListNode<Color> {
        private byte r;
        private byte g;
        private byte b;
    
        public ColorNode(byte r, byte g, byte b){
            this.r = r;
            this.b = b;
            this.g = g;
        }
    
        @Override
        public Color getItem() {
            return new Color(r, g, b);
        }
    
        @Override
        public void setItem(Color item) {
            this.r = item.r;
            this.g = item.g;
            this.b = item.b;
        }
        
    }
}
