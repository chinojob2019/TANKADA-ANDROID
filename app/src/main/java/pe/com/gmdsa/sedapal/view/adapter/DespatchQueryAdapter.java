package pe.com.gmdsa.sedapal.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.model.Despacho;
import pe.com.gmdsa.sedapal.domain.model.ItemDespatchSale;
import pe.com.gmdsa.sedapal.util.Util;

public class DespatchQueryAdapter extends RecyclerView.Adapter<DespatchQueryAdapter.ViewHolderDespatchQuery> {

    List<Despacho> items = new ArrayList<>();
    Context context;
    OnClickItem onClickItem;

    public DespatchQueryAdapter(List<Despacho> items, Context context, OnClickItem onClickItem) {
        this.items = items;
        this.context = context;
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem {
        public void onClick(View view, Despacho item);
    }

    @Override
    public ViewHolderDespatchQuery onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consulta_diaria, parent, false);

        return new ViewHolderDespatchQuery(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDespatchQuery holder, int position) {
        Despacho item = items.get(position);
        holder.txtDate.setText(Util.getContent(item.getFecha()));

        if (item.getTipo().equals("Apertura")) {
            holder.txtDetail.setText(Util.getContent("" + item.getCapacidad()));
            holder.txtTitle.setText(Util.getContent(item.getTipo()));
            holder.txtTitle.setTextColor(Color.parseColor("#00b901"));
            holder.txtTitle.setTypeface(null, Typeface.BOLD);
        } else {
            if (item.getTipo().equals("Cierre")) {
                holder.txtDetail.setText(Util.getContent("" + item.getCapacidad()));
                holder.txtTitle.setText(Util.getContent(item.getTipo()));
                holder.txtTitle.setTextColor(Color.parseColor("#e51b1e"));
                holder.txtTitle.setTypeface(null, Typeface.BOLD);
            } else {
                holder.txtDetail.setText(Util.getContent(Util.getContent(item.getPlaca()) + " / " + Util.getContent(String.valueOf(item.getTicket())) + " / " + Util.getContent(item.getCapacidad())));

                if (item.getPlaca() == null && item.getTicket() == 0) {
                    holder.txtDetail.setText(Util.getContent(item.getCapacidad()));
                }
                holder.txtTitle.setText(Util.getContent(item.getTipo()));
                holder.txtTitle.setTextColor(Color.parseColor("#4b4c4f"));
                holder.txtTitle.setTypeface(null, Typeface.NORMAL);
            }
        }

        try{
            holder.txtVolumen.setVisibility(View.VISIBLE);
            if (item.getFlag().trim().equals("1")) {
                holder.txtVolumen.setText("Volumen: " + item.getVolumen());
            } else if (item.getFlag().trim().equals("0")) {
                holder.txtVolumen.setText("Lectura incial: " + item.getLectura_inicial() + "       " + "Lectura final: " + item.getLectura_final());
            } else {
                holder.txtVolumen.setVisibility(View.INVISIBLE);
            }
        }catch (Exception e){
            holder.txtVolumen.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolderDespatchQuery extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtDetail)
        TextView txtDetail;
        @BindView(R.id.txtVolumen)
        TextView txtVolumen;

        public ViewHolderDespatchQuery(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem.onClick(v, items.get(getAdapterPosition()));
                }
            });
        }
    }
}
