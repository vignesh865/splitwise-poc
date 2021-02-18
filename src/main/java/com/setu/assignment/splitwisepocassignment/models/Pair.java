package com.setu.assignment.splitwisepocassignment.models;

public class Pair {

    String party1;
    String party2;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair other = (Pair) obj;
        if (party1 == null) {
            if (other.party1 != null)
                return false;
        } else if (!party1.equals(other.party1))
            return false;
        if (party2 == null) {
            if (other.party2 != null)
                return false;
        } else if (!party2.equals(other.party2))
            return false;
        return true;
    }

}
