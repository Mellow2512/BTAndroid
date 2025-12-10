package th.nguyenphananhtai.ungdungdocsach;

public class Book {
    private int id;
    private String title;
    private String author;
    private String category;
    private int coverImage;
    private float rating;
    private int pages;
    private boolean isFavorite;
    private String description;

    public Book(int id, String title, String author, String category,
                int coverImage, float rating, int pages, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.coverImage = coverImage;
        this.rating = rating;
        this.pages = pages;
        this.isFavorite = false;
        this.description = description;
    }

    // Getters and Setters
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

    public int getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(int coverImage) {
        this.coverImage = coverImage;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}