package ru.iu3.fclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import ru.iu3.fclient.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'fclient' library on application startup.
    static {
        System.loadLibrary("fclient");
        System.loadLibrary("mbedcrypto");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ru.iu3.fclient.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Получение рандомных значений
        int res = initRng();
        byte[] v = randomBytes(10);

        // Проверка библиотеки на шифрование
        byte[] key = new byte[16];
        byte[] data = "ThisIsMyPassword12345".getBytes();

        byte[] encryptedData = encrypt(key, data);
        String encryptedString = new String(encryptedData);

        byte[] decryptedData = decrypt(key, encryptedData);
        String decryptedString = new String(decryptedData);

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'fclient' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public static native int initRng();
    public static native byte[] randomBytes(int no);

    public static native byte[] encrypt(byte[] key, byte[] data);

    public static native byte[] decrypt(byte[] key, byte[] data);

}