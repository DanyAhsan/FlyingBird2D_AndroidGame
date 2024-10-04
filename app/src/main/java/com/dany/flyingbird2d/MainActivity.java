package com.dany.flyingbird2d;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean isMute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // Check if auto-rotate is enabled
        if (!isAutoRotateEnabled()) {
            // Prompt the user to enable auto-rotate
            showAutoRotatePrompt();
        }

        // Force the screen to be rotated to landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        TextView highScoreTxt = findViewById(R.id.highScoreTxt);

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        highScoreTxt.setText("HighScore: " + prefs.getInt("highscore", 0));

        isMute = prefs.getBoolean("isMute", false);

        final ImageView volumeCtrl = findViewById(R.id.volumeCtrl);

        if (isMute)
            volumeCtrl.setImageResource(R.drawable.ic_volume_off_black_24dp);
        else
            volumeCtrl.setImageResource(R.drawable.ic_volume_up_black_24dp);

        volumeCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isMute = !isMute;
                if (isMute)
                    volumeCtrl.setImageResource(R.drawable.ic_volume_off_black_24dp);
                else
                    volumeCtrl.setImageResource(R.drawable.ic_volume_up_black_24dp);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMute", isMute);
                editor.apply();

            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Landscape mode
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Portrait mode
            // Force the screen to be rotated to landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private boolean isAutoRotateEnabled() {
        return Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
    }

    private void showAutoRotatePrompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enable Auto-Rotate");
        builder.setMessage("For the best experience, please enable auto-rotate in your device settings.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Open the system settings for the display
                Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void showInfoDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Info");
        builder.setMessage("- Drag plane upward/downward to control the altitude\n- Tap to fire\n- Missing a bird will result in Game Over\n- Hitting a bird will also result in Game Over");
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if auto-rotate is enabled again in onResume
        if (!isAutoRotateEnabled()) {
            // Prompt the user to enable auto-rotate
            showAutoRotatePrompt();
        }
    }

}
