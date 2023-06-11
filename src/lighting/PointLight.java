package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light
{
    private Point position;
    private double kC=1,kL=0,kO=0;
    public PointLight(Color intensity, Point point)
    {
        super(intensity);
        position = point;
    }

    public PointLight setPosition(Point position) {
        this.position = position;
        return this;
    }

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kO) {
        this.kO = kO;
        return this;
    }

    public Object setKl(double v)
    {
        this.kL = kL;
        return this;
    }

}
