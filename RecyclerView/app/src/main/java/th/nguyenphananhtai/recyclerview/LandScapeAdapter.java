package th.nguyenphananhtai.recyclerview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class LandScapeAdapter extends RecyclerView.Adapter<LandScapeAdapter.ItemLandHolder>{
    Context context;
    ArrayList<LandScape> lstData;

    public LandScapeAdapter(Context context, ArrayList<LandScape> lstData) {
        this.context = context;
        this.lstData = lstData;
    }

    @NonNull
    @Override
    public ItemLandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater cai_bom = LayoutInflater.from(context);
        View vItem = cai_bom.inflate(R.layout.item_land,parent,false);
        ItemLandHolder viewHolderCreated = new ItemLandHolder(vItem);
        return viewHolderCreated;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemLandHolder holder, int position) {
        LandScape landScapeHienThi = lstData.get(position);
        String caption = landScapeHienThi.getlandCaption();
        String tenFileAnh = landScapeHienThi.getLandImageFileName();
        holder.tvCapTion.setText(caption);
        String packageName = holder.itemView.getContext().getPackageName();
        int imageID = holder.itemView.getResources().getIdentifier(tenFileAnh,"mipmap",packageName);
        holder.ivLand.setImageResource(imageID);
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public class ItemLandHolder extends RecyclerView.ViewHolder{
        TextView tvCapTion;
        ImageView ivLand;
        public ItemLandHolder(@NonNull View itemView) {
            super(itemView);
            tvCapTion = itemView.findViewById(R.id.textViewCaption);
            ivLand = itemView.findViewById(R.id.imageViewLand);
        }
    }
}
