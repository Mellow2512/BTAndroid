package th.nguyenphananhtai.ungdungdocsach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity implements BookAdapter.OnBookClickListener {

    private ImageView btnBack, btnFilter, btnClearSearch;
    private EditText searchView;
    private RecyclerView recyclerViewBooks;
    private TextView tvBookCount;
    private LinearLayout emptyState;

    private Chip chipAll, chipNovel, chipScience, chipHistory, chipSelfHelp;

    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private String currentCategory = "Tất cả";

    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Get user ID
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = prefs.getString("userId", "guest_" + System.currentTimeMillis());

        initViews();
        setupRecyclerView();
        loadBooksFromFirebase();
        setupSearch();
        setupFilterChips();
        setupClickListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnFilter = findViewById(R.id.btnFilter);
        btnClearSearch = findViewById(R.id.btnClearSearch);
        searchView = findViewById(R.id.searchView);
        recyclerViewBooks = findViewById(R.id.recyclerViewBooks);
        tvBookCount = findViewById(R.id.tvBookCount);
        emptyState = findViewById(R.id.emptyState);

        chipAll = findViewById(R.id.chipAll);
        chipNovel = findViewById(R.id.chipNovel);
        chipScience = findViewById(R.id.chipScience);
        chipHistory = findViewById(R.id.chipHistory);
        chipSelfHelp = findViewById(R.id.chipSelfHelp);
    }

    private void setupRecyclerView() {
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, bookList, this);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBooks.setAdapter(bookAdapter);
    }

    private void loadBooksFromFirebase() {
        databaseReference.child("books")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        bookList.clear();
                        for (DataSnapshot bookSnapshot : snapshot.getChildren()) {
                            Book book = bookSnapshot.getValue(Book.class);
                            if (book != null) {
                                bookList.add(book);
                            }
                        }
                        bookAdapter.updateBookList(bookList);
                        updateBookCount();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LibraryActivity.this,
                                "Lỗi: " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadBooksByCategory(String category) {
        if (category.equals("Tất cả")) {
            loadBooksFromFirebase();
            return;
        }

        databaseReference.child("books")
                .orderByChild("category")
                .equalTo(category)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        bookList.clear();
                        for (DataSnapshot bookSnapshot : snapshot.getChildren()) {
                            Book book = bookSnapshot.getValue(Book.class);
                            if (book != null) {
                                bookList.add(book);
                            }
                        }
                        bookAdapter.updateBookList(bookList);
                        updateBookCount();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LibraryActivity.this,
                                "Lỗi: " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupSearch() {
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bookAdapter.filter(s.toString());
                updateBookCount();

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
                loadBooksByCategory(currentCategory);
            }
        });

        chipNovel.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentCategory = "Tiểu thuyết";
                loadBooksByCategory(currentCategory);
            }
        });

        chipScience.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentCategory = "Khoa học";
                loadBooksByCategory(currentCategory);
            }
        });

        chipHistory.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentCategory = "Lịch sử";
                loadBooksByCategory(currentCategory);
            }
        });

        chipSelfHelp.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentCategory = "Kỹ năng sống";
                loadBooksByCategory(currentCategory);
            }
        });
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnFilter.setOnClickListener(v -> {
            Toast.makeText(this, "Chức năng lọc nâng cao", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateBookCount() {
        int count = bookAdapter.getItemCount();
        tvBookCount.setText("Tìm thấy " + count + " cuốn sách");

        if (count == 0) {
            emptyState.setVisibility(View.VISIBLE);
            recyclerViewBooks.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            recyclerViewBooks.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBookClick(Book book) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("bookId", book.getId());
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(Book book, int position) {
        // Kiểm tra xem đã yêu thích chưa
        DatabaseReference favRef = databaseReference.child("favorites")
                .child(userId)
                .child(book.getId());

        favRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Đã yêu thích -> Xóa
                    favRef.removeValue()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(LibraryActivity.this,
                                        "Đã xóa khỏi yêu thích",
                                        Toast.LENGTH_SHORT).show();
                                bookAdapter.setBookFavorite(position, false);
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(LibraryActivity.this,
                                        "Lỗi: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            });
                } else {
                    // Chưa yêu thích -> Thêm
                    favRef.setValue(book)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(LibraryActivity.this,
                                        "Đã thêm vào yêu thích",
                                        Toast.LENGTH_SHORT).show();
                                bookAdapter.setBookFavorite(position, true);
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(LibraryActivity.this,
                                        "Lỗi: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LibraryActivity.this,
                        "Lỗi: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}