package th.nguyenphananhtai.ungdungdocsach;

public class Book {
    private String id;
    private String title;
    private String author;
    private String category;
    private String coverUrl;
    private float rating;
    private long pages;
    private String description;
    private long createdAt;

    public Book() {
    }

    // Constructor đầy đủ
    public Book(String id, String title, String author, String category,
                String coverUrl, float rating, int pages, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.coverUrl = coverUrl;
        this.rating = rating;
        this.pages = pages;
        this.description = description;
        this.createdAt = System.currentTimeMillis();
    }

    // Getters và Setters - TÊN PHẢI KHỚP
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCoverUrl() {  // ĐÂY LÀ GETTER ĐÚNG
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}