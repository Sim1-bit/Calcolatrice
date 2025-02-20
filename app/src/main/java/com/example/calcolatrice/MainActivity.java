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
import android.widget.Toast;

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

        text = findViewById(R.id.textView);
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
        switch (aux.getText().toString())
        {
            case "C":
                String expression = text.getText().toString(); // Get text properly
                if (!expression.isEmpty())
                { // Check if not empty
                    text.setText(expression.substring(0, expression.length() - 1)); // Remove last char
                }
                return;
            case "CE":
                text.setText("");
                return;
        }

        if(!(aux.getText().toString().equals("=")))
        {
            if(!text.getText().toString().isEmpty())
            {
                switch (text.getText().charAt(text.getText().length() - 1))
                {
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        if(aux.getText().charAt(0) == '+' || aux.getText().charAt(0) == '-' || aux.getText().charAt(0) == '*' || aux.getText().charAt(0) == '/')
                            return;
                        break;

                }
            }

            /*char c = text.getText().charAt(text.getText().length() - 1);
            String b = aux.getText().toString();
            if((c == '+' || c == '-' || c == '*' || c == '/') && (b.equals("+") || b.equals("-") || b.equals("*") || b.equals("/")))
            {
                return;
            }*/
            if(text.getText().toString().isEmpty() && (aux.getText().toString().equals("*") || aux.getText().toString().equals("/")))
                return;

            StringBuilder auxS = new StringBuilder(text.getText().toString());
            auxS.append(aux.getText().toString());
            text.setText(auxS);
        }
        else
        {
            text.setText(String.valueOf(calculateExpression()));
        }
    }

    private int calculateExpression()
    {
        String ex = text.getText().toString();
        StringBuilder aux = new StringBuilder();
        List<Integer> numbers = new ArrayList<Integer>();
        List<Character> operators = new ArrayList<Character>();

        findExpression(ex, aux, numbers, operators);

        resolvePriority(numbers, operators);

        for(int i = 0; i + 1 <= operators.size(); i++)
        {
            numbers.set(i + 1, calculateExpression(operators.get(i),numbers.get(i),numbers.get(i+1)));
        }

        return numbers.get(numbers.size() - 1);
    }

    private void findExpression(String ex, StringBuilder aux, List<Integer> numbers, List<Character> operators)
    {
        for(int i = 0; i <= ex.length(); i++)
        {
            if(i ==  ex.length())
            {
                numbers.add(Integer.parseInt(aux.toString()));
                continue;
            }

            char ch = ex.toCharArray()[i];
            if(ch != '+' && ch != '-' && ch != '*' && ch != '/')
            {
                aux.append(Character.toString(ch));
                continue;
            }
            else if((ch == '-' || ch == '+') && i == 0)
            {
                aux = new StringBuilder("0");
            }
            numbers.add(Integer.parseInt(aux.toString()));
            operators.add(ch);
            aux = new StringBuilder();
        }
    }

    private int calculateExpression(char operator, int num1, int num2)
    {
        switch (operator)
        {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2;
        }
        return 0;
    }

    private void resolvePriority(List<Integer> numbers, List<Character> operators)
    {
        for(int i = 0; i < operators.size(); i++)
        {
            if(operators.get(i) == '*' || operators.get(i) == '/')
            {
                numbers.set(i + 1, calculateExpression(operators.get(i), numbers.get(i), numbers.get(i + 1)));
                operators.remove(i);
                numbers.remove(i);
                i--;
            }
        }
    }
}

