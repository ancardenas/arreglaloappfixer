package com.arreglalo.fixerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements View.OnClickListener{
    private List<Solicitud> solicitudList;
    private RecyclerView recyclerView;
    private Context context;
    private  LayoutInflater inflater;
    private View.OnClickListener listener;
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

    view.setOnClickListener(this);

    return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, int position) {
        holder.bindData(solicitudList.get(position));
    }




    public void setItems(List<Solicitud> items){
        this.solicitudList= items;
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
            if(listener !=null){
                listener.onClick(v);
            }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tipo,detalles,fecha;
        ImageView icono;
        public ViewHolder(View view){
            super(view);
            tipo = view.findViewById(R.id.txt_tipo);
            detalles = view.findViewById(R.id.txt_detalles);
            icono = view.findViewById(R.id.icono);
            fecha = view.findViewById(R.id.txt_fecha);


        }
        void bindData(final Solicitud item){
            tipo.setText(item.getService());
            detalles.setText(item.getDetails());
            if (item.isComplete()){
                icono.setImageResource(R.mipmap.completedstateicon);

            }else if(item.isAcepted()){
                icono.setImageResource(R.mipmap.aceptedstateicon);
            }else{
                icono.setImageResource(R.mipmap.sendstateicon);
            }
            fecha.setText(item.getDia()+"/"+item.getMes()+" "+item.getHora()+":"+item.getMinuto());


        }
    }






}
