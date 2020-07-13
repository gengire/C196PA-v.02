package edu.wgu.grimes.c868pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import edu.wgu.grimes.c868pa.database.entities.AccountEntity;
import edu.wgu.grimes.c868pa.utilities.HashingUtil;

public class RegisterViewModel extends BaseViewModel {

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }


    public void userExists(String username, Consumer<Boolean> findUser) {
        executor.execute(() -> {
            int count = mRepository.getCountByUsername(username);
            findUser.accept(count > 0);
        });
    }

    public void createAccount(String username, String password) {
        AccountEntity account = new AccountEntity(username, password, null);
        account.setUsername(username);
        account.setPassword(HashingUtil.generateStrongPassword(password));
        mRepository.saveAccount(account);
    }
}
