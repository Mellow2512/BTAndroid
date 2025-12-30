package th.nguyenphananhtai.ungdungdocsach;

public class ReadingHistory {
    private String id;
    private String bookId;
    private String userId;
    private long lastReadTime;
    private long currentPage;
    private long totalPages;
    private float progress;

    // Denormalized book info
    private String bookTitle;
    private String bookAuthor;
    private String bookCoverUrl;
    private String bookCategory;

    // 1. Constructor rỗng (Bắt buộc cho Firebase)
    public ReadingHistory() {
    }

    // 2. Constructor đầy đủ
    public ReadingHistory(String bookId, String userId, String bookTitle,
                          String bookAuthor, String bookCoverUrl, String bookCategory,
                          long totalPages) {
        this.bookId = bookId;
        this.userId = userId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookCoverUrl = bookCoverUrl;
        this.bookCategory = bookCategory;
        this.totalPages = totalPages;
        this.currentPage = 0;
        this.progress = 0;
        this.lastReadTime = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public long getLastReadTime() { return lastReadTime; }
    public void setLastReadTime(long lastReadTime) { this.lastReadTime = lastReadTime; }

    public long getCurrentPage() { return currentPage; }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalPages() { return totalPages; }
    public void setTotalPages(long totalPages) { this.totalPages = totalPages; }

    public float getProgress() { return progress; }
    public void setProgress(float progress) { this.progress = progress; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getBookAuthor() { return bookAuthor; }
    public void setBookAuthor(String bookAuthor) { this.bookAuthor = bookAuthor; }

    public String getBookCoverUrl() { return bookCoverUrl; }
    public void setBookCoverUrl(String bookCoverUrl) { this.bookCoverUrl = bookCoverUrl; }

    public String getBookCategory() { return bookCategory; }
    public void setBookCategory(String bookCategory) { this.bookCategory = bookCategory; }

    // Helper methods hiển thị
    public String getProgressText() {
        return String.format("%.0f%%", progress);
    }

    public String getTimeAgo() {
        long diff = System.currentTimeMillis() - lastReadTime;
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return days + " ngày trước";
        } else if (hours > 0) {
            return hours + " giờ trước";
        } else if (minutes > 0) {
            return minutes + " phút trước";
        } else {
            return "Vừa xong";
        }
    }
}