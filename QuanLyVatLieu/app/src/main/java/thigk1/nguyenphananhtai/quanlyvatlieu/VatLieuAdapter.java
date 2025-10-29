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

public class VatLieuAdapter extends RecyclerView.Adapter<VatLieuAdapter.ItemVatLieu> {
    Context context;
    ArrayList<DSVatLieu> arrVatLieu;

    public VatLieuAdapter(Context context, ArrayList<DSVatLieu> arrVatLieu) {
        this.context = context;
        this.arrVatLieu = arrVatLieu;
    }

    @NonNull
    @Override
    public ItemVatLieu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.vatlieu_item, parent, false);
        return new ItemVatLieu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVatLieu holder, int position) {
        DSVatLieu vatLieu = arrVatLieu.get(position);
        holder.tvItemCN3.setText(vatLieu.getTenVatLieu());
    }

    @Override
    public int getItemCount() {
        return arrVatLieu.size();
    }

    class ItemVatLieu extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvItemCN3;

        public ItemVatLieu(@NonNull View itemView) {
            super(itemView);
            tvItemCN3 = itemView.findViewById(R.id.tvItemCN3);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int positionClicked = getAbsoluteAdapterPosition();
            DSVatLieu vatLieu = arrVatLieu.get(positionClicked);

            // Tạo Intent để chuyển sang Activity chi tiết vật liệu
            Intent intent = new Intent(context, ChiTietVatLieuActivity.class);

            // Đưa dữ liệu vào Intent
            intent.putExtra("tenVatLieu", vatLieu.getTenVatLieu());
            intent.putExtra("maVatLieu", vatLieu.getMaVatLieu());
            intent.putExtra("loaiVatLieu", vatLieu.getLoaiVatLieu());
            intent.putExtra("nhaCungCap", vatLieu.getNhaCungCap());
            intent.putExtra("moTa", vatLieu.getMoTa());
            intent.putExtra("thongTinChiTiet", vatLieu.getThongTinChiTiet());

            // Mở Activity chi tiết
            context.startActivity(intent);
        }
    }
}
