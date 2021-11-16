/**
 * MIT License
 * Copyright (c) 2021 Maciej Wójcik, Maciej Poncyleusz
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pl.first.firstjava;

public class SudokuPart implements Observable {
    private SudokuField[] fields = new SudokuField[9];
    private Observer observer;

    SudokuPart() {
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField();
        }
    }

    public boolean verify() {
        int[] values = new int[9];
        for (int x = 0; x < 9; x++) {
            if (fields[x].getFieldValue() == 0) {
                return false;
            }
            values[fields[x].getFieldValue() - 1]++;
        }
        for (int i : values) {
            if (i != 1) {
                return false;
            }
        }
        return true;
    }

    public void setField(int x, int value, boolean notify) {
        if (x < 0 || x > 8) {
            return;
        }
        fields[x].setFieldValue(value);
        if (notify) {
            notifyObservers(x);
        }
    }

    public int getField(int x) {
        if (x < 0 || x > 8) {
            return -1;
        }
        return fields[x].getFieldValue();
    }

    @Override
    public void setObserver(Observer observer) {
        this.observer = observer;
    }


    @Override
    public void notifyObservers(int x) {
        observer.update(this, x);
    }
}
