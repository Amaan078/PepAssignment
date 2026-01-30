public class assignment3 {

    interface MyList<T> {
        void add(T element);
        void insert(T element, int index);
        T get(int index);
        void delete(int index);
        int size();
        boolean isEmpty();
    }

    static class MyArrayList<T> implements MyList<T> {
        private Object[] data;
        private int size;
        private int capacity;

        public MyArrayList() {
            this.capacity = 1;
            this.data = new Object[capacity];
            this.size = 0;
        }

        public void add(T element) {
            if (size == capacity) {
                resize();
            }
            data[size] = element;
            size++;
        }

        public void insert(T element, int index) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            if (size == capacity) {
                resize();
            }
            for (int i = size; i > index; i--) {
                data[i] = data[i - 1];
            }
            data[index] = element;
            size++;
        }

        @SuppressWarnings("unchecked")
        public T get(int index) {
            if (isEmpty()) {
                throw new IllegalStateException("List is empty");
            }
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            return (T) data[index];
        }

        public void delete(int index) {
            if (isEmpty()) {
                throw new IllegalStateException("Cannot delete from an empty list");
            }
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            for (int i = index; i < size - 1; i++) {
                data[i] = data[i + 1];
            }
            data[size - 1] = null;
            size--;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private void resize() {
            capacity *= 2;
            Object[] newData = new Object[capacity];
            for (int i = 0; i < size; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }

        public String toString() {
            if (size == 0) return "[]";
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < size; i++) {
                sb.append(data[i]);
                if (i < size - 1) sb.append(", ");
            }
            sb.append("]");
            return sb.toString();
        }
    }

    static class MyLinkedList<T> implements MyList<T> {

        private class Node {
            T data;
            Node next;

            Node(T data) {
                this.data = data;
                this.next = null;
            }
        }

        private Node head;
        private int size;

        public MyLinkedList() {
            this.head = null;
            this.size = 0;
        }

        public void add(T element) {
            Node newNode = new Node(element);
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
            size++;
        }

        public void insert(T element, int index) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }

            Node newNode = new Node(element);

            if (index == 0) {
                newNode.next = head;
                head = newNode;
            } else {
                Node current = head;
                for (int i = 0; i < index - 1; i++) {
                    current = current.next;
                }
                newNode.next = current.next;
                current.next = newNode;
            }
            size++;
        }

        public T get(int index) {
            if (isEmpty()) {
                throw new IllegalStateException("List is empty");
            }
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }

            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.data;
        }

        public void delete(int index) {
            if (isEmpty()) {
                throw new IllegalStateException("Cannot delete from an empty list");
            }
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }

            if (index == 0) {
                head = head.next;
            } else {
                Node current = head;
                for (int i = 0; i < index - 1; i++) {
                    current = current.next;
                }
                current.next = current.next.next;
            }
            size--;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public String toString() {
            if (head == null) return "[]";
            StringBuilder sb = new StringBuilder("[");
            Node current = head;
            while (current != null) {
                sb.append(current.data);
                if (current.next != null) sb.append(", ");
                current = current.next;
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting List Framework Tests");

        MyList<Integer> arrayList = new MyArrayList<>();
        MyList<String> linkedList = new MyLinkedList<>();

        System.out.println("Testing MyArrayList (Integers)");
        try {
            arrayList.add(10);
            arrayList.add(20);
            arrayList.add(30);
            arrayList.insert(15, 1);
            printListInfo(arrayList);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Testing MyLinkedList (Strings)");
        try {
            linkedList.add("Alice");
            linkedList.add("Bob");
            linkedList.insert("Charlie", 1);
            linkedList.delete(0);
            printListInfo(linkedList);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Testing Exception Handling");

        MyList<Double> emptyList = new MyArrayList<>();
        testException(() -> emptyList.delete(0), "Delete from empty list");

        testException(() -> arrayList.get(100), "Get invalid index");
    }

    public static void printListInfo(MyList<?> list) {
        System.out.println("List Content: " + list.toString());
        System.out.println("Current Size: " + list.size());
    }

    public static void testException(Runnable operation, String testName) {
        System.out.println("Test: " + testName);
        try {
            operation.run();
            System.out.println("FAIL: Operation succeeded but should have failed.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("CAUGHT EXPECTED EXCEPTION: IndexOutOfBoundsException - " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("CAUGHT EXPECTED EXCEPTION: IllegalStateException - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("CAUGHT UNEXPECTED EXCEPTION: " + e.getClass().getSimpleName());
        } finally {
            System.out.println("Test '" + testName + "' execution complete.\n");
        }
    }
}