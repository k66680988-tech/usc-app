package com.tutorial.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import org.libsdl.app.SDLActivity;

public class HelloSDL2Activity extends SDLActivity
{
    private static final int REQUEST_ALL_FILES_ACCESS = 9001;

    @Override
    protected void onStart()
    {
        super.onStart();
        requestAllFilesAccessIfNeeded();
    }

    // FIX(android16): guide the user to the "All files access" settings page on
    // first launch so /sdcard/.usc becomes writable. If it stays denied, the
    // native side now falls back to the app-specific external directory.
    private void requestAllFilesAccessIfNeeded()
    {
        if (Build.VERSION.SDK_INT >= 30 && !Environment.isExternalStorageManager())
        {
            try
            {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_ALL_FILES_ACCESS);
            }
            catch (Exception e)
            {
                try
                {
                    startActivity(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION));
                }
                catch (Exception ignored)
                {
                }
            }
        }
    }
}