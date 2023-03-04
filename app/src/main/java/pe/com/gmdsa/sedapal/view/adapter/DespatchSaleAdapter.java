package pe.com.gmdsa.sedapal.view.adapter;

import android.content.Context;
import android.provider.SyncStateContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.model.ItemDespatchSale;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.util.Util;


public class DespatchSaleAdapter extends RecyclerView.Adapter<DespatchSaleAdapter.ViewHolder> {

    List<ItemDespatchSale> list = new ArrayList<>();
    Context context;
    OnClickItem onClick;
    String documentType = null;

    public DespatchSaleAdapter(List<ItemDespatchSale> list, Context context, OnClickItem onClick) {
        this.list = list;
        this.context = context;
        this.onClick = onClick;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public interface OnClickItem {
        public void onClick(View view, ItemDespatchSale item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_despatch_sale, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemDespatchSale item = list.get(position);
        holder.txtTitle.setText(Util.getContent(item.getDocumento()));
        String detail = item.getPlaca() + Constants.VALUES.SLASH + item.getTicket() + Constants.VALUES.SLASH + item.getCapacidad();
        holder.txtDetail.setText(detail);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtDetail)
        TextView txtDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.onClick(view, list.get(getAdapterPosition()));
                }
            });
        }
    }
}
