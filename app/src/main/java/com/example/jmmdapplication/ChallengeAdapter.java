package com.example.jmmdapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jmmdapplication.Database.Relations.UsersWithChallenges;
import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.databinding.ItemChallengeBinding;

import java.util.List;

/**
 * Adapter class for displaying a list of challenges in a {@link RecyclerView}.
 * <p>
 * This adapter binds a list of {@link Challenge} objects to a RecyclerView, allowing for the display and management of challenge data.
 * </p>
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">...</a>
 */
public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder> {

    private final List<Challenge> challenges;
    private final OnItemClickListener onItemClickListener;

    /**
     * Interface for handling item click events.
     */
    public interface OnItemClickListener {
        void onItemClick(Challenge challenge);
    }

    /**
     * Constructs a new {@link ChallengeAdapter}.
     *
     * @param usersWithChallenges A {@link UsersWithChallenges} object containing the challenges to be displayed.
     * @param onItemClickListener An instance of {@link OnItemClickListener} for handling item click events.
     */
    public ChallengeAdapter(UsersWithChallenges usersWithChallenges, OnItemClickListener onItemClickListener) {
        this.challenges = (usersWithChallenges != null) ? usersWithChallenges.challenges : null;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChallengeBinding binding = ItemChallengeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChallengeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeViewHolder holder, int position) {
        if (challenges != null && !challenges.isEmpty()) {
            holder.bind(challenges.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return (challenges != null) ? challenges.size() : 0;
    }

    /**
     * ViewHolder class for displaying challenge information.
     */
    public class ChallengeViewHolder extends RecyclerView.ViewHolder {

        private final ItemChallengeBinding binding;

        /**
         * Constructs a new {@link ChallengeViewHolder}.
         *
         * @param binding The binding object for item_challenge layout.
         */
        public ChallengeViewHolder(ItemChallengeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Binds a {@link Challenge} object to the views in the ViewHolder.
         *
         * @param challenge The {@link Challenge} object to be bound to the views.
         */
        public void bind(Challenge challenge) {
            binding.challengeName.setText(challenge.getName());
            binding.challengeDescription.setText(challenge.getDescription());
            binding.getRoot().setOnClickListener(v -> onItemClickListener.onItemClick(challenge));
        }
    }
}
