public class ThreadJoinTest {
    public static void main(String[] args) throws InterruptedException {
        SharedObj sharedObj = new SharedObj();
        Thread thread1 = new Thread(new ThreadMinus(sharedObj));
        Thread thread2 = new Thread(new ThreadPlus(sharedObj));
        thread1.start();
        thread1.join();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();
        thread2.join();
//        try {
//            thread1.join();
//            thread2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("number is: " + sharedObj.num);
        System.out.println("increment count: " + sharedObj.countPos);
        System.out.println("decrement count: " + sharedObj.countNeg);

    }
}

class SharedObj {
    public int num;
    public int countPos = 0;
    public int countNeg = 0;

    public SharedObj() {
        num = 2000;
    }

    public void change(int x) {
        num += x;
        if (x < 0) {
            countNeg++;
        } else {
            countPos++;
        }
        System.out.println("number is: " + num + " with operation: " + x);
    }
}

class ThreadMinus implements Runnable {
    SharedObj sharedObj;

    public ThreadMinus(SharedObj sharedObj) {
        this.sharedObj = sharedObj;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            sharedObj.change(-1);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ThreadPlus implements Runnable {
    SharedObj sharedObj;

    public ThreadPlus(SharedObj sharedObj) {
        this.sharedObj = sharedObj;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            sharedObj.change(+1);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}