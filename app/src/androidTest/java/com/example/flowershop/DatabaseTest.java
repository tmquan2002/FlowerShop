package com.example.flowershop;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    @Test
    public void testCreateDatabaseAssertTrue() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FlowerDatabase.getInstance(appContext);
        File dbFile = appContext.getDatabasePath("FlowerShop");
        Assert.assertTrue(dbFile.exists());
    }
}
