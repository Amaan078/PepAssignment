class MyArrayList<T> {

    private Object[] arr;
    private int size;
    private int capacity;

    MyArrayList() {
        this.size = 0;
        this.capacity = 1;
        this.arr = new Object[this.capacity];
    }

    private void resize() {
        System.out.println("Increasing size of the array");
        this.capacity = this.capacity * 2;
        // new T[] is not allowed in java
        Object[] temp = new Object[this.capacity];

        for (int i = 0; i < this.size; i++) {
            temp[i] = this.arr[i];
        }
        this.arr = temp;
    }

    void add(T x) {
        if (this.size == this.capacity) {
            this.resize();
        }
        this.arr[this.size++] = x;
    }

    T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        // type casting Object to T
        return (T) arr[index];
    }

    void insert(T x, int pos) {
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        if (this.size == this.capacity) {
            this.resize();
        }

        for (int i = size; i > pos; i--) {
            arr[i] = arr[i - 1];
        }

        arr[pos] = x;
        size++;
    }

    void delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        for (int i = index; i < size - 1; i++) {
            arr[i] = arr[i + 1];
        }

        arr[size - 1] = null;
        size--;
    }

    int getSize() {
        return size;
    }
}



class Main {
    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>();

        list.add(10);
        list.add(20);
        list.add(30);
        list.insert(15, 1);

        System.out.println(list.get(1));

        list.delete(9);

        for (int i = 0; i < list.getSize(); i++) {
            System.out.print(list.get(i) + " ");
        }
    }
}