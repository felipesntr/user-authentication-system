package core.usecase;

import core.entity.User;
import core.repository.LoadUserByEmailRepository;
import utils.errors.MissingParamError;
import utils.helpers.Encrypter;
import utils.helpers.TokenGenerator;

public class AuthUseCase {
    LoadUserByEmailRepository loadUserByEmailRepository;
    Encrypter encrypter;
    TokenGenerator tokenGenerator;

    public AuthUseCase(
            LoadUserByEmailRepository loadUserByEmailRepository,
            Encrypter encrypter,
            TokenGenerator tokenGenerator) {
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
