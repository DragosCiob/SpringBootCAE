package ro.siit.SpringBootCAE.models;

import java.util.Comparator;

public class UpdateIndexComparator implements Comparator<Request> {

    @Override
    public int compare(Request o1, Request o2) {

        return o1.getIndex().compareTo(o2.getIndex());
    }
}
