package core.usecase;

import org.junit.Test;

import utils.errors.MissingParamError;

public class AuthUseCaseTest {
    class AuthUseCase {
        public AuthUseCase() {
        }

        public String auth(String email, String password) throws MissingParamError {
            if (email == "")
                throw new MissingParamError("email");
            return "";
        }
    }

    @Test(expected = MissingParamError.class)
    public void should_throw_if_an_empty_email_is_provided() throws MissingParamError {
        AuthUseCase authUseCase = new AuthUseCase();
        authUseCase.auth("", "valid_password");
    }
}