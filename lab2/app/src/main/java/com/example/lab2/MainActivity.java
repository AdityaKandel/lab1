package com.example.lab2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onOpenInGoogleMaps(View view){
        EditText teamAddress = (EditText) findViewById(R.id.teamAddressTextView);

        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?q=" + teamAddress.getText());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }
    ActivityResultLauncher<Intent> profileActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result){
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        ImageView avatarImage = (ImageView) findViewById(R.id.avatarImage);

                        String drawbleName = "flag_02";
                        switch(data.getIntExtra("imageID",R.id.flagid00)){
                            case R.id.flagid00:
                                drawbleName = "flag_00";
                                break;
                            case R.id.flagid01:
                                drawbleName = "flag_01";
                                break;
                            case R.id.flagid02:
                                drawbleName = "flag_02";
                                break;
                            case R.id.flagid03:
                                drawbleName = "flag_03";
                                break;
                            case R.id.flagid04:
                                drawbleName = "flag_04";
                                break;
                            case R.id.flagid05:
                                drawbleName = "flag_05";
                                break;
                            case R.id.flagid06:
                                drawbleName = "flag_06";
                                break;
                            case R.id.flagid07:
                                drawbleName = "flag_07";
                                break;
                            case R.id.flagid08:
                                drawbleName = "flag_08";
                                break;
                            default:
                                drawbleName = "flag_02";
                                break;

                        }
                        int resID = getResources().getIdentifier(drawbleName,"drawable", getPackageName());
                        avatarImage.setImageResource(resID);
                    }
                }

            });
    public void OnsetAvatarButton(View view){
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        profileActivityResultLauncher.launch(intent);
    }
}