package com.example.jmmdapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jmmdapplication.Database.entities.Challenge;

import java.util.List;

/**
 * Adapter for displaying a list of {@link Challenge} items in a {@link RecyclerView}.
 * <p>
 * This adapter binds {@link Challenge} data to the views in the RecyclerView, managing how each item is displayed.
 * </p>
 */

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder> {

    private final List<Challenge> challengeList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Challenge challenge);
    }

    /**
     * Creates a new ChallengeAdapter.
     *
     * @param challengeList The list of {@link Challenge} items to be displayed in the RecyclerView.
     */

    public ChallengeAdapter(List<Challenge> challengeList ,OnItemClickListener listener) {
        this.challengeList = challengeList;
        this.listener = listener;
    }

    /**
     * Called when RecyclerView needs a new {@link ChallengeViewHolder} of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new {@link ChallengeViewHolder} that holds a View of the given view type.
     */

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_challenge, parent, false);
        return new ChallengeViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The {@link ChallengeViewHolder} which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */

    @Override
    public void onBindViewHolder(@NonNull ChallengeViewHolder holder, int position) {
        Challenge challenge = challengeList.get(position);
        holder.challengeName.setText(challenge.getName());
        holder.challengeDescription.setText(challenge.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(challenge);
            }
        });

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */

    @Override
    public int getItemCount() {
        return challengeList.size();
    }

    /**
     * ViewHolder for displaying individual challenge items.
     */

    public static class ChallengeViewHolder extends RecyclerView.ViewHolder {
        private final TextView challengeName;
        private final TextView challengeDescription;

        /**
         * Creates a new {@link ChallengeViewHolder} instance.
         *
         * @param itemView The view of the item.
         */

        public ChallengeViewHolder(@NonNull View itemView) {
            super(itemView);
            challengeName = itemView.findViewById(R.id.challenge_name);
            challengeDescription = itemView.findViewById(R.id.challenge_description);
        }

    }
}
