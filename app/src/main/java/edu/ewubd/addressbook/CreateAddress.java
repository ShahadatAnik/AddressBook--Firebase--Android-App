package edu.ewubd.addressbook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateAddress extends AppCompatActivity {
    EditText name, email, address1, address2, phone;
    Button back, create;
    DatabaseReference myDB = FirebaseDatabase.getInstance().getReference("address");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_address);

        back = findViewById(R.id.btn_back_create_note);
        create = findViewById(R.id.btn_save_create_note);
        back.setOnClickListener(v-> onBackPressed());
        create.setOnClickListener(v->saveAddress());

        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        address1 = findViewById(R.id.et_address1);
        address2 = findViewById(R.id.et_address2);
        phone = findViewById(R.id.et_phone);

    }
    void saveAddress(){
        if(name.getText().toString().trim().length() < 1 || email.getText().toString().trim().length() < 1 ||
                address1.getText().toString().trim().length() < 1 || phone.getText().toString().trim().length() < 1){
            Toast.makeText(this,"Please fill all the field appropriately",Toast.LENGTH_LONG).show();
        } else {
            String ErrorMassage="";
            if(name.getText().toString().trim().length()<5){
                ErrorMassage += "Invalid name\n";
            }
            if(!isValidEmail(email.getText().toString().trim())){
                ErrorMassage += "Invalid email\n";
            }
            if(phone.getText().toString().trim().length() < 14){
                ErrorMassage += "Invalid phone\n";
            }

            if(!isCountryCodeRight(phone.getText().toString().trim())){
                ErrorMassage += "Invalid country code for phone\n";
            }

            if(ErrorMassage.length()>0){
                Toast.makeText(this,ErrorMassage,Toast.LENGTH_LONG).show();
            }
            else{
                String key = name.getText().toString().trim()+"--"+System.currentTimeMillis();
                AddressList al = new AddressList(key, name.getText().toString().trim(), email.getText().toString().trim(),
                        address1.getText().toString().trim(), address2.getText().toString().trim(), phone.getText().toString().trim());
                myDB.child(key).setValue(al);
                Toast.makeText(this,"Address Created Successfully",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }


        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    boolean isCountryCodeRight(@NonNull String number){
        String cc = "";
        for (int i = 0; i < number.length(); i++){
            cc += number.charAt(i);
            if(i == 3) break;
        }
        if(cc.equals("+880")){
            return true;
        }
        return false;
    }
}