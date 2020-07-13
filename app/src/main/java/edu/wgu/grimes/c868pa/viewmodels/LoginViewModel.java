package edu.wgu.grimes.c868pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import edu.wgu.grimes.c868pa.App;
import edu.wgu.grimes.c868pa.database.entities.AccountEntity;

public class LoginViewModel extends BaseViewModel {

    public LoginViewModel(@NonNull Application application) {
        super(application);
//        mRepository.deleteAllAccounts();
    }

    public void getAccount(String username, Consumer<AccountEntity> processor) {
        executor.execute(() -> {
           processor.accept(mRepository.getAccountByUsername(username));
        });
    }

    public void setLoggedInAccountId(int accountId) {
        ((App)getApplication()).setLoggedInAccountId(accountId);
        mRepository.setLoggedInAccountId(accountId);
    }
}
