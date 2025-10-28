package th.nguyenphananhtai.thithugk2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HoatDongAdapter extends RecyclerView.Adapter<HoatDongAdapter.ItemHoatDong> {
    Context context;
    ArrayList<DSHoatDong> arrHoatDong;

    public HoatDongAdapter(Context context, ArrayList<DSHoatDong> arrHoatDong) {
        this.context = context;
        this.arrHoatDong = arrHoatDong;
    }

    public int getItemCount() {
        return arrHoatDong.size();
    }

    public ItemHoatDong onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cn4_item, parent, false);
        return new ItemHoatDong(view);
    }

    public void onBindViewHolder(@NonNull ItemHoatDong holder, int position) {
        DSHoatDong hoatDong = arrHoatDong.get(position);
        holder.tvTitle.setText(hoatDong.getTenHoatDong());
        holder.tvTime.setText(hoatDong.getThoiGian());
    }

    class ItemHoatDong extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvTime;

        public ItemHoatDong(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int positionClicked = getAbsoluteAdapterPosition();
            DSHoatDong hoatDong = arrHoatDong.get(positionClicked);

            // Tạo Intent để chuyển sang Activity chi tiết
            Intent intent = new Intent(context, ChiTietHoatDongActivity.class);

            // Đưa dữ liệu vào Bundle
            intent.putExtra("tenHoatDong", hoatDong.getTenHoatDong());
            intent.putExtra("thoiGian", hoatDong.getThoiGian());
            intent.putExtra("diaDiem", hoatDong.getDiaDiem());
            intent.putExtra("donVi", hoatDong.getDonVi());
            intent.putExtra("doiTuong", hoatDong.getDoiTuong());
            intent.putExtra("moTa", hoatDong.getMoTa());
            intent.putExtra("noiDung", hoatDong.getNoiDung());
            intent.putExtra("loiIch", hoatDong.getLoiIch());

            // Chuyển sang Activity chi tiết
            context.startActivity(intent);
        }
    }
}