package com.oss2.dasom;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor(access = AccessLevel.MODULE)
@NoArgsConstructor
public class NanoId implements Serializable {

    @Setter(AccessLevel.PRIVATE)
    private String id;

    public static NanoId makeId() {
        return new NanoId(NanoIdUtils.randomNanoId());
    }

    public static NanoId of(String id) {
        return new NanoId(id);
    }

    @Override
    public String toString(){
        return id;
    }

}