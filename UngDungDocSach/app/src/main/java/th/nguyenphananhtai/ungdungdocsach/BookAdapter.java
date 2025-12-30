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
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    // Khai báo 2 kiểu hiển thị
    private static final int VIEW_TYPE_LIST = 0;
    private static final int VIEW_TYPE_GRID = 1;

    private Context context;
    private List<Book> bookList;
    // QUAN TRỌNG: List dự phòng để phục vụ tìm kiếm
    private List<Book> bookListFull;

    private OnBookClickListener listener;

    // Biến lưu trạng thái hiện tại (Mặc định là false = List)
    private boolean isGridMode = false;

    public interface OnBookClickListener {
        void onBookClick(Book book);
        void onFavoriteClick(Book book, int position);
    }

    public BookAdapter(Context context, List<Book> bookList, OnBookClickListener listener) {
        this.context = context;
        this.bookList = bookList;
        // Tạo bản sao dữ liệu gốc
        this.bookListFull = new ArrayList<>(bookList);
        this.listener = listener;
    }

    // Hàm để Activity gọi khi muốn chuyển đổi chế độ
    public void setGridMode(boolean isGrid) {
        this.isGridMode = isGrid;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return isGridMode ? VIEW_TYPE_GRID : VIEW_TYPE_LIST;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_GRID) {
            view = LayoutInflater.from(context).inflate(R.layout.item_book_grid, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        }
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvRating.setText(String.format("%.1f", book.getRating()));

        // Các trường này có thể bị ẩn ở layout Grid, cần check null
        if (holder.tvCategory != null) holder.tvCategory.setText(book.getCategory());
        if (holder.tvPages != null) holder.tvPages.setText(String.valueOf(book.getPages()));

        // --- Glide Load Image ---
        GlideUrl glideUrl = new GlideUrl(book.getCoverUrl(), new LazyHeaders.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .addHeader("Referer", "https://tiki.vn/")
                .build());

        Glide.with(context)
                .load(glideUrl)
                .placeholder(R.drawable.book_placeholder)
                .error(R.drawable.book_placeholder)
                .centerCrop()
                .into(holder.imgCover);

        // --- Logic Tim ---
        if (book.isFavorite()) {
            holder.btnFavorite.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            holder.btnFavorite.setImageResource(R.drawable.ic_favorite_border);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onBookClick(book);
        });

        holder.btnFavorite.setOnClickListener(v -> {
            if (listener != null) {
                // Update UI ngay lập tức
                boolean newState = !book.isFavorite();
                book.setFavorite(newState);
                notifyItemChanged(position);

                listener.onFavoriteClick(book, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    // --- HÀM CẬP NHẬT LIST (Sửa lại để update cả listFull) ---
    public void updateBookList(List<Book> newList) {
        this.bookList = newList;
        // Cập nhật luôn list dự phòng
        this.bookListFull = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    // --- HÀM LỌC TÌM KIẾM (ĐÂY LÀ HÀM BẠN ĐANG THIẾU) ---
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

    public void setBookFavorite(int position, boolean isFavorite) {
        if (position >= 0 && position < bookList.size()) {
            bookList.get(position).setFavorite(isFavorite);
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
            tvCategory = itemView.findViewById(R.id.tvCategory); // Có thể null ở Grid
            tvRating = itemView.findViewById(R.id.tvRating);
            tvPages = itemView.findViewById(R.id.tvPages); // Có thể null ở Grid
        }
    }
}