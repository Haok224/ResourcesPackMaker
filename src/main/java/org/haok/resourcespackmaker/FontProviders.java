package org.haok.resourcespackmaker;
@SuppressWarnings({"all"})
public class FontProviders {
    String type;
    String file;
    int[] shift;
    double size;
    double oversample;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int[] getShift() {
        return shift;
    }

    public void setShift(int[] shift) {
        this.shift = shift;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getOversample() {
        return oversample;
    }

    public void setOversample(double oversample) {
        this.oversample = oversample;
    }

    public FontProviders(String type, String file, int[] shift, double size, double oversample) {
        this.type = type;
        this.file = file;
        this.shift = shift;
        this.size = size;
        this.oversample = oversample;
    }
}
