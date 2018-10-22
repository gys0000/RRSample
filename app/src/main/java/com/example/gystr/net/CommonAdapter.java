package com.example.gystr.net;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class CommonAdapter<T> extends RecyclerView.Adapter<CommonAdapter.CommonViewHolder> {
    private Context context;
    private List<T> mList;
    private int layoutID;
    private int brId;

    public CommonAdapter(Context context, List<T> mList, int layoutID, int brId) {
        this.context = context;
        this.mList = mList;
        this.layoutID = layoutID;
        this.brId = brId;
    }

    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutID, parent, false);
        CommonViewHolder commonViewHolder = new CommonViewHolder(binding.getRoot());
        commonViewHolder.setBinding(binding);
        return commonViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonAdapter.CommonViewHolder holder, int position) {
        holder.getBinding().setVariable(brId, mList.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class CommonViewHolder extends RecyclerView.ViewHolder {
        public CommonViewHolder(View itemView) {
            super(itemView);
        }

        private ViewDataBinding binding;

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}
