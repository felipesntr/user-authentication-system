package core.usecase;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import core.entity.User;
import core.repository.LoadUserByEmailRepository;

import utils.errors.MissingParamError;
import utils.helpers.Encrypter;
import utils.helpers.TokenGenerator;

public class AuthUseCaseTest {
    class LoadUserByEmailRepositoryMemory implements LoadUserByEmailRepository {
        ArrayList<User> users = new ArrayList<User>();

        User user_1 = new User("name_1", "email_1", "password_1", "role_1", "token_1", "refresh_1", "expire_1");
        User user_2 = new User("name_2", "email_2", "password_2", "role_2", "token_2", "refresh_2", "expire_2");
        User user_3 = new User("name_3", "email_3", "password_3", "role_3", "token_3", "refresh_3", "expire_3");
        User user_4 = new User("name_4", "email_4", "password_4", "role_4", "token_4", "refresh_4", "expire_4");
        User user_5 = new User("name_5", "email_5", "password_5", "role_5", "token_5", "refresh_5", "expire_5");

        public LoadUserByEmailRepositoryMemory() {
            users.add(user_1);
            users.add(user_2);
            users.add(user_3);
            users.add(user_4);
            users.add(user_5);
        }

        @Override
        public User load(String email) {
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    return user;
                }
            }
            return null;
        }

    }

    class AuthUseCase {
        LoadUserByEmailRepository loadUserByEmailRepository;
        Encrypter encrypter;
        TokenGenerator tokenGenerator;

        public AuthUseCase(
                LoadUserByEmailRepository loadUserByEmailRepository,
                Encrypter encrypter,
                TokenGenerator tokenGenerator) {
                ) {
            this.loadUserByEmailRepository = loadUserByEmailRepository;
            this.encrypter = encrypter;
            this.tokenGenerator = tokenGenerator;
        }

        public String auth(String email, String password) throws MissingParamError {
            if (email == "")
                throw new MissingParamError("email");
            if (password == "")
                throw new MissingParamError("password");
            User user = loadUserByEmailRepository.load(email);
            if (user == null)
                return null;
            if (!this.encrypter.compare(user.getPassword(), password))
                return null;
            return tokenGenerator.generate(user.getId());
        }
    }

    public AuthUseCase makeSut() {
        return new AuthUseCase(new LoadUserByEmailRepositoryMemory(), new Encrypter(), new TokenGenerator());
    }

    @Test(expected = MissingParamError.class)
    public void should_throw_if_an_empty_email_is_provided() throws MissingParamError {
        AuthUseCase authUseCase = makeSut();
        authUseCase.auth("", "valid_password");
    }

    @Test(expected = MissingParamError.class)
    public void should_throw_if_an_empty_password_is_provided() throws MissingParamError {
        AuthUseCase authUseCase = makeSut();
        authUseCase.auth("valid_email@email.com", "");
    }

    @Test
    public void should_return_null_if_no_user_found() throws Exception {
        AuthUseCase authUseCase = makeSut();
        authUseCase.auth("invalid_email@email.com", "any_password");
    }

    @Test
    public void should_return_null_if_an_invalid_password_is_provided() throws Exception {
        AuthUseCase authUseCase = makeSut();
        authUseCase.auth("any_email@email.com", "invalid_password");
    }
}