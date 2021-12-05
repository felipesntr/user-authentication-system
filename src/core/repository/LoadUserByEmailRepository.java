package core.repository;

import core.entity.User;

public interface LoadUserByEmailRepository {
    public User load(String email);
}
