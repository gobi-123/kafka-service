package com.p3solutions.kafka;

import lombok.Builder;
import lombok.Data;

/**
 * TestObject
 * 
 * @author svudya
 */
@Data
@Builder
public class TestObject {
    private String id;
    private String name;

}