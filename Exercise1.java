import java.util.*;
import java.text.SimpleDateFormat;

class MessageQueue {
    private Deque<String> messages;

    public MessageQueue() {
        messages = new ArrayDeque<>();
    }

    synchronized public void put(String str) {
        messages.addLast(str);
        notifyAll();
    }

    synchronized String take() {
        while (messages.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return messages.removeFirst();
    }
}

class MessageSender implements Runnable {
    private MessageQueue messageQueue;
    private String senderName;

    public MessageSender(MessageQueue deque, String senderName) {
        this.messageQueue = deque;
        this.senderName = senderName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            String timeStamp = new SimpleDateFormat("HH.mm.ss.SSS").format(new Date());
            String messageName = senderName + " message-" + i + " " + timeStamp;
            messageQueue.put(messageName);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

class MessageReciever implements Runnable {
    private MessageQueue messageQueue;
    private String recieverName;

    public MessageReciever(MessageQueue deque, String recieverName) {
        this.messageQueue = deque;
        this.recieverName = recieverName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            String message = messageQueue.take();
            if (message == null) break;
            System.out.println("Message received by " + recieverName + ": " + message);
        }
    }
}

public class Exercise1 {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue();

        Thread t1 = new Thread(new MessageSender(messageQueue, "sender-1"));
        Thread t2 = new Thread(new MessageSender(messageQueue, "sender-2"));
        Thread t3 = new Thread(new MessageSender(messageQueue, "sender-3"));

        Thread t4 = new Thread(new MessageReciever(messageQueue, "reciever-1"));
        Thread t5 = new Thread(new MessageReciever(messageQueue, "reciever-2"));
        Thread t6 = new Thread(new MessageReciever(messageQueue, "reciever-3"));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}
