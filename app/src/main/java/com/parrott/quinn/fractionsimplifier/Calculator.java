package com.parrott.quinn.fractionsimplifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quinn on 2015-10-29.
 */
public class Calculator {

    private static List<Fraction> query;
    private static Object lock = new Object();

    public static void setup(){
        query = new ArrayList<>();
    }


    public static void push(Fraction fraction){
        synchronized (lock) {
            query.add(fraction);
        }
    }

    public static Fraction pop(){
        synchronized (lock) {
            if (!query.isEmpty()){
                return query.remove(0);
            } else {
                return null;
            }
        }
    }

    public static class Fraction {

        private int num;
        private int denum;

        public Fraction(int numerator, int denumerator){
            num = numerator;
            denum = denumerator;
        }

        public int getNumerator(){
            return num;
        }

        public int getDenumerator(){
            return denum;
        }

    }

}
