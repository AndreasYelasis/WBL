package com.andreas.wbl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

//Base class for an Adapter.Adapters provide a binding from an app-specific data set to views that are displayed within a RecyclerView.
public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {


    private Context context;
    private List<Report> reports;

    public ReportsAdapter(Context context, List<Report> reports) {
        this.context = context;
        this.reports = reports;
    }
    //A ViewHolder describes an item view and metadata about its place within the RecyclerView.
//RecyclerView.Adapter implementations should subclass ViewHolder and add fields for caching potentially expensive findViewById(int) results.
//While RecyclerView.LayoutParams belong to the RecyclerView.LayoutManager, ViewHolders belong to the adapter.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_card,parent,false);

        return new ViewHolder(itemView);
    }
    //Called by RecyclerView to display the data at the specified position. This method should update the contents of the itemView to reflect the item at the given position.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Glide.with(context).load(reports.get(position).getPhoto()).into(holder.imageView);
        holder.reportId.setText((reports.get(position).getReportId())+"");
        holder.customerName.setText(reports.get(position).getCustomerName());
        holder.area.setText(reports.get(position).getArea());
        holder.address.setText(reports.get(position).getAddress());
        holder.timestamp.setText(reports.get(position).getTimestampTaken());
    }


    //Counts the amount of reports
    @Override
    public int getItemCount() {
        return reports.size();
    }

    //A ViewHolder describes an item view and metadata about its place within the RecyclerView.
    //RecyclerView.Adapter implementations should subclass ViewHolder and add fields for caching potentially expensive findViewById(int) results.
    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView timestamp;
        public ImageView imageView;
        public TextView reportId;
        public TextView customerName;
        public TextView area;
        public TextView address;
        public TextView menuButton;
        //Are the fields that are sent to Card to be displayed
        public ViewHolder(View itemView) {
            super(itemView);
            timestamp = (TextView) itemView.findViewById(R.id.taken_timestamp);
            reportId = (TextView) itemView.findViewById(R.id.reportID);
            customerName = (TextView) itemView.findViewById(R.id.reportcustomername);
            area = (TextView) itemView.findViewById(R.id.reportarea);
            address = (TextView) itemView.findViewById(R.id.reportaddress);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            menuButton = (TextView) itemView.findViewById(R.id.textViewOptions);
            menuButton.setOnClickListener(this);
        }

        //When adapter view holder clicked it will called showPopupMenu function
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            showPopupMenu(v,position);
        }
    }
    //Declares the popupMenu that will be displayed
    private void showPopupMenu(View view, int poaition) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_report, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuClickListener(poaition));
        popup.show();
    }


    //MenuClickListener called by showPopupMenu
    class MenuClickListener implements PopupMenu.OnMenuItemClickListener {
        //integer pos takes the position of each record.
        Integer pos;
        public MenuClickListener(int pos) {
            this.pos=pos;
        }
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_complete:
                    Toast.makeText(context, reports.get(pos).getReportId()+" history", Toast.LENGTH_SHORT).show();
                    Intent completeIntent = new Intent(context, InfoActivity.class);
                    completeIntent.putExtra("thesi",reports.get(pos).getReportId());
                    context.startActivity(completeIntent);
                    return true;
                case R.id.action_showall:
                    Toast.makeText(context, reports.get(pos).getReportId()+" update", Toast.LENGTH_SHORT).show();
                    Intent showallIntent = new Intent(context,ModificationActivity.class);
                    Bundle info = new Bundle();
                    info.putInt("report_id",reports.get(pos).getReportId());
                    info.putString("area",reports.get(pos).getArea());
                    info.putString("address",reports.get(pos).getAddress());
                    info.putInt("zip_code",reports.get(pos).getZipCode());
                    info.putString("customer_name",reports.get(pos).getCustomerName());
                    info.putString("timestamp_taken",reports.get(pos).getTimestampTaken());
                    info.putInt("phone",reports.get(pos).getPhone());
                    info.putString("synergio",reports.get(pos).getSynergio());
                    showallIntent.putExtras(info);
                    context.startActivity(showallIntent);
                    return true;
                case R.id.action_map:
                    Toast.makeText(context, "Delete "+reports.get(pos).getReportId(), Toast.LENGTH_SHORT).show();


                    Intent mapIntent = new Intent(context, MapsActivity.class);
                    mapIntent.putExtra("odos",reports.get(pos).getAddress()+" "+reports.get(pos).getArea());
                    context.startActivity(mapIntent);
                    return true;
                default:
            }
            return false;
        }

    }

}
