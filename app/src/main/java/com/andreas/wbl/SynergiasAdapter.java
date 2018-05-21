package com.andreas.wbl;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

//Base class for an Adapter.Adapters provide a binding from an app-specific data set to views that are displayed within a RecyclerView.
public class SynergiasAdapter extends RecyclerView.Adapter<SynergiasAdapter.ViewHolder> {

    private Context context;
    private List<Synergia> synergias;

    public SynergiasAdapter(Context context, List<Synergia> synergias) {
        this.context = context;
        this.synergias = synergias;
    }
    //A ViewHolder describes an item view and metadata about its place within the RecyclerView.
//RecyclerView.Adapter implementations should subclass ViewHolder and add fields for caching potentially expensive findViewById(int) results.
//While RecyclerView.LayoutParams belong to the RecyclerView.LayoutManager, ViewHolders belong to the adapter.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.synergia_card,parent,false);

        return new ViewHolder(itemView);
    }
    //Called by RecyclerView to display the data at the specified position. This method should update the contents of the itemView to reflect the item at the given position.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.synergiaName.setText(synergias.get(position).getSynergioName());
//        holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                isChecked[checkedposition] = isChecked;
//            }
//        });
//
//        holder.checkBox.setChecked(isChecked[position]);
    }


    //Counts the amount of synergias
    @Override
    public int getItemCount() {
        return synergias.size();
    }

    //A ViewHolder describes an item view and metadata about its place within the RecyclerView.
    //RecyclerView.Adapter implementations should subclass ViewHolder and add fields for caching potentially expensive findViewById(int) results.
    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView synergiaName;
        public TextView menuButton;
        //Are the fields that are sent to Card to be displayed
        public ViewHolder(View itemView) {
            super(itemView);
            synergiaName = (TextView) itemView.findViewById(R.id.synergianame);
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
        inflater.inflate(R.menu.menu_synergia, popup.getMenu());
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
                    Toast.makeText(context, synergias.get(pos).getSynergioName()+" history", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_map:
                    Toast.makeText(context, "WorkHours "+synergias.get(pos).getSynergioName(), Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
