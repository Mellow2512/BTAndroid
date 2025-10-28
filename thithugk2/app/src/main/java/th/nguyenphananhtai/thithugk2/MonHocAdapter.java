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

public class MonHocAdapter extends RecyclerView.Adapter<MonHocAdapter.ItemMonHoc> {
    Context context;
    ArrayList<DSMon> arrMonHoc;

    public MonHocAdapter(Context context, ArrayList<DSMon> arrMonHoc) {
        this.context = context;
        this.arrMonHoc = arrMonHoc;
    }

    @NonNull
    @Override
    public ItemMonHoc onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cn3_item, parent, false);
        return new ItemMonHoc(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMonHoc holder, int position) {
        DSMon monHoc = arrMonHoc.get(position);
        holder.tvItemCN3.setText(monHoc.getTenMon());
    }

    @Override
    public int getItemCount() {
        return arrMonHoc.size();
    }

    class ItemMonHoc extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvItemCN3;

        public ItemMonHoc(@NonNull View itemView) {
            super(itemView);
            tvItemCN3 = itemView.findViewById(R.id.tvItemCN3);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int positionClicked = getAbsoluteAdapterPosition();
            DSMon monHoc = arrMonHoc.get(positionClicked);

            // Tạo Intent để chuyển sang Activity chi tiết
            Intent intent = new Intent(context, ChiTietMonHocActivity.class);

            // Đưa dữ liệu vào Bundle
            intent.putExtra("tenMon", monHoc.getTenMon());
            intent.putExtra("maMon", monHoc.getMaMon());
            intent.putExtra("soTinChi", monHoc.getSoTinChi());
            intent.putExtra("giangVien", monHoc.getGiangVien());
            intent.putExtra("moTa", monHoc.getMoTa());
            intent.putExtra("noiDung", monHoc.getNoiDung());

            // Chuyển sang Activity chi tiết
            context.startActivity(intent);
        }
    }
}