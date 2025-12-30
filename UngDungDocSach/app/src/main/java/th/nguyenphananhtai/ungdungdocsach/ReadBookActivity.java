package th.nguyenphananhtai.ungdungdocsach;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadBookActivity extends AppCompatActivity {

    // Views
    private ImageView btnBackRead, btnMenu;
    private TextView tvHeaderTitle, tvProgress, tvBookContent;
    private TextView tvCurrentPage, tvTotalPage;
    private SeekBar seekBarPage;
    private CardView btnPrevious, btnNext;
    private ScrollView scrollView;

    // Data
    private DatabaseReference databaseReference;
    private String bookId, userId;
    private Book currentBook;

    // Reading progress
    private int currentPage = 1;
    private int totalPages = 1;
    private String[] pageContents; // Array chứa nội dung từng trang

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        // 1. Setup Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // 2. Lấy User ID
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = prefs.getString("userId", "guest");

        // 3. Init Views
        initViews();

        // 4. Lấy dữ liệu sách
        bookId = getIntent().getStringExtra("bookId");
        currentPage = getIntent().getIntExtra("currentPage", 1);

        if (bookId != null) {
            loadBookContent();
        } else {
            Toast.makeText(this, "Lỗi: Không tìm thấy sách", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initViews() {
        btnBackRead = findViewById(R.id.btnBackRead);
        btnMenu = findViewById(R.id.btnMenu);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvProgress = findViewById(R.id.tvProgress);
        tvBookContent = findViewById(R.id.tvBookContent);
        tvCurrentPage = findViewById(R.id.tvCurrentPage);
        tvTotalPage = findViewById(R.id.tvTotalPage);
        seekBarPage = findViewById(R.id.seekBarPage);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        scrollView = findViewById(R.id.scrollView);

        // Setup click listeners
        btnBackRead.setOnClickListener(v -> finish());

        btnMenu.setOnClickListener(v ->
                Toast.makeText(this, "Menu tùy chỉnh", Toast.LENGTH_SHORT).show());

        btnPrevious.setOnClickListener(v -> previousPage());
        btnNext.setOnClickListener(v -> nextPage());

        // SeekBar listener
        seekBarPage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && progress > 0) {
                    currentPage = progress;
                    updatePageContent();
                    scrollToTop();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saveReadingProgress();
            }
        });
    }

    private void loadBookContent() {
        databaseReference.child("books").child(bookId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currentBook = snapshot.getValue(Book.class);
                        if (currentBook != null) {
                            // Hiển thị tiêu đề
                            tvHeaderTitle.setText(currentBook.getTitle());

                            // Tính tổng số trang (giả sử 500 từ/trang)
                            String content = currentBook.getContent();
                            if (content == null || content.isEmpty()) {
                                content = getDummyContent(currentBook.getTitle());
                            }

                            // Chia nội dung thành các trang
                            splitContentToPages(content);

                            // Setup UI
                            totalPages = pageContents.length;
                            seekBarPage.setMax(totalPages);
                            tvTotalPage.setText(String.valueOf(totalPages));

                            // Hiển thị trang hiện tại
                            if (currentPage > totalPages) currentPage = 1;
                            updatePageContent();

                            // Lưu lịch sử đọc
                            saveReadingHistory();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ReadBookActivity.this,
                                "Lỗi: " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void splitContentToPages(String fullContent) {
        // Chia content thành các đoạn ~500 từ/trang
        String[] words = fullContent.split("\\s+");
        int wordsPerPage = 500;
        int numPages = (int) Math.ceil(words.length / (double) wordsPerPage);

        if (numPages == 0) numPages = 1;
        pageContents = new String[numPages];

        for (int i = 0; i < numPages; i++) {
            int start = i * wordsPerPage;
            int end = Math.min(start + wordsPerPage, words.length);

            StringBuilder pageContent = new StringBuilder();
            for (int j = start; j < end; j++) {
                pageContent.append(words[j]).append(" ");
            }
            pageContents[i] = pageContent.toString().trim();
        }
    }

    private void updatePageContent() {
        if (pageContents != null && currentPage > 0 && currentPage <= pageContents.length) {
            tvBookContent.setText(pageContents[currentPage - 1]);
            tvCurrentPage.setText(String.valueOf(currentPage));
            tvProgress.setText("Trang " + currentPage + "/" + totalPages);
            seekBarPage.setProgress(currentPage);
        }
    }

    private void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            updatePageContent();
            scrollToTop();
            saveReadingProgress();
        } else {
            Toast.makeText(this, "Đây là trang đầu tiên", Toast.LENGTH_SHORT).show();
        }
    }

    private void nextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            updatePageContent();
            scrollToTop();
            saveReadingProgress();
        } else {
            Toast.makeText(this, "Đã đến trang cuối cùng", Toast.LENGTH_SHORT).show();
        }
    }

    private void scrollToTop() {
        scrollView.post(() -> scrollView.smoothScrollTo(0, 0));
    }

    private void saveReadingHistory() {
        if (currentBook == null) return;

        // Tính progress dựa trên trang hiện tại
        float progress = totalPages > 0 ?
                ((float) currentPage / totalPages) * 100 : 0;

        ReadingHistory history = new ReadingHistory(
                currentBook.getId(),
                userId,
                currentBook.getTitle(),
                currentBook.getAuthor(),
                currentBook.getCoverUrl(),
                currentBook.getCategory(),
                (int) currentBook.getPages()
        );

        history.setLastReadTime(System.currentTimeMillis());
        history.setCurrentPage(currentPage);
        history.setProgress(progress);

        // Lưu vào Firebase
        databaseReference.child("reading_history")
                .child(userId)
                .child(bookId)
                .setValue(history);
    }

    private void saveReadingProgress() {
        if (currentBook == null) return;

        // Chỉ update currentPage và lastReadTime
        databaseReference.child("reading_history")
                .child(userId)
                .child(bookId)
                .child("currentPage")
                .setValue(currentPage);

        databaseReference.child("reading_history")
                .child(userId)
                .child(bookId)
                .child("lastReadTime")
                .setValue(System.currentTimeMillis());

        float progress = totalPages > 0 ?
                ((float) currentPage / totalPages) * 100 : 0;

        databaseReference.child("reading_history")
                .child(userId)
                .child(bookId)
                .child("progress")
                .setValue(progress);
    }

    private String getDummyContent(String title) {
        return "ĐẮC NHÂN TÂM\n" +
                "CHƯƠNG 1: NHỮNG KỸ THUẬT CÓ BẢN CỦA SỰ GIAO TIẾP\n\n" +

                "1. Đừng chỉ trích, khiển trách hay than phiền\n\n" +

                "Khi bạn phê phán người khác, bạn sẽ rất đau lòng khi biết rằng phê phán " +
                "thường không có ích gì cả, bởi vì nó khiến người khác phải tự vệ và thường " +
                "cố biện minh cho chính mình. Phê phán còn nguy hiểm vì nó làm tổn thương " +
                "lòng tự trọng của người khác, làm tổn hại đến cảm giác quan trọng của họ, " +
                "và gây nên sự oán giận.\n\n" +

                "Như B. F. Skinner, nhà tâm lý học nổi tiếng thế giới đã chứng minh qua nhiều " +
                "thí nghiệm của mình, con vật được khen thưởng vì làm điều tốt sẽ học nhanh hơn " +
                "và ghi nhớ lâu hơn so với con vật bị phạt vì làm sai. Các nghiên cứu sau này " +
                "đã chứng minh điều này cũng đúng với con người. Bằng cách phê phán, chúng ta " +
                "không tạo ra bất cứ thay đổi lâu dài nào và thường gây ra sự oán giận.\n\n" +

                "Ví dụ về Abraham Lincoln, một trong những nhà lãnh đạo vĩ đại nhất lịch sử " +
                "Hoa Kỳ. Lincoln từng nói: 'Đừng chỉ trích họ; họ chính xác như chúng ta sẽ " +
                "là trong hoàn cảnh tương tự.' Lincoln đã học được bài học đắt giá này khi " +
                "còn trẻ.\n\n" +

                "Thay vì chỉ trích, hãy cố gắng hiểu họ. Hãy cố gắng tìm hiểu tại sao họ lại " +
                "làm như vậy. Điều đó sẽ nhiều lợi ích và thú vị hơn nhiều so với chỉ trích; " +
                "và nó tạo ra sự cảm thông, lòng khoan dung và sự tử tế.\n\n" +

                "Nguyên tắc 1: Đừng chỉ trích, khiển trách hay than phiền.\n\n" +

                "---\n\n" +

                "2. Thể hiện sự đánh giá cao một cách chân thành\n\n" +

                "Có một điều mà tất cả chúng ta đều khao khát - và điều đó hầu như không thể " +
                "đạt được - đó là sự đánh giá cao chân thành từ người khác. Charles Schwab nói: " +
                "'Tôi coi khả năng khơi dậy sự nhiệt tình trong con người là tài sản quý giá " +
                "nhất tôi có. Và cách để phát triển điều tốt nhất trong con người là qua sự " +
                "đánh giá cao và khuyến khích.'\n\n" +

                "Không có gì khác mà chúng ta khao khát nhiều hơn là được người khác đánh giá " +
                "cao. Và không có gì nhanh chóng hơn để giết chết tham vọng của một người so " +
                "với sự chỉ trích từ những người cấp trên.\n\n" +

                "Sự khác biệt giữa sự đánh giá cao và xu nịnh là gì? Một cái là chân thành còn " +
                "một cái không. Một cái xuất phát từ trái tim; cái kia xuất phát từ môi. Một " +
                "cái là vị tha; cái kia là ích kỷ. Một cái được mọi người ngưỡng mộ; cái kia " +
                "bị mọi người khinh thường.\n\n" +

                "Hãy tìm những điều tốt trong người khác và ca ngợi họ. Hãy chân thành, hãy " +
                "nhiệt tình. Đừng sợ thể hiện sự đánh giá cao của bạn. Mọi người khao khát " +
                "được đánh giá cao và ghi nhớ.\n\n" +

                "Nguyên tắc 2: Thể hiện sự đánh giá cao một cách chân thành và chân thực.\n\n" +

                "(Đây là nội dung mẫu. Trong phiên bản thực, nội dung sẽ được load từ Firebase.)";
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Auto-save khi thoát
        if (currentBook != null) {
            saveReadingProgress();
        }
    }
}