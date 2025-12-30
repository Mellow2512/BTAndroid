package th.nguyenphananhtai.ungdungdocsach;

public class Book {
    private String id;
    private String title;
    private String author;
    private String category;
    private String coverUrl;
    private double rating;
    private long pages;
    private String description;
    private long createdAt;
    private String content;
    private boolean isFavorite = false;

    // 1. Constructor rỗng (BẮT BUỘC cho Firebase)
    public Book() {
    }

    // 2. Constructor đầy đủ (Cập nhật kiểu dữ liệu double và long)
    public Book(String id, String title, String author, String category,
                String coverUrl, double rating, long pages, String description) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    // Getter rating trả về double
    public double getRating() {
        return rating;
    }

    // Setter rating nhận vào double
    public void setRating(double rating) {
        this.rating = rating;
    }

    // Getter pages trả về long
    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}