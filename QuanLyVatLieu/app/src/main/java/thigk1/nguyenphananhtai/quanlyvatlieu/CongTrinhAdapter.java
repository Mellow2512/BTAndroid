package thigk1.nguyenphananhtai.quanlyvatlieu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CongTrinhAdapter extends RecyclerView.Adapter<CongTrinhAdapter.ItemCongTrinh> {
    Context context;
    ArrayList<DSCongTrinh> arrCongTrinh;

    public CongTrinhAdapter(Context context, ArrayList<DSCongTrinh> arrCongTrinh) {
        this.context = context;
        this.arrCongTrinh = arrCongTrinh;
    }

    @Override
    public int getItemCount() {
        return arrCongTrinh.size();
    }

    @NonNull
    @Override
    public ItemCongTrinh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.congtrinh_item, parent, false);
        return new ItemCongTrinh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCongTrinh holder, int position) {
        DSCongTrinh congTrinh = arrCongTrinh.get(position);
        holder.tvTitle.setText(congTrinh.getTenCongTrinh());
        holder.tvTime.setText(congTrinh.getNgayKhoiCong());
    }

    class ItemCongTrinh extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvTime;

        public ItemCongTrinh(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int positionClicked = getAbsoluteAdapterPosition();
            DSCongTrinh congTrinh = arrCongTrinh.get(positionClicked);

            // Tạo Intent để chuyển sang Activity chi tiết công trình
            Intent intent = new Intent(context, ChiTietCongTrinhActivity.class);

            // Đưa dữ liệu vào Intent
            intent.putExtra("tenCongTrinh", congTrinh.getTenCongTrinh());
            intent.putExtra("ngayKhoiCong", congTrinh.getNgayKhoiCong());
            intent.putExtra("diaDiem", congTrinh.getDiaDiem());
            intent.putExtra("donViThiCong", congTrinh.getDonViThiCong());
            intent.putExtra("chuDauTu", congTrinh.getChuDauTu());
            intent.putExtra("moTa", congTrinh.getMoTa());
            intent.putExtra("quyMo", congTrinh.getQuyMo());
            intent.putExtra("loiIch", congTrinh.getLoiIch());

            // Chuyển sang Activity chi tiết
            context.startActivity(intent);
        }
    }
}
