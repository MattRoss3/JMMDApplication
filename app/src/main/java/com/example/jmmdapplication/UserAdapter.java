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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserWithDetails> users;

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

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final ItemUserInfoBinding binding;

        public UserViewHolder(ItemUserInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserWithDetails user) {
            binding.userName.setText(user.user.getUsername());
            binding.userProgress.setText("Progress: " + user.challengeWithDetails.size()  + "%");
            binding.userChallenges.setText("Challenges: " + user.challengeWithDetails.size());
        }
    }

    public void deleteItem(int position) {
        UserWithDetails user = users.get(position);

        DatabaseRepository.getRepository().deleteUser(user.user);

        users.remove(position);
        notifyItemRemoved(position);
    }
}
