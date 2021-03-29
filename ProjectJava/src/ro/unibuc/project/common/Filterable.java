package ro.unibuc.project.common;

public interface Filterable<T, C>{
        C[] filter(C[] items, T value);
}
