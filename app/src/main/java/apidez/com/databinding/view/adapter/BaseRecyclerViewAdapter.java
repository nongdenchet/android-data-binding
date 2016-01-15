package apidez.com.databinding.view.adapter;

import android.databinding.ObservableList;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by nongdenchet on 10/29/15.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> mItems;
    private int mRecyclerViewRefCount = 0;
    private final WeakReferenceOnListChangedCallback<T> callback = new WeakReferenceOnListChangedCallback<>(this);

    public void setItems(List<T> items) {
        if (this.mItems == items) {
            return;
        }
        if (mRecyclerViewRefCount > 0) {
            if (this.mItems instanceof ObservableList) {
                ((ObservableList<T>) this.mItems).removeOnListChangedCallback(callback);
            }
            if (items instanceof ObservableList) {
                ((ObservableList<T>) items).addOnListChangedCallback(callback);
            }
        }
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (mRecyclerViewRefCount == 0 && mItems != null && mItems instanceof ObservableList) {
            ((ObservableList<T>) mItems).addOnListChangedCallback(callback);
        }
        mRecyclerViewRefCount += 1;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mRecyclerViewRefCount -= 1;
        if (mRecyclerViewRefCount == 0 && mItems != null && mItems instanceof ObservableList) {
            ((ObservableList<T>) mItems).removeOnListChangedCallback(callback);
        }
    }

    private static class WeakReferenceOnListChangedCallback<T> extends ObservableList.OnListChangedCallback<ObservableList<T>> {
        final WeakReference<BaseRecyclerViewAdapter<T>> adapterRef;
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        WeakReferenceOnListChangedCallback(BaseRecyclerViewAdapter<T> adapter) {
            this.adapterRef = new WeakReference<>(adapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            BaseRecyclerViewAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            mHandler.post(adapter::notifyDataSetChanged);
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, final int positionStart, final int itemCount) {
            BaseRecyclerViewAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            mHandler.post(adapter::notifyDataSetChanged);
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, final int positionStart, final int itemCount) {
            BaseRecyclerViewAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            mHandler.post(() -> adapter.notifyItemRangeInserted(positionStart, itemCount));
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, final int fromPosition, final int toPosition, final int itemCount) {
            BaseRecyclerViewAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            mHandler.post(() -> {
                for (int i = 0; i < itemCount; i++) {
                    adapter.notifyItemMoved(fromPosition + i, toPosition + i);
                }
            });
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, final int positionStart, final int itemCount) {
            BaseRecyclerViewAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            mHandler.post(() -> adapter.notifyItemRangeRemoved(positionStart, itemCount));
        }
    }
}
