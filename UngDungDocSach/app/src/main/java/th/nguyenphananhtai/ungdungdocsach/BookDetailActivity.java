package th.nguyenphananhtai.ungdungdocsach;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

public class BookDetailActivity extends AppCompatActivity {

    private ImageView btnBack, btnFavoriteDetail, imgBookCoverDetail;
    private TextView tvBookTitleDetail, tvAuthorDetail, tvRatingDetail;
    private TextView tvPagesDetail, tvCategoryDetail, tvDescriptionDetail;
    private CardView btnReadNow, btnDownload;

    private LocalDataManager dataManager;
    private String userId;
    private String bookId;
    private Book currentBook;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // Initialize Local Data Manager
        dataManager = LocalDataManager.getInstance(this);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = prefs.getString("userId", "guest_" + System.currentTimeMillis());

        initViews();

        bookId = getIntent().getStringExtra("bookId");

        if (bookId != null) {
            loadBookDetails(bookId);
            checkFavoriteStatus();
        } else {
            Toast.makeText(this, "Lỗi: Không tìm thấy sách", Toast.LENGTH_SHORT).show();
            finish();
        }

        setupClickListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnFavoriteDetail = findViewById(R.id.btnFavoriteDetail);
        imgBookCoverDetail = findViewById(R.id.imgBookCoverDetail);
        tvBookTitleDetail = findViewById(R.id.tvBookTitleDetail);
        tvAuthorDetail = findViewById(R.id.tvAuthorDetail);
        tvRatingDetail = findViewById(R.id.tvRatingDetail);
        tvPagesDetail = findViewById(R.id.tvPagesDetail);
        tvCategoryDetail = findViewById(R.id.tvCategoryDetail);
        tvDescriptionDetail = findViewById(R.id.tvDescriptionDetail);
        btnReadNow = findViewById(R.id.btnReadNow);
        btnDownload = findViewById(R.id.btnDownload);
    }

    private void loadBookDetails(String bookId) {
        dataManager.getBookByIdAsync(bookId, new LocalDataManager.OnBookLoadedListener() {
            @Override
            public void onBookLoaded(Book book) {
                currentBook = book;
                displayBookDetails(book);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(BookDetailActivity.this,
                        "Lỗi tải sách: " + error,
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void displayBookDetails(Book book) {
        tvBookTitleDetail.setText(book.getTitle());
        tvAuthorDetail.setText(book.getAuthor());
        tvRatingDetail.setText(String.format("%.1f", book.getRating()));
        tvPagesDetail.setText(String.valueOf(book.getPages()));
        tvCategoryDetail.setText(book.getCategory());
        tvDescriptionDetail.setText(book.getDescription());

        // Load cover image with Glide
        Glide.with(this)
                .load(book.getCoverUrl())
                .placeholder(R.drawable.book_placeholder)
                .error(R.drawable.book_placeholder)
                .centerCrop()
                .into(imgBookCoverDetail);

        // Lưu vào lịch sử đọc
        saveToReadingHistory(book);
    }

    private void checkFavoriteStatus() {
        dataManager.isFavoriteAsync(userId, bookId, isFav -> {
            isFavorite = isFav;
            updateFavoriteIcon();
        });
    }

    private void updateFavoriteIcon() {
        if (isFavorite) {
            btnFavoriteDetail.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            btnFavoriteDetail.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnFavoriteDetail.setOnClickListener(v -> toggleFavorite());

        btnReadNow.setOnClickListener(v -> {
            if (currentBook != null) {
                Toast.makeText(this, "Mở trình đọc sách: " + currentBook.getTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnDownload.setOnClickListener(v -> {
            if (currentBook != null) {
                Toast.makeText(this, "Đang tải xuống: " + currentBook.getTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleFavorite() {
        if (currentBook == null) return;

        if (isFavorite) {
            dataManager.removeFromFavorites(userId, bookId,
                    new LocalDataManager.OnOperationListener() {
                        @Override
                        public void onSuccess() {
                            isFavorite = false;
                            updateFavoriteIcon();
                            Toast.makeText(BookDetailActivity.this,
                                    "Đã xóa khỏi yêu thích",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            dataManager.addToFavorites(userId, bookId,
                    new LocalDataManager.OnOperationListener() {
                        @Override
                        public void onSuccess() {
                            isFavorite = true;
                            updateFavoriteIcon();
                            Toast.makeText(BookDetailActivity.this,
                                    "Đã thêm vào yêu thích",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveToReadingHistory(Book book) {
        ReadingHistory history = new ReadingHistory(
                book.getId(),
                userId,
                book.getTitle(),
                book.getAuthor(),
                book.getCoverUrl(),
                book.getCategory(),
                book.getPages()
        );

        dataManager.saveReadingHistory(userId, history, new LocalDataManager.OnOperationListener() {
            @Override
            public void onSuccess() {
                Log.d("BookDetail", "Saved to reading history");
            }
        });
    }
}