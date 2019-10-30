/**
 * 
 */
package com.p3solutions.kafka.topic_manager;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;

/**
 * @author saideepak
 *
 */
public class CreateTopic extends NewTopic {

	/**
	 * New Topic with Replica Assignments List
	 * 
	 * @param name
	 * @param replicasAssignments
	 */
	public CreateTopic(String name, Map<Integer, List<Integer>> replicasAssignments) {
		super(name, replicasAssignments);
	}

	/**
	 * New Topic with one partition and one replication factor
	 * 
	 * @param name
	 * @return {@link NewTopic}
	 */
	public CreateTopic(String name) {
		super(name, 1, (short) 1);
	}

	/**
	 * New Topic with configurable partitions and one replication factor
	 * 
	 * @param name
	 * @param numPartitions
	 * @return {@link NewTopic}
	 */
	public CreateTopic(String name, int numPartitions) {
		super(name, numPartitions, (short) 1);
	}

	/**
	 * New Topic with configurable partitions and replication factor
	 * 
	 * @param name
	 * @param numPartitions
	 * @param replicationFactor
	 * @return {@link NewTopic}
	 */
	public CreateTopic(String name, int numPartitions, short replicationFactor) {
		super(name, numPartitions, replicationFactor);
	}
}
