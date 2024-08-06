package com.example.jmmdapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jmmdapplication.Database.Relations.UserWithDetails;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ItemUserInfoBinding;
import java.util.List;
import java.util.Objects;

/**
 * Adapter class for displaying a list of users in a {@link RecyclerView}.
 * <p>
 * This adapter binds a list of {@link UserWithDetails} objects to a RecyclerView, allowing for the display and management of user data.
 * </p>
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserWithDetails> users;

    /**
     * Constructs a new {@link UserAdapter}.
     *
     * @param users List of {@link UserWithDetails} to be displayed in the RecyclerView.
     */

    public UserAdapter(List<UserWithDetails> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserInfoBinding binding = ItemUserInfoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserWithDetails user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    /**
     * ViewHolder class for displaying user information.
     */

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final ItemUserInfoBinding binding;

        /**
         * Constructs a new {@link UserViewHolder}.
         *
         * @param binding The binding object for item_user_info layout.
         */

        public UserViewHolder(ItemUserInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Binds a {@link UserWithDetails} object to the views in the ViewHolder.
         *
         * @param user The {@link UserWithDetails} object to be bound to the views.
         */

        public void bind(UserWithDetails user) {
            binding.userName.setText(user.user.getUsername());
            binding.userProgress.setText("Progress: " + user.challengeWithDetails.size()  + "%");
            binding.userChallenges.setText("Challenges: " + user.challengeWithDetails.size());
        }
    }

    /**
     * Deletes an item from the adapter and updates the RecyclerView.
     *
     * @param position The position of the item to be deleted.
     */

    public void deleteItem(int position) {
        UserWithDetails user = users.get(position);

        DatabaseRepository.getRepository().deleteUser(user.user);

        users.remove(position);
        notifyItemRemoved(position);
    }
}
