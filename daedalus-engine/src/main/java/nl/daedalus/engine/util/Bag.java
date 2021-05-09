package nl.daedalus.engine.util;

/**
 * A fast access collection based on Ashley (libgdx) bag, which is based on artemis bag.
 * Grows on demand.
 *
 * @param <T>
 */
public class Bag<T> {

    private int growAmount = 16;

    private T[] data;
    private int size = 0;

   public Bag(){
       this(16);
   }

   public Bag(int capacity) {
       data = (T[]) new Object[capacity];
   }

   public T get(int index) {
       return data[index];
   }

   public void add(T element) {
       if (size == data.length) {
           grow();
       }
       data[size++] = element;
   }

   public void set(int index, T element) {
       if (index >= data.length) {
           grow(index + 1);
       }
       data[index] = element;
   }

    public void clear () {
        for (int i = 0; i < size; i++) {
            data[i] = null; // null elements so gc can clean
        }
        size = 0;
    }

   public void grow() {
       int newCapacity = (data.length) + growAmount;
       grow(newCapacity);
   }

   public int getCapacity() {
       return data.length;
   }

    private void grow (int newCapacity) {
        T[] oldData = data;
        data = (T[])new Object[newCapacity];
        System.arraycopy(oldData, 0, data, 0, oldData.length);
    }

}
