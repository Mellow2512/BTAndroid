package th.nguyenphananhtai.recyclerview;

public class LandScape {
    String landImageFileName;
    String landCaption;

    public LandScape(String landImageFileName, String landCaption) {
        this.landImageFileName = landImageFileName;
        this.landCaption = landCaption;
    }

    public String getLandImageFileName() {
        return landImageFileName;
    }

    public void setLandImageFileName(String landImageFileName) {
        this.landImageFileName = landImageFileName;
    }

    public String getlandCaption() {
        return landCaption;
    }

    public void setlandCaption(String landCaption) {
        this.landCaption = landCaption;
    }
}
