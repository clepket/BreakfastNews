package pt.isec.gps1819.breakfastnews;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigFragment extends Fragment implements View.OnClickListener {


    public ConfigFragment() {
        // Required empty public constructor
    }

    EditText et_keywords, et_journalists;
    Button btn_config_guardar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_config, container, false);

        //Permissao para escrever em external storage
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        //variáveis para guardar perfil no ficheiro
        et_keywords = (EditText) v.findViewById(R.id.et_keywords);
        et_journalists = (EditText) v.findViewById(R.id.et_journalists);
        btn_config_guardar = (Button) v.findViewById(R.id.btn_config_guardar);

        //button listener
        btn_config_guardar.setOnClickListener(this);
            /*@Override
            public void onClick(View v) {
                String keywords = et_keywords.getText().toString();
                String jornalistas = et_journalists.getText().toString();

                saveTextAsFile(keywords, jornalistas);
            }
        });*/
        // Inflate the layout for this fragment
        return v;
    }

    private void saveTextAsFile(String keywords, String jornalistas) {
        String fileName = "perfil.txt";
        String enter = "\n";

        //create file
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);


        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            fos.write(keywords.getBytes());
            fos.write(enter.getBytes());
            fos.write(enter.getBytes());
            fos.write(jornalistas.getBytes());
            fos.close();
            Toast.makeText(getContext(), "Saved!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "File not found!!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error saving!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Permission granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Permission not granted!", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
        }
    }

    @Override
    public void onClick(View v) {
        String keywords = et_keywords.getText().toString();
        String jornalistas = et_journalists.getText().toString();

        saveTextAsFile(keywords, jornalistas);
    }
}
