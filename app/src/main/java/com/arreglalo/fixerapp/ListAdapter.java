package com.arreglalo.fixerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Solicitud> solicitudList;
    private RecyclerView recyclerView;
    private Context context;
    private  LayoutInflater inflater;

    public ListAdapter(List<Solicitud> solicituds, Context context){
        this.inflater = LayoutInflater.from(context);
        this.context =context;
        this.solicitudList = solicituds;
    }
    @Override
    public int getItemCount(){
        return solicitudList.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    View view = inflater.inflate(R.layout.solictud_list,null);
    return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, int position) {
        holder.bindData(solicitudList.get(position));
    }




    public void setItems(List<Solicitud> items){
        this.solicitudList= items;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tipo,detalles;
        public ViewHolder(View view){
            super(view);
            tipo = view.findViewById(R.id.txt_tipo);
            detalles = view.findViewById(R.id.txt_detalles);


        }
        void bindData(final Solicitud item){
            tipo.setText(item.getService());
            detalles.setText(item.getDetails());
        }
    }






}
