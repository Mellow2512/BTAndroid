package th.nguyenphananhtai.ungdungdocsach;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class BookDetailActivity extends AppCompatActivity {

    private ImageView btnBack, btnFavoriteDetail, imgBookCoverDetail;
    private TextView tvBookTitleDetail, tvAuthorDetail, tvRatingDetail;
    private TextView tvPagesDetail, tvCategoryDetail, tvDescriptionDetail;
    private CardView btnReadNow, btnDownload;

    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // Initialize views
        initViews();

        // Get book data from intent
        String bookTitle = getIntent().getStringExtra("bookTitle");
        int bookId = getIntent().getIntExtra("bookId", -1);

        // Load book details (thay bằng dữ liệu thật từ database)
        loadBookDetails(bookId);

        // Setup click listeners
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

    private void loadBookDetails(int bookId) {
        // TODO: Load từ database thật
        // Hiện tại dùng dữ liệu mẫu

        tvBookTitleDetail.setText("Đắc Nhân Tâm");
        tvAuthorDetail.setText("Dale Carnegie");
        tvRatingDetail.setText("4.8");
        tvPagesDetail.setText("320");
        tvCategoryDetail.setText("Kỹ năng sống");
        tvDescriptionDetail.setText("Đắc Nhân Tâm của Dale Carnegie là quyển sách nổi tiếng nhất, " +
                "bán chạy nhất và có tầm ảnh hưởng nhất của mọi thời đại. Tác phẩm đã được chuyển ngữ " +
                "sang hầu hết các thứ tiếng trên thế giới và có mặt ở hàng trăm quốc gia. " +
                "\n\nĐây là quyển sách duy nhất về thể loại self-help liên tục đứng đầu danh mục sách " +
                "bán chạy nhất (best-selling Books) do báo The New York Times bình chọn suốt 10 năm liền. " +
                "\n\nTrong cuốn sách này, Dale Carnegie đã đúc kết những bí quyết vàng để giao tiếp và " +
                "ứng xử với mọi người một cách khéo léo, tạo được thiện cảm ngay từ những giây phút đầu tiên.");

        imgBookCoverDetail.setImageResource(R.drawable.book1);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnFavoriteDetail.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            if (isFavorite) {
                btnFavoriteDetail.setImageResource(R.drawable.ic_favorite_filled);
                Toast.makeText(this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
            } else {
                btnFavoriteDetail.setImageResource(R.drawable.ic_favorite_border);
                Toast.makeText(this, "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
            }
        });

        btnReadNow.setOnClickListener(v -> {
            // TODO: Navigate to Reader Activity
            Toast.makeText(this, "Mở trình đọc sách", Toast.LENGTH_SHORT).show();
        });

        btnDownload.setOnClickListener(v -> {
            // TODO: Implement download functionality
            Toast.makeText(this, "Đang tải xuống...", Toast.LENGTH_SHORT).show();
        });
    }
}