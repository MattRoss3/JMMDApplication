package com.example.jmmdapplication;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jmmdapplication.Database.Relations.UsersWithChallenges;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.databinding.ItemUserInfoBinding;
import com.example.jmmdapplication.viewmodel.UserViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Adapter class for displaying a list of users in a RecyclerView.
 * This adapter binds a list of User objects to a RecyclerView, allowing for the display and management of user data.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;
    private UsersWithChallenges challengeList;
    private final UserViewModel userViewModel;
    private final ExecutorService executorService;

    /**
     * Constructs a new UserAdapter.
     *
     * @param users               List of User to be displayed in the RecyclerView.
     * @param usersWithChallenges UsersWithChallenges object containing user and their assigned challenges.
     * @param userViewModel       UserViewModel to handle user data operations.
     */
    public UserAdapter(List<User> users, UsersWithChallenges usersWithChallenges, UserViewModel userViewModel) {
        this.users = users;
        this.challengeList = usersWithChallenges;
        this.userViewModel = userViewModel;
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
        holder.bind(users.get(position), challengeList);
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
         * Constructs a new UserViewHolder.
         *
         * @param binding The binding object for item_user_info layout.
         */
        public UserViewHolder(ItemUserInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Binds a User object to the views in the ViewHolder.
         *
         * @param user          The User object to be bound to the views.
         * @param challengeList The UsersWithChallenges object containing user challenges.
         */
        public void bind(User user, UsersWithChallenges challengeList) {
            binding.userName.setText(user.getUsername());
            binding.userProgress.setText("Progress: " + "0%" + ""); // Update with actual progress if available
            binding.userChallenges.setText("Challenges: " + challengeList.challenges.size());
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
                new Handler(Looper.getMainLooper()).post(() -> {
                    users.remove(position);
                    notifyItemRemoved(position);
                });
            } catch (Exception e) {
                Log.e("UserAdapter", "Error deleting user", e);
            }
        });
    }

    /**
     * Updates the list of users in the adapter and refreshes the RecyclerView.
     *
     * @param users The new list of users.
     */
    public void updateUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    /**
     * Updates the list of user challenges in the adapter and refreshes the RecyclerView.
     *
     * @param challengeList The new list of user challenges.
     */
    public void updateChallenges(UsersWithChallenges challengeList) {
        this.challengeList = challengeList;
        notifyDataSetChanged();
    }
}
