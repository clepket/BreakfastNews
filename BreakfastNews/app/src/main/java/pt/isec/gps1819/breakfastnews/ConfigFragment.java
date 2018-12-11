package pt.isec.gps1819.breakfastnews;


import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigFragment extends Fragment implements View.OnClickListener  {


    public ConfigFragment() {
        // Required empty public constructor
    }
    Integer hour = 19;
    Integer min = 30;
    EditText et_keywords, et_journalists;
    Button btn_config_guardar;
    ImageButton image_btn_clock;
    CheckBox checkBox;
    public MainActivity mainQuiz;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_config, container, false);
        String buffer = null;
        String[] linhas = null;

        //Permissao para escrever em external storage
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        buffer = ((MainActivity)getActivity()).readFile("perfil.txt");

        //variáveis para guardar perfil no ficheiro
        et_keywords = (EditText) v.findViewById(R.id.et_keywords);
        et_journalists = (EditText) v.findViewById(R.id.et_journalists);
        btn_config_guardar = (Button) v.findViewById(R.id.btn_config_guardar);
        image_btn_clock = (ImageButton) v.findViewById(R.id.image_btn_clock);
        checkBox = v.findViewById(R.id.checkBox);

        if(buffer != null) {
            linhas = buffer.split("\n");
            if(linhas!=null && linhas.length>0) {
                et_keywords.setText(linhas[0]);
                CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);

                if (linhas.length > 1) {
                    et_journalists.setText(linhas[1]);
                    if (linhas.length > 2) {
                        checkBox.setText("Todos os dias às " + linhas[2] + "h" + linhas[3]);
                        checkBox.setChecked(true);
                    }
                }
            }
        }

        checkBox.setOnClickListener(this);

        //button listener
        btn_config_guardar.setOnClickListener(this);

        //image button listener
        image_btn_clock.setOnClickListener(this);
        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Permission granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Permission not granted!", Toast.LENGTH_SHORT).show();
                    //getActivity().finish();
                }
        }
    }

    @Override
    public void onClick(View v) {
        mainQuiz = (MainActivity)getActivity();
        hour = mainQuiz.hour;
        min = mainQuiz.min;
        switch (v.getId()) {
            case R.id.btn_config_guardar:
                String keywords = et_keywords.getText().toString();
                String jornalistas = et_journalists.getText().toString();

                mainQuiz.saveTextAsFile("/data/user/0/pt.isec.gps1819.breakfastnews/files/perfil.txt", keywords, jornalistas);

                FeedFragment feedFragment = new FeedFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment, feedFragment, feedFragment.getTag()).commit();
                break;

            case R.id.image_btn_clock:
                //Intent intentLoadNewActivity = new Intent(getActivity(), TimePickerFragment.class);
                //startActivity(intentLoadNewActivity);
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(), "time picker");
                break;
            case R.id.checkBox:

                if (checkBox.isChecked()){
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY, hour);
                    c.set(Calendar.MINUTE, min);
                    c.set(Calendar.SECOND, 0);

                    mainQuiz.setAlarm(c);
                    Toast.makeText(getContext(), "Alarme para as "+ hour+min , Toast.LENGTH_SHORT).show();
                }else{
                    mainQuiz.alarmCancel();
                    Toast.makeText(getContext(), "Alarme cancelado!" , Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
