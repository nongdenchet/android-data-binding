package apidez.com.databinding.view.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by nongdenchet on 10/29/15.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public abstract void setItems(List<T> places);
}
