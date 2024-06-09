package com.example.androidapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;
import com.openapi.resolverApp.model.ComplainOverview;

import java.util.List;

public class NewComplainAdapter extends RecyclerView.Adapter<NewComplainAdapter.ViewHolder> {
    private final List<ComplainOverview> list;
    private final Context context;
    public static OnItemClickListener listener;

    public NewComplainAdapter(List<ComplainOverview> items, Context context) {
        this.list = items;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onViewClick(int position);
    }

    public static void setOnItemClickListener(OnItemClickListener clickListener) {
        listener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_order_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list != null && position < list.size()) {
            ComplainOverview singleItem = list.get(position);
            holder.complainerName.setText(singleItem.getComplainerName());
            holder.complaintSeverity.setText(singleItem.getSeverity().toString());
            holder.complaintLocation.setText(singleItem.getLocation());
            holder.complaintDescription.setText(singleItem.getComplain());
            holder.complaintDate.setText(singleItem.getDateTime());
            holder.tagsList.setText(singleItem.getTags().toString());
            holder.mainCard.setOnClickListener(v -> {
                listener.onViewClick(position);
            });

        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView complaintLocation, complaintDate, complaintDescription, complaintSeverity, complainerName;
        TextView tagsList;
        CardView mainCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            complaintLocation = itemView.findViewById(R.id.complaint_location);
            complainerName = itemView.findViewById(R.id.complainer_name);
            complaintDate = itemView.findViewById(R.id.complaint_date);
            complaintDescription = itemView.findViewById(R.id.complaint_description);
            complaintSeverity = itemView.findViewById(R.id.complaint_severity);
            tagsList = itemView.findViewById(R.id.complaint_tags);
            mainCard = itemView.findViewById(R.id.new_complaint_cardView);
        }
    }
}
