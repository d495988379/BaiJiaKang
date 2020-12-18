package com.zlxn.dl.baijiakang.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zlxn.dl.baijiakang.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Motee
 */
public abstract class BaseEmptyAdapter<T, H extends BaseEmptyViewHolder> extends RecyclerView.Adapter<BaseEmptyViewHolder> {


    public final int TYPE_EMPTY = 0;
    public final int TYPE_NORMAL = 1;


    protected static final String TAG = BaseEmptyAdapter.class.getSimpleName();

    protected final Context context;

    protected int layoutResId;

    protected List<T> datas;


    private OnItemClickListener mOnItemClickListener = null;
    private OnLongItemClicListner onItemLongClickListener;
    private BaseEmptyViewHolder vh;
    private View view;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnLongItemClicListner {
        void onItemClick(View view, int position);
    }

    public BaseEmptyAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }


    public BaseEmptyAdapter(Context context, int layoutResId, List<T> datas) {
        this.datas = datas == null ? new ArrayList<T>() : datas;
        this.context = context;
        this.layoutResId = layoutResId;
    }


    @Override
    public BaseEmptyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_EMPTY) {
            /*view = LayoutInflater.from(context).inflate(R.layout.item_empty,viewGroup,false);
            return  new RecyclerView.ViewHolder(view){};*/
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_empty, viewGroup, false);
            vh = new EmptyViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutResId, viewGroup, false);
            vh = new BaseEmptyViewHolder(view, mOnItemClickListener, onItemLongClickListener);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(BaseEmptyViewHolder viewHoder, int position) {
        if (viewHoder instanceof EmptyViewHolder) {

        } else {
            T item = getItem(position);
            convert((H) viewHoder, item);
        }

    }


    @Override
    public int getItemCount() {
        if (datas == null || datas.size() <= 0)
            return 1;
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (datas.size() <= 0) {
            return TYPE_EMPTY;
        }
        return TYPE_NORMAL;
    }

    public T getItem(int position) {
        if (position >= datas.size()) return null;
        return datas.get(position);
    }


    public void clear() {
//        int itemCount = datas.size();
//        datas.clear();
//        this.notifyItemRangeRemoved(0,itemCount);

        for (Iterator it = datas.iterator(); it.hasNext(); ) {

            T t = (T) it.next();
            int position = datas.indexOf(t);
            it.remove();
            notifyItemRemoved(position);
        }
    }

    /**
     * 从列表中删除某项
     *
     * @param t
     */
    public void removeItem(T t) {
        int position = datas.indexOf(t);
        datas.remove(position);
        notifyItemRemoved(position);
    }


    public List<T> getDatas() {

        return datas;
    }


    public void addData(List<T> datas) {

        this.datas.addAll(datas);
        notifyDataSetChanged();

        //addData(0,datas);
    }

    public void addData(int position, List<T> list) {

        if (list != null && list.size() > 0) {

            for (T t : list) {
                datas.add(position, t);
                notifyItemInserted(position);
            }

        }

    }


    public void refreshData(List<T> list) {

        if (list != null && list.size() > 0) {

            clear();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                datas.add(i, list.get(i));
                notifyItemInserted(i);
            }

        }
    }

    public void loadMoreData(List<T> list) {

        if (list != null && list.size() > 0) {

            int size = list.size();
            int begin = datas.size();
            for (int i = 0; i < size; i++) {
                datas.add(list.get(i));
                notifyItemInserted(i + begin);
            }

        }

    }


    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param viewHoder A fully initialized helper.
     * @param item      The item that needs to be displayed.
     */
    protected abstract void convert(H viewHoder, T item);

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;

    }

    public void setOnLongItemClickListener(OnLongItemClicListner listener) {
        this.onItemLongClickListener = listener;
    }


}
