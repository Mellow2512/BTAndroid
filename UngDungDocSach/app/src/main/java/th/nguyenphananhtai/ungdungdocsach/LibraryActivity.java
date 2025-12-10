package th.nguyenphananhtai.ungdungdocsach;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity implements BookAdapter.OnBookClickListener {

    // Views
    private ImageView btnBack, btnFilter, btnClearSearch;
    private EditText searchView;
    private RecyclerView recyclerViewBooks;
    private TextView tvBookCount;
    private LinearLayout emptyState;

    // Chips
    private Chip chipAll, chipNovel, chipScience, chipHistory, chipSelfHelp;

    // Data
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private String currentCategory = "Tất cả";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        // Initialize views
        initViews();

        // Setup books data
        setupBooksData();

        // Setup RecyclerView
        setupRecyclerView();

        // Setup search functionality
        setupSearch();

        // Setup filter chips
        setupFilterChips();

        // Setup click listeners
        setupClickListeners();

        // Update book count
        updateBookCount();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnFilter = findViewById(R.id.btnFilter);
        btnClearSearch = findViewById(R.id.btnClearSearch);
        searchView = findViewById(R.id.searchView);
        recyclerViewBooks = findViewById(R.id.recyclerViewBooks);
        tvBookCount = findViewById(R.id.tvBookCount);
        emptyState = findViewById(R.id.emptyState);

        // Chips
        chipAll = findViewById(R.id.chipAll);
        chipNovel = findViewById(R.id.chipNovel);
        chipScience = findViewById(R.id.chipScience);
        chipHistory = findViewById(R.id.chipHistory);
        chipSelfHelp = findViewById(R.id.chipSelfHelp);
    }

    private void setupBooksData() {
        bookList = new ArrayList<>();

        // Thêm sách mẫu (Thay bằng dữ liệu thật từ database/API)
        bookList.add(new Book(1, "Đắc Nhân Tâm", "Dale Carnegie", "Kỹ năng sống",
                R.drawable.book1, 4.8f, 320, "Cuốn sách kinh điển về nghệ thuật giao tiếp"));

        bookList.add(new Book(2, "Sapiens: Lược Sử Loài Người", "Yuval Noah Harari", "Lịch sử",
                R.drawable.book2, 4.7f, 512, "Cuộc hành trình từ thời nguyên thủy đến hiện đại"));

        bookList.add(new Book(3, "Nhà Giả Kim", "Paulo Coelho", "Tiểu thuyết",
                R.drawable.book3, 4.6f, 240, "Hành trình tìm kiếm kho báu và chính mình"));

        bookList.add(new Book(4, "Tư Duy Nhanh Và Chậm", "Daniel Kahneman", "Khoa học",
                R.drawable.book1, 4.5f, 512, "Khám phá hai hệ thống tư duy của con người"));

        bookList.add(new Book(5, "1984", "George Orwell", "Tiểu thuyết",
                R.drawable.book2, 4.9f, 368, "Tác phẩm kinh điển về chủ nghĩa toàn trị"));

        bookList.add(new Book(6, "Nghệ Thuật Bán Hàng", "Dale Carnegie", "Kỹ năng sống",
                R.drawable.book3, 4.4f, 288, "Bí quyết thành công trong kinh doanh"));

        bookList.add(new Book(7, "Vũ Trụ Trong Vỏ Hạt Dẻ", "Stephen Hawking", "Khoa học",
                R.drawable.book1, 4.6f, 256, "Khám phá những bí ẩn của vũ trụ"));

        bookList.add(new Book(8, "Lịch Sử Thế Giới", "John Morris Roberts", "Lịch sử",
                R.drawable.book2, 4.3f, 640, "Tổng quan lịch sử nhân loại qua các thời kỳ"));
    }

    private void setupRecyclerView() {
        bookAdapter = new BookAdapter(this, bookList, this);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBooks.setAdapter(bookAdapter);
    }

    private void setupSearch() {
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bookAdapter.filter(s.toString());
                updateBookCount();

                // Show/hide clear button
                if (s.length() > 0) {
                    btnClearSearch.setVisibility(View.VISIBLE);
                } else {
                    btnClearSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnClearSearch.setOnClickListener(v -> {
            searchView.setText("");
            btnClearSearch.setVisibility(View.GONE);
        });
    }

    private void setupFilterChips() {
        chipAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentCategory = "Tất cả";
                bookAdapter.filterByCategory(currentCategory);
                updateBookCount();
            }
        });

        chipNovel.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentCategory = "Tiểu thuyết";
                bookAdapter.filterByCategory(currentCategory);
                updateBookCount();
            }
        });

        chipScience.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentCategory = "Khoa học";
                bookAdapter.filterByCategory(currentCategory);
                updateBookCount();
            }
        });

        chipHistory.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentCategory = "Lịch sử";
                bookAdapter.filterByCategory(currentCategory);
                updateBookCount();
            }
        });

        chipSelfHelp.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentCategory = "Kỹ năng sống";
                bookAdapter.filterByCategory(currentCategory);
                updateBookCount();
            }
        });
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnFilter.setOnClickListener(v -> {
            // TODO: Implement advanced filter dialog
            Toast.makeText(this, "Chức năng lọc nâng cao", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateBookCount() {
        int count = bookAdapter.getItemCount();
        tvBookCount.setText("Tìm thấy " + count + " cuốn sách");

        // Show/hide empty state
        if (count == 0) {
            emptyState.setVisibility(View.VISIBLE);
            recyclerViewBooks.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            recyclerViewBooks.setVisibility(View.VISIBLE);
        }
    }

    // BookAdapter.OnBookClickListener implementations
    @Override
    public void onBookClick(Book book) {
        // TODO: Navigate to BookDetailActivity
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("bookId", book.getId());
        intent.putExtra("bookTitle", book.getTitle());
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(Book book, int position) {
        if (book.isFavorite()) {
            Toast.makeText(this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
            // TODO: Save to database/SharedPreferences
        } else {
            Toast.makeText(this, "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
            // TODO: Remove from database/SharedPreferences
        }
    }
}