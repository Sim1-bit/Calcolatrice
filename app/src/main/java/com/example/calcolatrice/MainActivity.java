package com.example.calcolatrice;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    Map<String, Button> buttons = new HashMap<>();
    TextView text = null;
    private void TakeWidget()
    {
        buttons.put("1", (Button) findViewById(R.id.b1));
        buttons.put("2", (Button) findViewById(R.id.b2));
        buttons.put("3", (Button) findViewById(R.id.b3));
        buttons.put("4", (Button) findViewById(R.id.b4));
        buttons.put("5", (Button) findViewById(R.id.b5));
        buttons.put("6", (Button) findViewById(R.id.b6));
        buttons.put("7", (Button) findViewById(R.id.b7));
        buttons.put("8", (Button) findViewById(R.id.b8));
        buttons.put("9", (Button) findViewById(R.id.b9));
        buttons.put("0", (Button) findViewById(R.id.b0));

        buttons.put("+", (Button) findViewById(R.id.bP));
        buttons.put("-", (Button) findViewById(R.id.bM));
        buttons.put("*", (Button) findViewById(R.id.bX));
        buttons.put("/", (Button) findViewById(R.id.bD));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TakeWidget();
    }

    public void onClick(View v)
    {
        Button aux = (Button) v;

        if((aux.getText().toString().equals("=")))
        {
            text.setText(text.getText().toString() + aux.getText().toString());
        }

    }

    private int calculateExpression()
    {
        String ex = text.getText().toString();

        String aux = "";
        List<Integer> numbers = new ArrayList<Integer>();
        List<Character> operators = new ArrayList<Character>();

        for(int i = 0; i < ex.toCharArray().length; i++)
        {
            char ch = ex.toCharArray()[i];
            if(ch != '+' && ch != '-' && ch != '*' && ch != '/')
            {
                aux = aux + Character.toString(ch);
                continue;
            }
            else if(ch == '-' && i == 0)
            {
                aux = "0";
            }
            numbers.add(Integer.parseInt(aux));
            operators.add(ch);
            aux = "";
        }

        for(int i = 0; i < operators.size();i++)
        {
            switch (operators.get(i))
            {
                case '+':
                    break;

            }
        }

        return 0;
    }
}