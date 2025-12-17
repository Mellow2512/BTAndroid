package th.nguyenphananhtai.ungdungdocsach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private List<Book> bookList;
    private List<Book> bookListFull; // Backup for search
    private OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
        void onFavoriteClick(Book book, int position);
    }

    public BookAdapter(Context context, List<Book> bookList, OnBookClickListener listener) {
        this.context = context;
        this.bookList = bookList;
        this.bookListFull = new ArrayList<>(bookList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvCategory.setText(book.getCategory());
        holder.tvRating.setText(String.format("%.1f", book.getRating()));
        holder.tvPages.setText(String.valueOf(book.getPages()));

        // Load ảnh từ URL bằng Glide thay vì setImageResource
        Glide.with(context)
                .load(book.getCoverUrl())
                .placeholder(R.drawable.book_placeholder)
                .error(R.drawable.book_placeholder)
                .centerCrop()
                .into(holder.imgCover);

        // Favorite icon - mặc định là chưa yêu thích
        // (Sẽ check thật từ Firebase trong activity)
        holder.btnFavorite.setImageResource(R.drawable.ic_favorite_border);

        // Click listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBookClick(book);
            }
        });

        holder.btnFavorite.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFavoriteClick(book, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    // Filter by search query
    public void filter(String query) {
        bookList.clear();
        if (query.isEmpty()) {
            bookList.addAll(bookListFull);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Book book : bookListFull) {
                if (book.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                        book.getAuthor().toLowerCase().contains(lowerCaseQuery)) {
                    bookList.add(book);
                }
            }
        }
        notifyDataSetChanged();
    }

    // Filter by category
    public void filterByCategory(String category) {
        bookList.clear();
        if (category.equals("Tất cả")) {
            bookList.addAll(bookListFull);
        } else {
            for (Book book : bookListFull) {
                if (book.getCategory().equals(category)) {
                    bookList.add(book);
                }
            }
        }
        notifyDataSetChanged();
    }

    // Update full list (for refresh)
    public void updateBookList(List<Book> newList) {
        bookListFull.clear();
        bookListFull.addAll(newList);
        bookList.clear();
        bookList.addAll(newList);
        notifyDataSetChanged();
    }

    // Set favorite icon cho một item cụ thể
    public void setBookFavorite(int position, boolean isFavorite) {
        if (position >= 0 && position < bookList.size()) {
            notifyItemChanged(position);
        }
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover, btnFavorite;
        TextView tvTitle, tvAuthor, tvCategory, tvRating, tvPages;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.imgBookCover);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            tvTitle = itemView.findViewById(R.id.tvBookTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvPages = itemView.findViewById(R.id.tvPages);
        }
    }
}