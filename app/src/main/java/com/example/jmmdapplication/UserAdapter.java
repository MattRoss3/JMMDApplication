package com.example.jmmdapplication;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.databinding.ItemUserInfoBinding;
import com.example.jmmdapplication.viewmodel.UserViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Adapter class for displaying a list of users in a {@link RecyclerView}.
 * <p>
 * This adapter binds a list of {@link User} objects to a RecyclerView, allowing for the display and management of user data.
 * </p>
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">JMMDApplication</a>
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;
    private final UserViewModel userViewModel;
    private final ExecutorService executorService;

    /**
     * Constructs a new {@link UserAdapter}.
     *
     * @param users       List of {@link User} to be displayed in the RecyclerView.
     * @param viewModel   The ViewModel instance for user operations.
     */
    public UserAdapter(List<User> users, UserViewModel viewModel) {
        this.users = users;
        this.userViewModel = viewModel;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserInfoBinding binding = ItemUserInfoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    /**
     * ViewHolder class for displaying user information.
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {
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
         * Binds a {@link User} object to the views in the ViewHolder.
         *
         * @param user The {@link User} object to be bound to the views.
         */
        public void bind(User user) {
            binding.userName.setText(user.getUsername());
            // Assuming User entity has a method to get progress and challenges
            binding.userProgress.setText("Progress: "  + "%");
            binding.userChallenges.setText("Challenges: " );
        }
    }

    /**
     * Deletes an item from the adapter and updates the RecyclerView.
     *
     * @param position The position of the item to be deleted.
     */
    public void deleteItem(int position) {
        User user = users.get(position);

        executorService.execute(() -> {
            try {
                userViewModel.delete(user);
                // UI updates must be done on the main thread
                new Handler(Looper.getMainLooper()).post(() -> {
                    users.remove(position);
                    notifyItemRemoved(position);
                });
            } catch (Exception e) {
                Log.e("UserAdapter", "Error deleting user", e);
            }
        });
    }
}
