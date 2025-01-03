package ar.com.colectiva.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.*;

public interface MockingDataSupplier<Entity, IdClass> {

    List<Entity> list();

    Map<IdClass, Entity> map();

    default Entity pickRandom() {
        return list().get(new Random().nextInt(list().size()));
    }

    default Set<Entity> set() {
        return new HashSet<>(list());
    }

    default Slice<Entity> slice(Pageable pageable) {
        List<Entity> list = list();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());
        List<Entity> sliceContent = list.subList(start, end);

        boolean hasNext = end < list.size();
        return new SliceImpl<>(sliceContent, pageable, hasNext);
    }

}
