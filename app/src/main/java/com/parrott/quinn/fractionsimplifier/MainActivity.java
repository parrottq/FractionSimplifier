package com.parrott.quinn.fractionsimplifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    NumberPicker numeratorInput;
    NumberPicker denominatorInput;

    NumberPicker numeratorOutput;
    NumberPicker denominatorOutput;

    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numeratorInput = (NumberPicker)findViewById(R.id.numeratorInput);
        denominatorInput = (NumberPicker)findViewById(R.id.denominatorInput);
        setupNumberPicker(numeratorInput);
        setupNumberPicker(denominatorInput);

        numeratorOutput = (NumberPicker)findViewById(R.id.numeratorOutput);
        denominatorOutput = (NumberPicker)findViewById(R.id.denominatorOutput);

        Calculator.setup();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Calculator.Fraction fraction = Calculator.pop();
                    if (fraction == null){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        numeratorOutput.setValue(fraction.getNumerator());
                        denominatorOutput.setValue(fraction.getDenumerator());
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();

        while (true) {

        }

    }


    private void setupNumberPicker(NumberPicker numberPicker){
        numberPicker.setMinValue(0);
        numberPicker.setValue(1);
        numberPicker.setMaxValue(2);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                picker.setMaxValue(newVal + 2);
                for (Object e : getPrimes(newVal).toArray()){
                    Log.i("FS",Integer.toString((int)e));
                }

                Calculator.push(new Calculator.Fraction(numeratorInput.getValue(), denominatorInput.getValue()));

            }

            private List<Integer> getPrimes(int number){
                List<Integer> list = new ArrayList<>();
                for (int e=1; e<=number; e++){
                    if (number%e == 0){
                        list.add(e);
                    }
                }
                return list;
            }

            private int biggestNumber(List<Integer> list1, List<Integer> list2){
                List<Integer> list = new ArrayList<Integer>();
                for (Object number1 : list1.toArray()){
                    for (Object number2 : list2.toArray()){
                        if (number1 == number2){
                            list.add((int) number1);
                        }
                    }
                }
                return (int)list.toArray()[-1];
            }
        });
    }

}
