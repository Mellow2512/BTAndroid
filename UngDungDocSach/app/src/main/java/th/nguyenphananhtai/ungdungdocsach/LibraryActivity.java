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

import com.google.android.material.chip.ChipGroup;
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
    private ChipGroup chipGroup; // Dùng ChipGroup

    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private String currentCategory = "Tất cả";

    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        // Firebase & User
        databaseReference = FirebaseDatabase.getInstance().getReference();
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = prefs.getString("userId", "guest_" + System.currentTimeMillis());

        initViews();
        setupRecyclerView();

        // Mặc định load tất cả
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
        chipGroup = findViewById(R.id.chipGroup);
    }

    private void setupRecyclerView() {
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, bookList, this);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBooks.setAdapter(bookAdapter);
    }

    // --- LOGIC LOAD SÁCH & ĐỒNG BỘ TIM ĐỎ ---
    private void loadBooksFromFirebase() {
        // Bước 1: Lấy danh sách Sách
        databaseReference.child("books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot bookSnapshot) {
                List<Book> tempList = new ArrayList<>();
                for (DataSnapshot snapshot : bookSnapshot.getChildren()) {
                    try {
                        Book book = snapshot.getValue(Book.class);
                        if (book != null) tempList.add(book);
                    } catch (Exception e) { e.printStackTrace(); }
                }
                // Bước 2: Đồng bộ với Favorites
                syncFavoritesAndLoad(tempList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadBooksByCategory(String category) {
        if (category.equals("Tất cả")) {
            loadBooksFromFirebase();
            return;
        }
        databaseReference.child("books").orderByChild("category").equalTo(category)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot bookSnapshot) {
                        List<Book> tempList = new ArrayList<>();
                        for (DataSnapshot snapshot : bookSnapshot.getChildren()) {
                            try {
                                Book book = snapshot.getValue(Book.class);
                                if (book != null) tempList.add(book);
                            } catch (Exception e) { e.printStackTrace(); }
                        }
                        syncFavoritesAndLoad(tempList);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }

    // Hàm phụ trợ: Kiểm tra sách nào đã like thì setFavorite(true)
    private void syncFavoritesAndLoad(List<Book> booksToLoad) {
        databaseReference.child("favorites").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot favSnapshot) {
                        for (Book book : booksToLoad) {
                            if (favSnapshot.hasChild(book.getId())) {
                                book.setFavorite(true);
                            } else {
                                book.setFavorite(false);
                            }
                        }
                        // Cập nhật UI
                        bookList.clear();
                        bookList.addAll(booksToLoad);
                        bookAdapter.updateBookList(new ArrayList<>(bookList));
                        updateBookCount();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }
    // ---------------------------------------------

    private void setupSearch() {
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bookAdapter.filter(s.toString());
                updateBookCount();
                btnClearSearch.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        btnClearSearch.setOnClickListener(v -> searchView.setText(""));
    }

    private void setupFilterChips() {
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chipAll) currentCategory = "Tất cả";
            else if (checkedId == R.id.chipNovel) currentCategory = "Tiểu thuyết";
            else if (checkedId == R.id.chipScience) currentCategory = "Khoa học";
            else if (checkedId == R.id.chipHistory) currentCategory = "Lịch sử";
            else if (checkedId == R.id.chipSelfHelp) currentCategory = "Kỹ năng sống";
            else currentCategory = "Tất cả";

            loadBooksByCategory(currentCategory);
        });
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnFilter.setOnClickListener(v -> Toast.makeText(this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show());
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
        DatabaseReference favRef = databaseReference.child("favorites").child(userId).child(book.getId());
        favRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    favRef.removeValue().addOnSuccessListener(aVoid ->
                            Toast.makeText(LibraryActivity.this, "Đã bỏ thích", Toast.LENGTH_SHORT).show());
                } else {
                    favRef.setValue(book).addOnSuccessListener(aVoid ->
                            Toast.makeText(LibraryActivity.this, "Đã thích sách này", Toast.LENGTH_SHORT).show());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}