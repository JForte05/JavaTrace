package rendering.engines;

import rendering.Camera;
import rendering.Renderable;
import rendering.targets.Image;

public interface Renderer {
    public Image render(Renderable subject, Camera through, int veritcalResolution);
}
