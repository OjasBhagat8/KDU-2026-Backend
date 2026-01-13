import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.text.SimpleDateFormat;
import java.util.concurrent.locks.*;

class MessageQueue {
    private final Lock LOCK = new ReentrantLock();
    private final Condition notEmpty = LOCK.newCondition();
    private Deque<String> messages;

    public MessageQueue() {
        messages = new ArrayDeque<>();
    }

     public void put(String str) {
        LOCK.lock();
        try {
            messages.addLast(str);
            notEmpty.signal();
        } finally {
            LOCK.unlock();
        }
    }

     String take() {
        LOCK.lock();
        try {
            while (messages.isEmpty()) {
                notEmpty.await();
            }
            return messages.removeFirst();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            LOCK.unlock();
        }
        
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

public class Exercise2 {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue();

        ExecutorService SenderExecutor = Executors.newFixedThreadPool(3);
        ExecutorService RecieverExecutor = Executors.newFixedThreadPool(3);

        SenderExecutor.submit(new MessageSender(messageQueue, "sender-1"));
        SenderExecutor.submit(new MessageSender(messageQueue, "sender-2"));
        SenderExecutor.submit(new MessageSender(messageQueue, "sender-3"));
        

        RecieverExecutor.submit(new MessageReciever(messageQueue, "reciever-1"));
        RecieverExecutor.submit(new MessageReciever(messageQueue, "reciever-2"));
        RecieverExecutor.submit(new MessageReciever(messageQueue, "reciever-3"));

        SenderExecutor.shutdown();
        RecieverExecutor.shutdown();
    }
}
