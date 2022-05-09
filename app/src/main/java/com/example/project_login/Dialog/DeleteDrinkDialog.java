package com.example.project_login.Dialog;

import static com.example.project_login.DAO.DrinkDAO.deleteDrink;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.project_login.R;

public class DeleteDrinkDialog extends Dialog {
    public Context context;
    private Button buttonOk;
    private Button buttonCancel;
    private String drinkId;

    public DeleteDrinkDialog(@NonNull Context context, String drinkId) {
        super(context);
        this.context = context;
        this.drinkId = drinkId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_delete_drink);

        buttonOk = findViewById(R.id.btn_Ok);
        buttonCancel = findViewById(R.id.btn_Cancel);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonOkClick(drinkId);
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonCancelClick();
            }
        });
    }

    private void buttonOkClick(String drinkId) {
        deleteDrink(drinkId);
    }

    private void buttonCancelClick() {
        this.dismiss();
    }
}
