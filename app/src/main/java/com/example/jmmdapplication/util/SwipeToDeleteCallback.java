package com.example.jmmdapplication.util;

import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jmmdapplication.UserAdapter;

/**
 * Callback class for handling swipe-to-delete actions in a {@link RecyclerView}.
 * <p>
 * This class allows for the deletion of items from the {@link RecyclerView} when swiped left or right.
 * </p>
 */

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private final UserAdapter mAdapter;

    /**
     * Constructor for SwipeToDeleteCallback.
     *
     * @param adapter The adapter associated with the {@link RecyclerView} from which items will be deleted.
     */


    public SwipeToDeleteCallback(UserAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;

    }

    /**
     * Called when an item is moved in the {@link RecyclerView}. Not used in this implementation.
     *
     * @param recyclerView The {@link RecyclerView} where the move happened.
     * @param viewHolder The {@link RecyclerView.ViewHolder} being moved.
     * @param target The target {@link RecyclerView.ViewHolder} for the move.
     * @return false, indicating that moving items is not supported.
     */

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * Called when a swipe action is detected. This method handles the deletion of the swiped item.
     *
     * @param viewHolder The {@link RecyclerView.ViewHolder} that was swiped.
     * @param direction The direction of the swipe. Either {@link ItemTouchHelper.LEFT} or {@link ItemTouchHelper.RIGHT}.
     */

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.deleteItem(position);
    }

    /**
     * Called when the child view is drawn during a swipe. This method can be overridden to customize the visual appearance
     * of the swipe action.
     *
     * @param c The {@link Canvas} on which the child view is drawn.
     * @param recyclerView The {@link RecyclerView} where the swipe happened.
     * @param viewHolder The {@link RecyclerView.ViewHolder} being swiped.
     * @param dX The amount of horizontal displacement.
     * @param dY The amount of vertical displacement.
     * @param actionState The current state of the action.
     * @param isCurrentlyActive true if the swipe is currently active, false otherwise.
     */

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}

