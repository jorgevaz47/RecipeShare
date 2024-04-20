package com.example.recipeshare;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.User;
import com.example.recipeshare.databinding.ActivityLoginPageBinding;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IntentFactoryTest {
    @Mock
    private RecipeLogRepository repository;
    @Mock
    private ActivityLoginPageBinding binding;
    @Mock
    private User user;
    private LoginPage loginPage;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(false);
        loginPage = new LoginPage(binding);
        loginPage.repository = repository;
    }

    // Not sure how to make them NOT crash :/
    @Test
    public void testVerifyUser_WithCorrectInformation() {
        String username = "testUser";
        String password = "testPassword";
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        userLiveData.setValue(user);
        when(repository.getUserByUserName(username)).thenReturn(userLiveData);

        loginPage.verifyUser(username, password);

        verify(loginPage).startActivity(any(Intent.class));
    }

    @Test
    public void testVerifyUser_WithIncorrectInformation(){
        String username = "incorrectUser";
        String password = "testPassword";
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        userLiveData.setValue(null);
        when(repository.getUserByUserName(username)).thenReturn(userLiveData);

        loginPage.verifyUser(username, password);

        Log.i("UNIT TEST INCCORECT", verify(loginPage).toString());
    }
}
