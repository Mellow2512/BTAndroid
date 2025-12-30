package th.nguyenphananhtai.ungdungdocsach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDetailActivity extends AppCompatActivity {

    private ImageView btnBack, btnFavoriteDetail, imgBookCoverDetail;
    private TextView tvBookTitleDetail, tvAuthorDetail, tvRatingDetail;
    private TextView tvPagesDetail, tvCategoryDetail, tvDescriptionDetail;
    private CardView btnReadNow, btnDownload;

    private DatabaseReference databaseReference;
    private String userId;
    private String bookId;
    private Book currentBook;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

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
        // Load book from Firebase
        databaseReference.child("books").child(bookId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            currentBook = snapshot.getValue(Book.class);
                            if (currentBook != null) {
                                displayBookDetails(currentBook);
                            } else {
                                Toast.makeText(BookDetailActivity.this,
                                        "Lỗi: Không thể tải thông tin sách",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            Toast.makeText(BookDetailActivity.this,
                                    "Sách không tồn tại",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(BookDetailActivity.this,
                                "Lỗi tải sách: " + error.getMessage(),
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
        // Check if book is in favorites
        databaseReference.child("favorites").child(userId).child(bookId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        isFavorite = snapshot.exists();
                        updateFavoriteIcon();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("BookDetail", "Error checking favorite: " + error.getMessage());
                    }
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
                // Chuyển sang màn hình đọc sách
                Intent intent = new Intent(BookDetailActivity.this, ReadBookActivity.class);
                intent.putExtra("bookId", currentBook.getId());
                startActivity(intent);
            }
        });

        btnDownload.setOnClickListener(v -> {
            if (currentBook != null) {
                Toast.makeText(this, "Đang tải xuống: " + currentBook.getTitle(),
                        Toast.LENGTH_SHORT).show();
                // TODO: Implement download functionality
            }
        });
    }

    private void toggleFavorite() {
        if (currentBook == null) return;

        DatabaseReference favRef = databaseReference.child("favorites").child(userId).child(bookId);

        if (isFavorite) {
            // Remove from favorites
            favRef.removeValue()
                    .addOnSuccessListener(aVoid -> {
                        isFavorite = false;
                        updateFavoriteIcon();
                        Toast.makeText(BookDetailActivity.this,
                                "Đã xóa khỏi yêu thích",
                                Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(BookDetailActivity.this,
                                "Lỗi: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Add to favorites
            favRef.setValue(currentBook)
                    .addOnSuccessListener(aVoid -> {
                        isFavorite = true;
                        updateFavoriteIcon();
                        Toast.makeText(BookDetailActivity.this,
                                "Đã thêm vào yêu thích",
                                Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(BookDetailActivity.this,
                                "Lỗi: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
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

        // Save to Firebase
        databaseReference.child("reading_history").child(userId).child(bookId)
                .setValue(history)
                .addOnSuccessListener(aVoid -> {
                    Log.d("BookDetail", "Saved to reading history");
                })
                .addOnFailureListener(e -> {
                    Log.e("BookDetail", "Failed to save history: " + e.getMessage());
                });
    }
}