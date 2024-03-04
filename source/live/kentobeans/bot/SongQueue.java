package live.kentobeans.bot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 *
 * @author Jason
 */
public class SongQueue implements Queue {

    private static final List<Object> queue = new ArrayList<>();
    
    @Override
    public synchronized boolean add(Object e) {
        if (e == null) {
            throw new NullPointerException("Specified element is null");
        }

        return queue.add(e);
    }

    @Override
    public synchronized boolean offer(Object e) {
        if (e == null) {
            throw new NullPointerException("Specified element is null");
        }

        return queue.add(e);
    }

    @Override
    public synchronized Object remove() {
        if (queue.isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        Object obj = queue.get(0);
        queue.remove(obj);
        
        return obj;
    }

    @Override
    public synchronized Object poll() {
       if (queue.isEmpty()) {
           return null;
       }
       
       Object obj = queue.get(0);
       queue.remove(obj);
       
       return obj;
    }

    @Override
    public synchronized Object element() {
        if (queue.isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        
        return queue.get(0);
    }

    @Override
    public synchronized Object peek() {
        if (queue.isEmpty()) {
            return null;
        }
        
        return queue.get(0);
    }
    
    @Override
    public synchronized int size() {
        return queue.size();
    }
    
    @Override
    public synchronized Object[] toArray() {
        return queue.toArray();
    }
    
    @Override
    public synchronized void clear() {
        queue.clear();
    }

    @Override
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public synchronized boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public synchronized Iterator iterator() {
        return queue.iterator();
    }

    @Override
    public synchronized Object[] toArray(Object[] a) {
        return queue.toArray(a);
    }

    @Override
    public synchronized boolean remove(Object o) {
        return queue.remove(o);
    }

    @Override
    public synchronized boolean containsAll(Collection c) {
        return queue.containsAll(c);
    }

    @Override
    public synchronized boolean addAll(Collection c) {
        return queue.addAll(c);
    }

    @Override
    public synchronized boolean removeAll(Collection c) {
        return queue.removeAll(c);
    }

    @Override
    public synchronized boolean retainAll(Collection c) {
        return queue.retainAll(c);
    }
    
    public synchronized void addAtPosition(Object e, int position) {
        if (e == null) {
            throw new NullPointerException("Specified element is null");
        }

        if (queue.contains(e)) {
            queue.remove(e);
        }
        queue.add(position, e);
    }
    
    public synchronized void moveToFront(Object e) {
        addAtPosition(e, 0);
    }
}
