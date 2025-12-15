package th.nguyenphananhtai.ungdungdocsach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ReadingHistoryAdapter extends RecyclerView.Adapter<ReadingHistoryAdapter.HistoryViewHolder> {

    private Context context;
    private List<ReadingHistory> historyList;
    private List<ReadingHistory> historyListFull;
    private OnHistoryClickListener listener;

    public interface OnHistoryClickListener {
        void onHistoryClick(ReadingHistory history);
        void onContinueClick(ReadingHistory history);
    }

    public ReadingHistoryAdapter(Context context, List<ReadingHistory> historyList, OnHistoryClickListener listener) {
        this.context = context;
        this.historyList = historyList;
        this.historyListFull = new ArrayList<>(historyList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reading_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        ReadingHistory history = historyList.get(position);

        holder.tvTitle.setText(history.getBookTitle());
        holder.tvAuthor.setText(history.getBookAuthor());
        holder.tvProgress.setText(history.getProgressText());
        holder.tvLastRead.setText(history.getTimeAgo());
        holder.progressBar.setProgress((int) history.getProgress());

        // Load cover image
        Glide.with(context)
                .load(history.getBookCoverUrl())
                .placeholder(R.drawable.book_placeholder)
                .error(R.drawable.book_placeholder)
                .centerCrop()
                .into(holder.imgCover);

        // Click listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onHistoryClick(history);
            }
        });

        holder.btnContinue.setOnClickListener(v -> {
            if (listener != null) {
                listener.onContinueClick(history);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    // Filter by status
    public void filterByStatus(String status) {
        historyList.clear();
        if (status.equals("Tất cả")) {
            historyList.addAll(historyListFull);
        } else if (status.equals("Đang đọc")) {
            for (ReadingHistory history : historyListFull) {
                if (history.getProgress() > 0 && history.getProgress() < 100) {
                    historyList.add(history);
                }
            }
        } else if (status.equals("Hoàn thành")) {
            for (ReadingHistory history : historyListFull) {
                if (history.getProgress() >= 100) {
                    historyList.add(history);
                }
            }
        }
        notifyDataSetChanged();
    }

    // Update list
    public void updateHistoryList(List<ReadingHistory> newList) {
        historyListFull.clear();
        historyListFull.addAll(newList);
        historyList.clear();
        historyList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover;
        TextView tvTitle, tvAuthor, tvProgress, tvLastRead, btnContinue;
        ProgressBar progressBar;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.imgBookCover);
            tvTitle = itemView.findViewById(R.id.tvBookTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvProgress = itemView.findViewById(R.id.tvProgress);
            tvLastRead = itemView.findViewById(R.id.tvLastRead);
            btnContinue = itemView.findViewById(R.id.btnContinue);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}