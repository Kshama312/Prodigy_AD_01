package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView input, solution;
    MaterialButton C, openBracket, closeBracket;
    MaterialButton divide, multiply, minus, plus, equals;
    MaterialButton zero, one , two, three, four, five,six, seven , eight, nine;
    MaterialButton AC, point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input= findViewById(R.id.input);
        solution = findViewById(R.id.solution);

        assignId(C, R.id.C);
        assignId(openBracket, R.id.openBracket);
        assignId(closeBracket, R.id.closeBracket);
        assignId(divide, R.id.divide);
        assignId(multiply, R.id.multiply);
        assignId(minus, R.id.minus);
        assignId(plus, R.id.plus);
        assignId(equals, R.id.equals);
        assignId(zero, R.id.zero);
        assignId(one, R.id.one);
        assignId(two, R.id.two);
        assignId(three, R.id.three);
        assignId(four, R.id.four);
        assignId(five, R.id.five);
        assignId(six, R.id.six);
        assignId(seven, R.id.seven);
        assignId(eight, R.id.eight);
        assignId(nine, R.id.nine);
        assignId(AC, R.id.AC);
        assignId(point, R.id.point);
    }
    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString();
        String calculate = input.getText().toString();
        if (buttonText.equals("AC")) {
            // Clear all
            input.setText("");
            solution.setText("0");
        } else if (buttonText.equals("=")) {
            // Calculate the result
            String result = getResult(calculate);
            input.setText(result);
            solution.setText(result);
        } else if (buttonText.equals("C")) {
            // Delete the last character
            if (!calculate.isEmpty()) {
                calculate = calculate.substring(0, calculate.length() - 1);
            }
            input.setText(calculate);
        } else {
            calculate = calculate + buttonText;
            input.setText(calculate);
        }
    }
    String getResult(String data) {
        try {
            double result = evaluateExpression(data);
            return String.valueOf(result);
        } catch (Exception e) {
            return "Error";
        }
    }

    double evaluateExpression(String expression) {
        expression = expression.replaceAll("\\s", ""); // Remove spaces
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    sb.append(expression.charAt(i));
                    i=i+1;
                }
                i=i-1;
                numbers.push(Double.parseDouble(sb.toString()));
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    double result = applyOperator(numbers.pop(), numbers.pop(), operators.pop());
                    numbers.push(result);
                }
                operators.pop();
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    double result = applyOperator(numbers.pop(), numbers.pop(), operators.pop());
                    numbers.push(result);
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            double result = applyOperator(numbers.pop(), numbers.pop(), operators.pop());
            numbers.push(result);
        }

        return numbers.pop();
    }

    double applyOperator(double b, double a, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case 'X':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
        }
        return 0;
    }

    int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        }
        return 0;
    }
}
