package com.locationalarm.util;

/**
 * http://stackoverflow.com/questions/113511/best-implementation-for-hashcode-method
 * Created by Brent on 4/24/2017.
 */

public class HashCodeBuilder {
    private int result;

    public HashCodeBuilder() {
        this.result = 1;
    }

    public void add(boolean bool) {
        rehash(bool?0:1);
    }

    public void add(byte byt) {
        rehash((int) byt);
    }

    public void add(char cha) {
        rehash((int) cha);
    }

    public void add(short shor) {
        rehash((int) shor);
    }

    public void add(int i) {
        rehash(i);
    }

    public void add(long f) {
        rehash((int)(f ^ (f >>> 32)));
    }

    public void add(float f) {
        rehash(Float.floatToIntBits(f));
    }

    public void add(double f) {
        add(Double.doubleToLongBits(f));
    }

    public void add(Object f) {
        rehash(f==null?0:f.hashCode());
    }

    private void rehash(int hashcode) {
        this.result = this.result * 37 + hashcode;
    }

    public int getResult() {
        return result;
    }
}
