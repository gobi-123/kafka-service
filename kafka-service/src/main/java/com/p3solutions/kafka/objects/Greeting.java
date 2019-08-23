/**
 * 
 */
package com.p3solutions.kafka.objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author saideepak
 *
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Greeting {
	private String msg;
	private String name;

	@Override
	public String toString() {
		return msg + "," + name;
	}
}
