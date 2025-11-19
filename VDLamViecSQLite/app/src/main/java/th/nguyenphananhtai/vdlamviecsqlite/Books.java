package th.nguyenphananhtai.vdlamviecsqlite;

public class Books {
    private int id;
    private String title;
    private int page;
    private float price;
    private String description;

    public Books(int id, String title, int page, float price, String description) {
        this.id = id;
        this.title = title;
        this.page = page;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
