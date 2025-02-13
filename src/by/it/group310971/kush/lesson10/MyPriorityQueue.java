package by.it.group310971.kush.lesson10;
import java.util.*;

public class MyPriorityQueue<E> implements Queue<E> {

    private int Size = 0;
    private E[] data = (E[])new Object[0];
    @Override
    public int size() {
        return Size;
    }

    @Override
    public boolean isEmpty() {
        return Size==0;
    }

    @Override
    public boolean contains(Object o) {
        if (o != null)
            for (int i = 0; i < Size; i++)
                if (data[i].equals(o))
                    return true;
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return offer(e);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Object []cArray = c.toArray();
        for(int i = 0; i < cArray.length; i++){
            if(!contains(cArray[i])){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        E []cArray =(E[]) c.toArray();
        if(cArray.length == 0)
            return false;
        for(int i = 0; i < cArray.length; i++){
            offer(cArray[i]);
        }
        return true;
    }

    public void heapify(){
        for(int i = Size/2-1;i>=0;i--)
            siftdown(i);
    }
    @Override
    public boolean removeAll(Collection<?> c) {
        int i = 0;
        for(;i<Size && !c.contains(data[i]);i++)
            ;
        if(i==Size)
            return false;
        int end = Size;
        int begin = i;
        int[] tosave = new int[end-begin];
        for(i = begin+1; i<end;i++)
            tosave[i-begin] = (c.contains(data[i]))?0:1;
        int par1 = begin;
        for(i = begin; i < end; i++)
            if(tosave[i-begin]==1)
                data[par1++]=data[i];
        Size = par1;
        for(i = Size; i < end; i++)
            data[i]=null;
        heapify();
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int i = 0;
        for(;i<Size && c.contains(data[i]);i++)
            ;
        if(i==Size)
            return false;
        int end = Size;
        int begin = i;
        int[] tosave = new int[end-begin];
        for(i = begin+1; i<end;i++)
            tosave[i-begin] = (!c.contains(data[i]))?0:1;
        int par1 = begin;
        for(i = begin; i < end; i++)
            if(tosave[i-begin]==1)
                data[par1++]=data[i];
        Size = par1;
        for(i = Size; i < end; i++)
            data[i]=null;
        heapify();
        return true;
    }

    @Override
    public void clear() {
        for(int i = 0; i < Size; i++)
            data[i]=null;
        Size = 0;
    }

    @Override
    public boolean offer(E e) {
        if(Size == data.length){
            E []temp = (E[])new Object[Size*3/2+1];
            System.arraycopy(data, 0, temp, 0, Size);
            data = temp;
        }
        data[Size]=e;
        siftup(Size);
        Size++;
        return true;
    }

    @Override
    public E remove() {
        if(Size == 0)
            throw new NoSuchElementException();
        return poll();
    }
    private void swap(int i, int j){
        E temp = data[i];
        data[i]=data[j];
        data[j]=temp;
    }
    private void siftup(int index){
        while(((Comparable<? super E>) data[index]).compareTo(data[(index-1)/2])<0){
            swap(index, (index-1)/2);
            index = (index-1)/2;
        }
    }

    private void siftdown(int index){
        boolean isinplace = false;
        while(2*index+1<Size && !isinplace){
            int left = 2*index+1;
            int right = left+1;
            int child = left;
            if(right < Size && ((Comparable<? super E>) data[right]).compareTo(data[left])<0)
                child = right;
            if(((Comparable<? super E>) data[index]).compareTo(data[child])<0)
                isinplace = true;
            if (!isinplace)
                swap(index, child);
            index = child;
        }
    }
    @Override
    public E poll() {
        if(Size == 0)
            return null;
        E element = data[0];
        data[0] = data[Size-1];
        data[--Size] = null;
        siftdown(0);
        return element;
    }

    @Override
    public E element() {
        if(Size == 0)
            throw new NoSuchElementException();
        return data[0];
    }

    @Override
    public E peek() {
        if(Size == 0)
            return null;
        return data[0];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < Size; i++){
            sb.append(data[i]);
            if(i < Size-1){
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
